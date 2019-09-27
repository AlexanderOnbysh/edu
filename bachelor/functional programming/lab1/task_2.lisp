(setq LIST_1 '(PRIM SD FLAG () (GHD)))
(setq LIST_2 '(1 56 98 52))
(setq LIST_3 '(T 2 3 4 Y H))

(DEFUN CONCAT_LISTS
       (L1 L2 L3) 
       (list (NTH 5 L1) (NTH 3 L2) (NTH 2 L3))
)
(print (CONCAT_LISTS LIST_1 LIST_2 LIST_3))
