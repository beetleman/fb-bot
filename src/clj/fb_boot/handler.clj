(ns fb-boot.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [fb-boot.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [fb-boot.env :refer [defaults]]
            [mount.core :as mount]
            [fb-boot.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    #'service-routes
    (route/not-found
      "page not found")))


(defn app [] (middleware/wrap-base #'app-routes))
