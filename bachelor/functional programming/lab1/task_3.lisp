(defun remove-nth (n list)
  (declare
    (type (integer 0) n)
    (type list list))
  (if (or (zerop n) (null list))
    (cdr list)
    (cons (car list) (remove-nth (1- n) (cdr list)))))

(DEFUN process (lst)
   (COND ((AND (EQ (TYPE-OF (CAR lst)) 'SYMBOL) (EQ (TYPE-OF (CAR (LAST lst))) 'SYMBOL)) (CONS (CAR lst) (CAR (LAST lst))))
   	(T (remove-nth 1 lst))
   	)
)


(PRINT (process '(1 2 3 4 5)))
(PRINT (process '(A 1 2 3 B)))
(PRINT (process '()))
