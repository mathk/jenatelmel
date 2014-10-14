(ns jenatelmel.routes.home
  (:require [compojure.core :refer :all]
            [jenatelmel.views.layout :as layout]))

(defn home []
  (layout/common
   [:h1 "SheepShelve"]
   [:p "Welcome to the sheep shelve"]
   [:p "If you want to contact us:"]
   [:form
    [:p "Name:"]
    [:input  {:class "test" :type "password"}]
    [:p "Message:" ]
    [:textarea {:rows 10 :cols 40 :class "test"}]]))

(defroutes home-routes
  (GET "/" [] (home)))
