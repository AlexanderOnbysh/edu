package ua.kpi.coursework.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.kpi.coursework.domain.User;
import ua.kpi.coursework.service.MovieService;

@Component
public class MovieValidator implements Validator {
    private final MovieService movieService;

    @Autowired
    public MovieValidator(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
