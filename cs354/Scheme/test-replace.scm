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

(define (replace source search-for replace-with)
    (if (same-value source search-for) ; True --> No need for iteration or recursion
        replace-with
        (if (pair? source)
            (cond ((null? (cdr source)) (list (replace (copy (car source)) search-for replace-with))) ; Base Case
                  (else (cons (replace (copy (car source)) search-for replace-with)
                              (replace (copy (cdr source)) search-for replace-with)))) ; Recursive replace
            (copy source))))
                    
        
; Tests the replace method by displaying the result as well as the original source.
; This tests for proper source/search-for detection.
; This also checks to make sure that all 'pairs' have actually been deep-copied.
(define (test-replace source search-for replace-with test-description)
    (define result (replace source search-for replace-with))
    (newline)
    (display "  ------ ") (display test-description) (display " ------") (newline)
    (display "            Source: ") (display source) (newline)
    (display "           Replace: ") (display search-for) (display " with: ") (display replace-with) (newline)
    (display "            Result: ") (display result) (newline)
    (display "    Deep Copy Test:  (eqv? source result) => ")
    (display (eqv? source result))
    (display (if (same-object source result) " --- Result is NOT a deep copy.)\n" " --- Result is a deep copy.\n")) (newline))
    
(test-replace 1 1 2 "(1) - Numbers Test 1")
(test-replace 11 1 2 "(2) - Numbers Test 2")
(test-replace 1098 10 3 "(3) - Numbers Test 3")
(test-replace 1098 98 3 "(4) - Numbers Test 4 ")
(test-replace 1098 1098 3 "(5) - Numbers Test 5")
(test-replace '(a (b c) d) '(b c) '(x y) "(6) - List Test 1 - Basic")
(test-replace '(a b c) '(a b) '(x y) "(7) - List Test 2 - List vs. Sub-List 1")
(test-replace '(a b c) '(b c) '(x y) "(8) - List Test 3 - List vs. Sub-List 2")
(test-replace '(a (b c) d (b c) b c (b) (c) (b c)) '(b c) '(x y) "(9) - List Test 4 - List vs. Sub-List 3")
(test-replace '(a (b ((a (b c))) c)) '(b c) '(x y) "(10) - List Test 5 - Recursion Test 1 - Basic")
(test-replace '((((b c)))(b c) b c ((((a (b c)))))(b c) w) '(b c) '(x y) "(11) - List Test 5 - Recursion Test 2 - Advanced")
(test-replace '(#f #t #f #t) #f #t "(12) - Boolean Test 1")
(test-replace '(#f #t #f #t) #t #f "(13) - Boolean Test 2")
(test-replace #f #f "False" "(14) - Boolean Test 3")
(test-replace #t #t "True" "(15) - Boolean Test 4")
(test-replace '(#f #f #f #f) #t "True" "(16) - Boolean Test 5")
(test-replace '(f false t true "false" "true" #f #t #t #f) #t "TRUE" "(17) - Boolean Test 6")
(test-replace '("AND" "aND" "And") "AND" "and" "(18) - String Test 1")
(test-replace '("You" "and" "Me") "and" "or" "(19) - String Test 2")
(test-replace '("Wrong" "\"Right, Wrong\"" "Wrong" "Right" ("Wrong" (("Wrong" "Wrong") "Wrong"))) "Wrong" "Right" "(20) - String Test 3")
(test-replace '(a (b c) (d $ (& c b c $) (b c)) ((b c) 874 (((a b c (b c)))) (d e) $ (g (h) (i () j)) (k (l m) n o ((b c) (a))))) '(b c) "(Lbc)" "(21) - Mixed Test")


