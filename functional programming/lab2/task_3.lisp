(defun list>= (a b)
  (cond
    ((or (null a) (null b)) nil)
    ((>= a (car b)) (list>= a (cdr b)))
    (t (cons (car b) (list>= a (cdr b))))))

(defun list< (a b)
  (cond
    ((or (null a) (null b)) nil)
    ((< a (car b)) (list< a (cdr b)))
    (t (cons (car b) (list< a (cdr b))))))

(defun hoare (L)
  (cond
    ((null L) nil)
    (t
      (append
        (hoare (list< (car L) (cdr L)))
        (cons (car L) nil) 
        (hoare (list>= (car L) (cdr L)))))))

(print (hoare '(1 5 3 8 2)))
(print (hoare '(9 8 7 6 5 4 3 2 1)))
(print (hoare '(1)))