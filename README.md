# Parser-calculator
A simple parser calculator java

final -> exp

exp   -> term exp2

exp2  -> + term exp2
        | - term exp2
        | ε

term  -> factor term2

term2 -> * factor term2
        | / factor term2
        | ε

factor -> num
        | ( exp )
        | ε

num   -> 0...9 num
	      | ε




FIRST SETS
FIRST(term) = FIRST(factor) = FIRST(exp) = FIRST(final) = FIRST(num)= { (,0...9 }
FIRST(exp2) = { +,-,ε }
FIRST(term2) = { *,/,ε }


FOLLOW SETS
FOLLOW(final) = { $ }
FOLLOW(exp) = FOLLOW(exp2) = { $,) }
FOLLOW(tern) = FOLLOW(term2 = { +,-,ε }
FOLLOW(factor) = { *,/,ε }
FOLLOW(num) = { (,0...9,ε }

FIRST+ SETS

FIRST+(final) = FIRST+(exp) = FIRST+(term) = FIRST+(factor) = { (,0...9 }
FIRST+(exp2) = FIRST(exp2) U FOLLOW(exp2) = { +,-,$,) }
FIRST+(term2) = FIRST(term2) U FOLLOW(term2) = { *,/,+,-,$,) }
