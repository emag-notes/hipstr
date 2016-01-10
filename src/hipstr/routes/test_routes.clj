(ns hipstr.routes.test-routes
  (:require [compojure.core :refer :all]))

(defn render-request-val [request-map & [request-key]]
  (str (if request-key
         (if-let [result ((keyword request-key) request-map)]
           result
           (str request-key " is not a valid key."))
         request-map)))

(defroutes test-routes
           (POST "/req" request (render-request-val request))
           ;(GET "/req/:val" [val] (str val))
           (GET "/req/:val" [val :as full-req] (str val "<br/>" full-req))
           (GET "/req/:val/:another-val/:and-another" [val & remainders]
             (str val "<br/>" remainders))
           (GET "/req/key/:key" [key :as request]
             (render-request-val request key)))
