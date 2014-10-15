(ns jenatelmel.models.db
  (:require [clojure.java.jdbc :as sql])
  (:import java.sql.DriverManager))

(def db {:classname    "org.sqlite.JDBC",
         :subprotocol  "sqlite",
         :subname      "db.sq3"})


(defn read-contact-requests []
  (sql/with-connection
    db
    (sql/with-query-results res
      ["SELECT * FROM contact_requests ORDER BY creation_utc DESC"]
      (doall res))))

(defn save-contact-request [phone email message]
  (sql/with-connection
    db
    (sql/insert-values
     :contact_requests
     [:phone :email :message]
     [phone email message])))

(defn create-contact-request-table []
  (sql/with-connection
    db
    (sql/create-table
     :contact_requests
     [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
     [:creation_utc "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
     [:phone "TEXT"]
     [:email "TEXT"]
     [:message "TEXT"])
    (sql/do-commands "CREATE INDEX timestamp_index ON contact_requests (creation_utc)")))
