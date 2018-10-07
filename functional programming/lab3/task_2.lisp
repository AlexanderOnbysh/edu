(defun flatten (list)
  (cond
    ((null list) nil)
    ((atom list) (list list))
    (T (mapcan #'flatten list))
    ))

(defun not-in (var expr)
  (not (member var (flatten expr))))

(defun rewrite (expr)
  (if (atom expr) expr
      (let ((operator (car expr)) (arg1 (cadr expr)) (arg2 (caddr expr)))
        (cond
          ((eq operator '+)
           (cond
             ((null arg2) (rewrite arg1)) ; +(- arg1)
             ((eq arg2 0) arg1) ; +0
             ((eq arg1 0) arg2) ; 0+
             ((and (numberp arg1) (numberp arg2)) (+ arg1 arg2))
             (T expr)
             ))
          ((eq operator '-)
           (cond
             ((and (eq arg1 0) (null arg2)) 0) ; (-0)
             ((and (null arg2) (listp arg1) (eq (car arg1) '-)) (rewrite (cadr arg1))) ; -(- arg1)
             ((eq arg1 0) (if (eq arg2 0) 0 (list '- arg2))) ; 0 - arg2
             ((eq arg2 0) arg1) ; arg1 - 0
             ((and (numberp arg1) (numberp arg2)) (- arg1 arg2))
             (T expr)
             ))
          ((eq operator '*)
           (cond
             ((or (eq arg2 0) (eq arg1 0)) 0) ; 0*
             ((eq arg2 1) arg1) ; 1*
             ((eq arg1 1) arg2) ; *1
             ((and (numberp arg1) (numberp arg2)) (* arg1 arg2))
             (T expr)
             ))
          ((eq operator '/) 
           (cond
             ((eq arg2 0) (error "divide by zero"))
             ((eq arg1 0) 0)
             ((and (numberp arg1) (numberp arg2)) (/ arg1 arg2))
             (T expr)
             ))
          ((eq operator '^) 
           (cond
             ((eq arg2 0) 1) ; f(x)^0
             ((eq arg2 1) arg1) ; f(x)^1
             ((and (numberp arg1) (numberp arg2)) (if (eq arg1 0) 0 (exp (* arg2 (log arg1)))))
             (T expr)
             ))
          (T expr)
          ))))     


(defmacro derivative (var expr)
  `(derive (quote ,var) (quote ,expr)))

(defun is-named-function (name)
  (if (member name '(^ sin cos tg ctg sqrt exp ln log asin acos atg actg)) T nil))

(defun named-function-derivative (name arg)
  (cond
    ((eq name 'sin) `(cos ,(rewrite arg)))
    ((eq name 'cos) `(- (sin ,(rewrite arg))))
    ((eq name 'sqrt) `(/ 1 (* 2 (sqrt ,(rewrite arg)))))
    ((eq name 'exp) (rewrite `(exp ,(rewrite arg))))
    ((eq name 'ln) `(/ 1 ,(rewrite arg)))
    ))

(defun pow (var arg1 arg2)
  (cond
    ; a^f(x)
    ((not-in var arg1)
     (rewrite (list '* 
                    (rewrite (list '* 
                                   (rewrite (list '^ arg1 arg2))
                                   `(ln ,arg1)))
                    (derive var arg2))))
    ; f(x) ^ a
    ((not-in var arg2)
     (rewrite (list '* 
                    (rewrite (list '* arg2
                                   (rewrite (list '^ arg1 
                                                  (rewrite (list '- arg2 '1))))))
                    (derive var arg1))))
    ; f(x) ^ g(x)
    (T (derive var (list 'exp (rewrite (list '* arg2 (list 'ln arg1))))))
    ))

(defun derive (var expr)
  (cond
    ((null expr) nil)
    ((and (atom expr) (eq var expr)) 1)
    ((not (member var (flatten expr))) 0)
    (T (let ((operator (car expr)) (arg1 (rewrite (cadr expr))) (arg2 (rewrite (caddr expr))))
         (cond
           ((member operator '(+ -)) 
            (if (null arg2) (rewrite (list operator (derive var arg1)))
                (rewrite (list operator (derive var arg1) (derive var arg2)))))
           ((eq operator '*)
            (rewrite (list '+
                           (rewrite (list '* (derive var arg1) arg2))
                           (rewrite (list '* arg1 (derive var arg2)))
                           )))
           ((eq operator '/)
            (rewrite (list '/ 
                           (rewrite (list '- 
                                          (rewrite (list '* (derive var arg1) arg2))
                                          (rewrite (list '* arg1 (derive var arg2)))
                                          ))
                           (rewrite (list '^ arg2 2)))))
           
           ((eq operator '^) 
            (pow var arg1 arg2))
           (T 
             (if (is-named-function operator) 
                 (rewrite (list '* (named-function-derivative operator arg1) (derive var arg1)))
                 (error "unknown function")))
           )))))

(print (derivative x (sin (* 2 x))))