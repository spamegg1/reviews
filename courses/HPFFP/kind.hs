-- a has kind *
class Sumthin a where
    s :: a -> a

-- b has kind *, f has kind *->*, g has kind *->*->*->*, c has kind *
-- g :: * -> * -> * -> *
-- g a b c (g a b c)
class Else where
    e :: b -> f (g a b c)

-- e has kind *->*->*, a,b,c,d have kind *
class Biffy where
    slayer :: e a b -> (a -> c) -> (b -> d) -> e c d

-- these two do NOT kind check:
class Impish v where
    impossibleKind :: v -> v a
class AlsoImp v where
    nope :: v a -> v