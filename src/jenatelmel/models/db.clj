(ns jenatelmel.models.db
  (:require [clojure.java.jdbc :as sql])
  (:import java.sql.DriverManager))

(def db {:classname    "org.sqlite.JDBC",
         :subprotocol  "sqlite",
         :subname      "db.sq3"})


(defn create-contact-request-table []
  (sql/with-connection
    db
    (sql/create-table
     :contact_requests
     [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
     [:creation_utc "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
     [:phone "TEXT"]
     [:email "TEXT"]
     [:message "TEST"])
    (sql/do-commands "CREATE INDEX timestamp_index ON contact_requests (creation_utc)")))
