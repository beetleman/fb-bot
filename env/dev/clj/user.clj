(ns user
  (:require [mount.core :as mount]
            fb-boot.core))

(defn start []
  (mount/start-without #'fb-boot.core/repl-server))

(defn stop []
  (mount/stop-except #'fb-boot.core/repl-server))

(defn restart []
  (stop)
  (start))


