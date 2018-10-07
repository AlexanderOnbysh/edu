(defun del-lev-n (w n m)
  (when w ((lambda (a d)
             (cond ((atom a) (cons a (del-lev-n d n m)))
                   ((= n 0) (cons (del-n a m) (del-lev-n d n m)))
                   ((cons (del-lev-n a (1- n) m) (del-lev-n d n m)))))
           (car w) (cdr w))))
 
(defun del-n (w n)
  (cond ((null w) nil)
        ((= n 1) (cdr w))
        (t (cons (car w) (del-n (cdr w) (1- n))))))

(print (del-lev-n '(a b (1 2 3 (1 2 3 (1 2 3))) c d) 0 2))