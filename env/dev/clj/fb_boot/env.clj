(ns fb-boot.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [fb-boot.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[fb-boot started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[fb-boot has shut down successfully]=-"))
   :middleware wrap-dev})
