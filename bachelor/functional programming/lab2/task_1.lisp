(defun swap (list)
  (if (null (rest list))
      list
      (cons (first (last list))
            (append (butlast (rest list))
                    (list (first list))))))

(print (swap '(1 2 3 4)))
(print (swap '(1 2)))
(print (swap '(1)))