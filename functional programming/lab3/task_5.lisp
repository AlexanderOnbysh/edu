(defun cusom_eval (local_form &optional (local_links nil))
  (cond
    ((atom local_form)
     (cond
       ((eq local_form 't1) 't1)
       ((eq local_form 'nil) 'nil1)
       ((numberp local_form) local_form)
       ((car (assoc local_form local_links)))
       (t (format t
                  "~%In atom absent local link: ~S"
                  local_form))
       ((atom (car local_form))
        (cond
          ((eq (car local_form) 'quote1)
           (cadr local_form))
          ((eq (car local_form) 'cond1)
           (evalualte-cond (cdr local_form) local_links))
          ((get (car local_form) 'fn)
           (custom-apply (get (car local_form) 'fn)
                         (evalualte-list (cdr local_form)
                                         local_links)
                         local_links))
          (t (custom-apply (car local_form)
                           (evalualte-list (cdr local_form)
                                           local_links)
                           local_links))))
       (t (custom-apply (car local_form)
                        (evalualte-list (cdr local_form) local_links)
                        local_links)))))
  )

(defun evalualte-cond (branches context)
  (cond
    ((null branches) 'nil1)
    ((not (eq (cusom_eval (caar branches) context)
              'nil1))
     (cusom_eval (cadar branches) context))
    (t (evalualte-cond (cdr branches) context)))
  )

(defun diff (L1 L2)
  (cond
    ((null L1) L1)
    ((member (first L1) L2) (diff (rest L1) L2))
    (t (cons (first l1) (diff (rest l1) l2)))))

(defun custom-apply (func arg local_links)
  (cond ((atom func)
         (cond
           ((eq func 'car1)
            (cond ((eq (car arg) 'nil1)
                   'nil1)
                  (t (caar arg))))
           ((eq func 'cdr1)
            (cond ((eq (car arg) 'nil1)
                   'nil1)
                  ((null (cdar arg))
                   'nil1)
                  (t (cdar arg))))
           ((eq func 'cons1)
            (cond ((eq (cadr arg)
                       'nil1)
                   (list (car arg)))
                  (t (cons (car arg)
                           (cadr arg)))))
           ((eq func 'atom1)
            (cond ((atom (car arg))
                   't1)
                  (t 'nil1)))
           ((eq func 'equal1)
            (cond ((equal (car arg)
                          (cadr arg))
                   't1)
                  (t 'nil1)))
           (t (format t "~%Unkown function:
                        ~S" func))))
        ((eq (car func) 'lambda1)
         (cusom_eval (caddr func)
                     (create-links (cadr func)
                                   arg local_links)))
        ((eq (car func) 'diff)
                (diff (caddr func)))
        
        (t (format t
                   "~%It's not lambda call: ~S"
                   func)))
  )


(defun create-links
		(local_forms params env)
  (cond
    ((null local_forms) env)
    (t (acons (car local_forms)
              (car params)
              (create-links (cdr local_forms)
                            (cdr params)
                            env))))
  )

(defun evalualte-list (params local_links)
  (cond
    ((null params) nill)
    (t (cons
         (cusom_eval (car params) local_links)
         (evalualte-list (cdr params)
                         local_links))))
  )


(print (diff '(1 2 3) '(2 3)))
(print (cusom_eval (diff '(1 2 3) '(1 2 3))))