(defun fact (n)
  (cond ((equal n 0) 1)
        (t ((lambda (x y)(* x y))
            n (fact (- n 1))
            )
           )
        )
  )

(print (fact 5))