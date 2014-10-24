{-# LANGUAGE DeriveDataTypeable #-}

module Main where
import MFlow.Wai.Blaze.Html.All
import Data.Typeable

data Menu = About | SumIt | SumIt2 | SumIt3 | Help
    deriving (Typeable, Show)

main = runNavigation "" . step . page . pageFlow "s" $  sumIt3--Main.menu

menu = do
    r <- h3 << "Menu"
              ++> wlink About << b << "About" <++ br
              <|> wlink SumIt << b << "Sumit" <++ br
              <|> wlink SumIt2 << b << "Sumit2" <++ br
              <|> wlink SumIt3 << b << "Sumit3" <++ br
              <|> wlink Help << b << "Help"
    case r of 
        About -> do 
            p << "Something to say"
                 ++> wlink () << p << "Home"
        SumIt -> sumIt
        SumIt3 -> sumIt3
        SumIt2 -> do 
            sumIt2
        Help -> do
            p << "What can I do for you?"
                 ++> stop

sumIt = do 
    return br
    n1 <- p << "First number"
              ++> getInt Nothing
              <** submitButton "send"
    n2 <- p << "Second number"
              ++> getInt Nothing
              <** submitButton "send"
    p << ("The result is " ++ show (n1 + n2))
        ++> wlink () << p << "click here"

sumIt2 = do
    return br
    n <- getInt Nothing <** submitButton "first" <++ br
    n' <- getInt (Just n) <** submitButton "second" <++ br
    p << (n+n') ++> wlink SumIt2 << b << "click to repeat" <++ br
                <** wlink ()  << b << "Home"
    return ()


sumIt3 = do
    n <- p << "First" ++> getInt Nothing <++ br
    n' <- p << "Second" ++> getInt (Just n) <++ br
    p << (n+n') ++> noWidget
    <** br ++> pageFlow "button" (submitButton "submit")
