(ns hipstr.routes.albums
  (:require [compojure.core :refer :all]
            [hipstr.layout :as layout]
            [hipstr.models.album-model :as album]))

(defn recently-added-page
  "Renders out the recently-added page."
  []
  (layout/render "albums/recently-added.html"
                 {:albums (album/get-recently-added)}))

(defn discographies-page
  "Renders out the discographies page."
  [artist]
  (layout/render "albums/discographies.html"
                 {:discographies (album/get-discographies-by-artist-name {:artist_name artist})
                  :artist artist}))

(defroutes album-routes
           (GET "/albums/recently-added" [] (recently-added-page))
           (GET "/albums/:artist" [artist] (discographies-page artist)))
