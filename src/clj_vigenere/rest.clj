(ns clj-vigenere.rest
   (:use compojure.core
     ring.middleware.json)
   (:require [compojure.handler :as handler]
             [compojure.route :as route]
             [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
             [ring.util.response :refer [response]]
             [ring.middleware.logger :as logger]
             [clojure.tools.logging :as log]
             [clj-vigenere.core :refer :all]))

(defroutes app-routes
  (POST "/encrypt" request
    (let
      [alphabet (-> request :body :alphabet)
       key (-> request :body :key)
       plaintext (-> request :body :plaintext)]
      (response (encrypt-decrypt (encryption-table alphabet) key plaintext))))
  (route/not-found (response {:message "Page not found"})))

(defn wrap-exception-handling [handler]
  (fn [request]
    (try
      (handler request)
      (catch Exception e
        (log/info "wrap-exception-handling" (str e))
        {:status 404, :body "Item not found"}))))

(def app
  (-> app-routes
    logger/wrap-with-logger
    (wrap-json-body {:keywords? true})
    wrap-json-response
    wrap-exception-handling
    (wrap-defaults api-defaults)))

  
