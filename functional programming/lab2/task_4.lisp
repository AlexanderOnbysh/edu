(defun merge-lists (a b)
  (cond ((not a) b)
        ((not b) a)
        ((< (car a) (car b)) (cons (car a) (merge-lists (cdr a) b)))
        (T (cons (car b) (merge-lists a (cdr b))))))

(print (merge-lists '(1 3 5 7) '(2 4 6 8 9)))