{-# LANGUAGE CPP #-}
{-# LANGUAGE NoRebindableSyntax #-}
{-# OPTIONS_GHC -fno-warn-missing-import-lists #-}
module Paths_addition (
    version,
    getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir,
    getDataFileName, getSysconfDir
  ) where

import qualified Control.Exception as Exception
import Data.Version (Version(..))
import System.Environment (getEnv)
import Prelude

#if defined(VERSION_base)

#if MIN_VERSION_base(4,0,0)
catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
#else
catchIO :: IO a -> (Exception.Exception -> IO a) -> IO a
#endif

#else
catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
#endif
catchIO = Exception.catch

version :: Version
version = Version [0,1,0,0] []
bindir, libdir, dynlibdir, datadir, libexecdir, sysconfdir :: FilePath

bindir     = "C:\\Users\\Bora\\Desktop\\HPFFP\\addition\\.stack-work\\install\\ca5e0e29\\bin"
libdir     = "C:\\Users\\Bora\\Desktop\\HPFFP\\addition\\.stack-work\\install\\ca5e0e29\\lib\\x86_64-windows-ghc-8.4.3\\addition-0.1.0.0-G9end3dkpiSAZgnmb9zNHE"
dynlibdir  = "C:\\Users\\Bora\\Desktop\\HPFFP\\addition\\.stack-work\\install\\ca5e0e29\\lib\\x86_64-windows-ghc-8.4.3"
datadir    = "C:\\Users\\Bora\\Desktop\\HPFFP\\addition\\.stack-work\\install\\ca5e0e29\\share\\x86_64-windows-ghc-8.4.3\\addition-0.1.0.0"
libexecdir = "C:\\Users\\Bora\\Desktop\\HPFFP\\addition\\.stack-work\\install\\ca5e0e29\\libexec\\x86_64-windows-ghc-8.4.3\\addition-0.1.0.0"
sysconfdir = "C:\\Users\\Bora\\Desktop\\HPFFP\\addition\\.stack-work\\install\\ca5e0e29\\etc"

getBinDir, getLibDir, getDynLibDir, getDataDir, getLibexecDir, getSysconfDir :: IO FilePath
getBinDir = catchIO (getEnv "addition_bindir") (\_ -> return bindir)
getLibDir = catchIO (getEnv "addition_libdir") (\_ -> return libdir)
getDynLibDir = catchIO (getEnv "addition_dynlibdir") (\_ -> return dynlibdir)
getDataDir = catchIO (getEnv "addition_datadir") (\_ -> return datadir)
getLibexecDir = catchIO (getEnv "addition_libexecdir") (\_ -> return libexecdir)
getSysconfDir = catchIO (getEnv "addition_sysconfdir") (\_ -> return sysconfdir)

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir ++ "\\" ++ name)
