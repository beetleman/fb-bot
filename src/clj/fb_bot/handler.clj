(ns fb-bot.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [fb-bot.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [fb-bot.env :refer [defaults]]
            [mount.core :as mount]
            [fb-bot.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    #'service-routes
    (route/not-found
      "page not found")))


(defn app [] (middleware/wrap-base #'app-routes))
