(ns hipstr.routes.albums
  (:require [compojure.core :refer :all]
            [hipstr.layout :as layout]
            [hipstr.models.album-model :as album]))

(defn recently-added-page
  "Renders out the recently-added page."
  []
  (layout/render "albums/recently-added.html"
                 {:albums (album/get-recently-added)}))

(defroutes album-routes
           (GET "/albums/recently-added" [] (recently-added-page)))
