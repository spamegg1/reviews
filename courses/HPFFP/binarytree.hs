module BinaryTree where

data BinaryTree a =
      Leaf
    | Node (BinaryTree a) a (BinaryTree a)
    deriving (Eq, Ord, Show)

insert' :: Ord a => a -> BinaryTree a -> BinaryTree a
insert' b Leaf = Node Leaf b Leaf
insert' b (Node left a right)
    | b == a = Node left a right
    | b < a = Node (insert' b left) a right
    | b > a = Node left a (insert' b right)




-- map function for BinaryTree
mapTree :: (a -> b) -> BinaryTree a -> BinaryTree b
mapTree _ Leaf = Leaf
mapTree f (Node left a right) =
    Node (mapTree f left) (f a) (mapTree f right)

testTree' :: BinaryTree Integer
testTree' = Node (Node Leaf 3 Leaf) 1 (Node Leaf 4 Leaf)
mapExpected = Node (Node Leaf 4 Leaf) 2 (Node Leaf 5 Leaf)
-- acceptance test for mapTree
mapOkay =
    if mapTree (+1) testTree' == mapExpected
    then print "yup okay!"
    else error "test failed!"




-- convert BinaryTree to list
-- mid first, then left, then right
preorder :: BinaryTree a -> [a]
preorder Leaf = []
preorder (Node left a right) = [a] ++ (preorder left) ++ (preorder right)

-- left first, in numeric order according to the BinaryTree
inorder :: BinaryTree a -> [a]
inorder Leaf = []
inorder (Node left a right) = (inorder left) ++ [a] ++ (inorder right)

-- mid first, then right, then left
postorder :: BinaryTree a -> [a]
postorder Leaf = []
postorder (Node left a right) = [a] ++ (postorder right) ++ (postorder left)

testTree :: BinaryTree Integer
testTree = Node (Node (Node Leaf 1 Leaf) 2 (Node Leaf 3 Leaf))
                4
                (Node (Node Leaf 5 Leaf) 6 (Node Leaf 7 Leaf))

testPreorder :: IO ()
testPreorder =
    if preorder testTree == [4, 2, 1, 3, 6, 5, 7]
    then putStrLn "Preorder fine!"
    else putStrLn "Bad news bears."

testInorder :: IO ()
testInorder =
    if inorder testTree == [1, 2, 3, 4, 5, 6, 7]
    then putStrLn "Inorder fine!"
    else putStrLn "Bad news bears."

testPostorder :: IO ()
testPostorder =
    if postorder testTree == [4, 6, 7, 5, 2, 3, 1]
    then putStrLn "Postorder fine!"
    else putStrLn "Bad news bears."




-- any traversal order is fine
-- I am using foldr on "in-order" (of the binary tree node values)
foldTree :: (a -> b -> b) -> b -> BinaryTree a -> b
foldTree f acc tree = foldr f acc (inorder tree) -- 2 ^ (3 ^ 4)
-- foldTree f acc Leaf = acc
-- foldTree f acc (Node left a right) = f a (foldTree f (foldTree f acc left) right) -- (3 ^ (4 ^ 2))
-- foldTree f acc (Node left a right) = f a (foldTree f (foldTree f acc right) left) -- (3 ^ (2 ^ 4))
-- foldTree f acc (Node left a right) = foldTree f (f a (foldTree f acc left)) right -- (4 ^ (3 ^ 2))
-- foldTree f acc (Node left a right) = foldTree f (f a (foldTree f acc right)) left -- (2 ^ (3 ^ 4))

testfoldTree :: IO ()
testfoldTree =
    if foldTree (+) 0 testTree == 28
    then putStrLn "foldTree fine!"
    else putStrLn "Bad news bears."

testExp = foldTree (^) 1 (Node (Node Leaf 2 Leaf) 3 (Node Leaf 4 Leaf))
