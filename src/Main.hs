{-# LANGUAGE DeriveDataTypeable #-}

module Main where
import MFlow.Wai.Blaze.Html.All
import Data.Typeable

data Menu = About | SumIt | Help
    deriving (Typeable, Show)

{- instance Typeable Menu where
    typeOf About = mkTyCon "About"
    typeOf SumIt = mkTyCon "SumIt"
    typeOf Help = mkTyCon "Help"
-}

main = runNavigation "" . transientNav $ Main.menu


menu = do
    r <- page $ h3 << "Menu"
              ++> wlink About << b << "About" <++ br
              <|> wlink SumIt << b << "Sumit" <++ br
              <|> wlink Help << b << "Help"
    case r of 
        About -> do 
            page $ p << "Something to say"
                 ++> wlink () << p << "Home"
            return ()
        SumIt -> sumIt
        Help -> do
            page $ p << "What can I do for you?"
                 ++> stop
            return ()

sumIt = do 
    setHeader $ html . body
    n1 <- ask $ p << "First number"
              ++> getInt Nothing
              <** submitButton "send"
    n2 <- ask $ p << "Second number"
              ++> getInt Nothing
              <** submitButton "send"
    ask $ p << ("The result is " ++ show (n1 + n2))
        ++> wlink () << p << "click here"


