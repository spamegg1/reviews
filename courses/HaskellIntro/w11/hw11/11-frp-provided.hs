{-# LANGUAGE RecursiveDo #-}

module Main where
import Graphics.UI.WX hiding (Event)
import Reactive.Banana
import Reactive.Banana.WX

main :: IO ()
main = start hello

hello :: IO ()
hello = do
    f     <- frame      [text := "WX Demo"]
    money <- textEntry f [text := "5"]

    add   <- button f   [text := "Insert coin"]
    buy   <- button f   [text := "Buy banana"]

    quit  <- button f   [text := "Quit", on command := close f]

    set f [layout :=
        margin 5 $ column 5 $ map hfill
            [ widget add
            , widget buy
            , widget money
            , widget quit
            ]]

    let coinMachine :: MomentIO ()
        coinMachine = mdo
            eCoin <- event0 add command
            eBuyPress <- event0 buy command

            let bPrice      = pure 10
                bCanBuy     = (>=) <$> bMoney <*> bPrice
                eBananaSold = whenE bCanBuy eBuyPress

            bMoney <- accumB 5 $ unions
                [ (+1) <$ eCoin
                , subtract <$> bPrice <@ eBananaSold
                ]

            let showDialog :: IO ()
                showDialog = infoDialog f "Yummy" "You bought a banana."

            sink buy [ enabled :== bCanBuy ]
            sink money [ text :== show <$> bMoney ]

            reactimate (showDialog <$ eBananaSold)

    eventNetwork <- compile coinMachine
    actuate eventNetwork
