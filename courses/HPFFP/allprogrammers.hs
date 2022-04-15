module Programmers where

data OperatingSystem =
      GnuPlusLinux
    | OpenBSDPlusNevermindJustBSDStill
    | Mac
    | Windows deriving (Eq, Show)

data ProgLang =
      Haskell
    | Agda
    | Idris
    | PureScript deriving (Eq, Show)

data Programmer = Programmer { os :: OperatingSystem, lang :: ProgLang }
                    deriving (Eq, Show)

allOperatingSystems :: [OperatingSystem]
allOperatingSystems =
    [ GnuPlusLinux,
      OpenBSDPlusNevermindJustBSDStill,
      Mac,
      Windows ]

allLanguages :: [ProgLang]
allLanguages = [Haskell, Agda, Idris, PureScript]

allProgrammers :: [Programmer]
allProgrammers = [Programmer {os = x, lang = y} | x <- allOperatingSystems, y <- allLanguages]
