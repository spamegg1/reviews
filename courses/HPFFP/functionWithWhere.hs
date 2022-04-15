module FunctionWithWhere where

printInc n = print plusTwo
    where plusTwo = n + 2
    
mult1 = x * y
    where x = 5
          y = 6
          
mult2 = x * 3 + y
    where x = 3
          y = 1000
          
wax0n = x * 5 where
    z = 7
    y = z + 8
    x = y ^ 2
    
triple x = x * 3

wax0ff x = triple x