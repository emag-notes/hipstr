(ns hipstr.models.album-model
  (:require [hipstr.models.connection :refer [db-spec]]
            [clojure.java.jdbc :as jdbc])
  (:use [korma.core]
        [korma.db]))

(declare artists albums)

(defentity artists
           (pk :artist_id)
           (has-many albums))

(defentity albums
           (pk :album_id)
           (belongs-to artists {:fk :artist_id}))

(defn get-recently-added
  "Gets the 10 most recently added albums in the db."
  []
  (select albums
          (fields :album_id
                  [:name :album_name] :release_date :created_at)
          (with artists (fields [:name :artist]))
          (order :created_at :DESC)
          (limit 10)))

(defn get-discographies-by-artist-name
  "Gets the discography for a given artist."
  [artist]
  (select albums
          (join artists)
          (fields :albums.album_id [:albums.name :album_name]
                  :albums.release_date)
          (where {:artists.name (:artist artist)})
          (order :release_date :DESC)))

(defn insert-album<!
  "Adds the album for the given artist to the database."
  [album]
  (let [album (-> (clojure.set/rename-keys album {:album_name :name})
                  (dissoc :artist_name)
                  (assoc :release_date
                         (sqlfn date (:release_date album))))]
    (insert albums (values album))))

(defn get-album-by-name
  "Fetches the specific album from the database for a particular
  artist."
  ; for backwards compatibility it is expected that the
  ; album param is {:artist_id :artist_name}
  [album]
  (first
    (select albums
            (where {:artist_id (:artist_id album)
                    :name (:artist_name album)}))))

(defn insert-artist<!
  "Inserts a new artist into the database."
  ; for backwards compatibility it is expected that the
  ; artist param is {:artist_name}
  [artist]
  (let [artist (clojure.set/rename-keys
                 artist {:artist_name :name})]
    (insert artists (values artist))))

(defn get-artist-by-name
  "Retrieves an artist from the database by name."
  ;for backwards compatibility it is expected that the
  ; artist_name param is {:artist_name}
  [artist_name]
  (first
    (select artists
            (where {:name (:artist_name artist_name)}))))

(defn add-album!
  "Adds a new album to the database."
  [album]
  (transaction
    (let [artist-info {:artist_name (:artist_name album)}
          ; fetch or insert the artist record
          artist (or (get-artist-by-name artist-info)
                     (insert-artist<! artist-info))
          album-info (assoc album :artist_id (:artist_id artist))]
      (or (get-album-by-name album-info)
          (insert-album<! album-info)))))