(ns jenatelmel.routes.home
  (:require [compojure.core :refer :all]
            [jenatelmel.models.db :as db]
            [jenatelmel.views.layout :as layout]
            [hiccup.form :refer :all]))

(defn format-time [timestamp]
  (-> "dd/MM/yyyy"
      (java.text.SimpleDateFormat.)
      (.format timestamp)))

(defn show-contact-requests []
  [:ul.contact
   (for [{:keys [phone email message creation_utc]} (db/read-contact-requests)]
     [:li
      [:blockquote message]
      [:p "-" [:cite email]]
      [:p "-" [:cite phone]]
      [:time creation_utc]])])



(defn home [& [phone email message error]]
  (layout/common
   [:h1 "SheepShelve"]
   [:p "Welcome to the sheep shelve"]
   (show-contact-requests)
   [:hr]
   [:p error]
   (form-to [:post "/"]
            [:p "Phone:"]
            (text-field "phone" phone)
            [:p "Email:"]
            (text-field "email" email)
            [:p "Message:"]
            (text-area {:rows 10 :cols 40} "message" message)
            [:br]
            (submit-button "Request contact"))))

(defn save-request-contact [phone email message]
  (cond
    (and (empty? phone) (empty? email))
     (home phone email message "Missing one of email or phone")

    :else
    (do
      (db/save-contact-request phone email message)
      (home))))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" [phone email message] (save-request-contact phone email message)))
