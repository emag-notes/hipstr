(ns hipstr.routes.access
  (:require [hipstr.models.user-model :refer [is-authed?]]))

(def rules
  "The rules for accessing various routes in out application"
  [{:redirect "/login" :rule is-authed?}])
