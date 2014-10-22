module Main where
import MFlow.Wai.Blaze.Html.All

main = runNavigation "sum" . transientNav $ sumIt

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


