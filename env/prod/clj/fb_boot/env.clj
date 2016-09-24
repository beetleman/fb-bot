(ns fb-boot.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[fb-boot started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[fb-boot has shut down successfully]=-"))
   :middleware identity})
