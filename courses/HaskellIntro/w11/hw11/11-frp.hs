{-# LANGUAGE RecursiveDo #-}

module Main where
import Graphics.UI.WX hiding (Event)
import Reactive.Banana
import Reactive.Banana.WX

eventSlider :: Slider a -> MomentIO (Event Int)
eventSlider w = do
    addHandler <- liftIO $ event1ToAddHandler w (event0ToEvent1 command)
    fromAddHandler $ mapIO (const $ get w selection) addHandler

main :: IO ()
main = start hello

hello :: IO ()
hello = do
    f     <- frame      [text := "WX Demo"]
    money <- textEntry f []
    price <- textEntry f []
    priceS <- hslider f True 1 20 []

    add   <- button f   [text := "Insert coin"]
    buy   <- button f   [text := "Buy banana"]

    quit  <- button f   [text := "Quit", on command := close f]

    set f [layout :=
        margin 5 $ column 5 $ map hfill
            [ widget add
            , widget buy
            , widget money
            , widget price
            , widget priceS
            , widget quit
            ]]

    let coinMachine :: MomentIO ()
        coinMachine = mdo
            eCoin <- event0 add command
            eBuyPress <- event0 buy command

            bSlider <- behavior priceS selection
            ePriceChange <- eventSlider priceS

            bPrice <- accumB 7 $ unions
                [ (min 10) . (+1) <$ eBananaSold
                , const <$> ePriceChange
                ]

            let bCanBuy     = (>=) <$> bMoney <*> bPrice
                eBananaSold = whenE bCanBuy eBuyPress

            bMoney <- accumB 5 $ unions
                [ (+1) <$ eCoin
                , subtract <$> bPrice <@ eBananaSold
                ]

            let showDialog :: IO ()
                showDialog = infoDialog f "Yummy" "You bought a banana."

            sink buy [ enabled :== bCanBuy ]
            sink money [ text :== show <$> bMoney ]
            sink price [ text :== show <$> bPrice ]
            sink priceS [ selection :== bPrice ]

            reactimate (showDialog <$ eBananaSold)

    eventNetwork <- compile coinMachine
    actuate eventNetwork
