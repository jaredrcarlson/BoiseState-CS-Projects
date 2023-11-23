; This method returns a copy of the source
; If the source is a 'pair' - A deep-copy is returned
(define (copy source)
    (if (pair? source)
        (cons (copy (car source)) (copy (cdr source)))
		source))

; Checks if values are the same
; Does not check if src1 and src2 are the same object in memory
(define (same-value src1 src2)
        (if (equal? src1 src2) #t #f))

(define (same-object src1 src2)
		(if (eqv? src1 src2) #t #f))

; Searches a source for supplied search-for term and replaces any and all occurrences with supplied replace-with
(define (replace source search-for replace-with)
    (if (same-value source search-for) ; True --> No need for iteration or recursion
        replace-with
        (if (pair? source)
            (cond ((null? (cdr source)) (list (replace (copy (car source)) search-for replace-with))) ; Base Case
                  (else (cons (replace (copy (car source)) search-for replace-with)
                              (replace (copy (cdr source)) search-for replace-with)))) ; Recursive replace
            (copy source))))