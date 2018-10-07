package ua.kpi.coursework.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.kpi.coursework.domain.Movie;
import ua.kpi.coursework.domain.Share;
import ua.kpi.coursework.domain.User;
import ua.kpi.coursework.repository.ShareRepository;
import ua.kpi.coursework.service.MovieServiceImpl;
import ua.kpi.coursework.service.SecurityService;
import ua.kpi.coursework.service.UserService;
import ua.kpi.coursework.validator.MovieValidator;
import ua.kpi.coursework.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class AppController {

    private final MovieServiceImpl movieService;
    private final UserValidator userValidator;
    private final UserService userService;
    private final SecurityService securityService;
    private final MovieValidator movieValidator;
    private final ShareRepository shareRepository;

    @Autowired
    public AppController(MovieServiceImpl movieService, UserValidator userValidator, UserService userService, SecurityService securityService, MovieValidator movieValidator, ShareRepository shareRepository) {
        this.movieService = movieService;
        this.userValidator = userValidator;
        this.userService = userService;
        this.securityService = securityService;
        this.movieValidator = movieValidator;
        this.shareRepository = shareRepository;
    }

    @ModelAttribute("user")
    public User emptyUserModel() {
        return new User();
    }


    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("genres", movieService.getUniqueGenres());
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("genres", movieService.getUniqueGenres());
        return "login";
    }

    @GetMapping("/genre/{genre}")
    public String movieByGenrePage(Model model, @PathVariable String genre, @RequestParam(required = false) Integer page) {
        int limit = 50;  // how many movies displayed per page
        int pages = 15;

        if (page == null)
            page = 0;

        int totalMovies = movieService.getTotalByGenre(genre);
        model.addAttribute("genre", genre);
        model.addAttribute("genres", movieService.getUniqueGenres());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalMovies", Math.ceil(totalMovies / limit) > pages ? pages : Math.ceil(totalMovies / limit));
        model.addAttribute("moviesByField", movieService.getMoviesByGenre(genre, new PageRequest(page, limit)));

        return "moviesByField";
    }

    @GetMapping("/year/{year}")
    public String movieByYearPage(Model model, @PathVariable Integer year, @RequestParam(required = false) Integer page) {
        int limit = 50;  // how many movies displayed per page
        int pages = 15;

        if (page == null)
            page = 0;

        int totalMovies = movieService.getTotalByYear(year);
        model.addAttribute("year", year);
        model.addAttribute("genres", movieService.getUniqueGenres());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalMovies", Math.ceil(totalMovies / limit) > pages ? pages : Math.ceil(totalMovies / limit));
        model.addAttribute("moviesByField", movieService.getMoviesByYear(year, new PageRequest(page, limit)));

        return "moviesByField";
    }

    @GetMapping("/movie/{id}")
    public String movieDetailView(Model model, @PathVariable Integer id) {
        model.addAttribute("movie", movieService.getMovieById(id));
        model.addAttribute("genres", movieService.getUniqueGenres());
        return "movie_detailed_view";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchMovieByTitle(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(required = false) Integer page) {
        String title = request.getParameter("title");
        int totalMovies;
        int limit = 10;  // how many movies displayed per page
        int pages = 15;

        if (page == null)
            page = 0;

        List<Movie> list = this.movieService.getMoviesByTitleLike(title, new PageRequest(0, limit));
        totalMovies = list.size();
        model.addAttribute("genres", movieService.getUniqueGenres());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalMovies", Math.ceil(totalMovies / limit) > pages ? pages : Math.ceil(totalMovies / limit));
        model.addAttribute("moviesByField", list);

        return "moviesByField";
    }

    @GetMapping("/")
    public String indexPage(Model model, @RequestParam(required = false) Integer page) {
        int limit = 50;  // how many movies displayed per page

        if (page == null)
            page = 0;

        int year = Calendar.getInstance().get(Calendar.YEAR) - 1;
        model.addAttribute("genres", movieService.getUniqueGenres());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalMovies", Math.ceil(movieService.getTotalByYear(year) / limit));
        model.addAttribute("moviesByYear", movieService.getMoviesByYear(year, new PageRequest(page, limit)));

        return "index";
    }

    @GetMapping("/watch-later")
    public String watchLater(Model model, Authentication authentication) {
        String name = authentication.getName();
        Optional<User> user = this.userService.findByUsername(name);

        if (user.isPresent()) {
            model.addAttribute("genres", movieService.getUniqueGenres());
            model.addAttribute("moviesWatchLater", user.get().getMoviesWatchLater());
            return "moviesWatchLater";
        } else {
            return "registration";
        }
    }

    @GetMapping("/favorites")
    public String favorites(Model model, Authentication authentication) {
        String name = authentication.getName();
        Optional<User> user = this.userService.findByUsername(name);

        if (user.isPresent()) {
            model.addAttribute("genres", movieService.getUniqueGenres());
            model.addAttribute("moviesFavorites", user.get().getMoviesFavorites());
            return "moviesFavorites";
        } else {
            return "registration";
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String watchLater() {
        return "hello";
    }

    @GetMapping("/admin")
    public String adminIndex() {
        return "admin_index";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("genres", movieService.getUniqueGenres());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("user") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getLogin(), userForm.getPasswordConfirm());

        return "redirect:/";
    }

    @RequestMapping(value = "/addMovie", method = RequestMethod.GET)
    public String addMovie(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("genres", movieService.getUniqueGenres());

        return "add_movie";
    }

    @RequestMapping(value = "/addMovie", method = RequestMethod.POST)
    public String addMovie(@ModelAttribute("movie") Movie movieForm, BindingResult bindingResult, Model model) {
        movieValidator.validate(movieForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "addMovie";
        }

        movieService.save(movieForm);

        return "redirect:/";
    }

    @RequestMapping(value = "addToFavorites", method = RequestMethod.POST)
    @ResponseBody
    public void addToFavorites(Authentication authentication, HttpServletRequest request) {
        Integer movie_id = Integer.parseInt(request.getParameter("id"));
        String name = authentication.getName();
        Optional<User> user = this.userService.findByUsername(name);
        if (user.isPresent()) {
            Movie movie = this.movieService.getMovieById(movie_id);
            if (user.get().getMoviesFavorites().contains(movie)) {
                this.movieService.deleteUserFromFavorites(movie, user.get());
            } else {
                this.movieService.addUserToFavorites(movie, user.get());
            }
        }
    }

    @RequestMapping(value = "addToWatchLater", method = RequestMethod.POST)
    @ResponseBody
    public void addToWatchLater(Authentication authentication, HttpServletRequest request) {
        Integer movie_id = Integer.parseInt(request.getParameter("id"));
        String name = authentication.getName();
        Optional<User> user = this.userService.findByUsername(name);
        if (user.isPresent()) {
            Movie movie = this.movieService.getMovieById(movie_id);
            if (user.get().getMoviesWatchLater().contains(movie)) {
                this.movieService.deleteUserWatchLater(movie, user.get());
            } else {
                this.movieService.addUserToWatchLater(movie, user.get());
            }
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/share", method = RequestMethod.GET)
    public String share(Authentication authentication) {
        String name = authentication.getName();
        Optional<User> user = this.userService.findByUsername(name);
        if (user.isPresent()) {
            String uuid = UUID.randomUUID().toString();
            Share share = new Share();
            share.setSharedMovies(user.get().getMoviesFavorites());
            share.setSharedLink(uuid);
            this.shareRepository.save(share);
            StringBuilder url = new StringBuilder();
            url.append("/share/");
            url.append(uuid);
            return url.toString();
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/share/{link}")
    public String share_link(@PathVariable("link") String link, Model model) {
        Optional<Share> share = this.shareRepository.findByLink(link);
        if (share.isPresent()) {
            Set<Movie> movies = share.get().getSharedMovies();
            model.addAttribute("genres", movieService.getUniqueGenres());
            model.addAttribute("sharedMovies", movies);
            return "shareLink";
        } else {
            return "redirect:/";
        }
    }
}
