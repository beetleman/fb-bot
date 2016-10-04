(ns fb-bot.facebook
  (:require [aleph.http :as http]
            [cheshire.core :as json]
            [fb-bot.config :refer [env]]))


(def TOKEN (or (System/getenv "FACEBOOK_ACCESS_TOKEN")
               "FACEBOOK_ACCESS_TOKEN"))
(def PROFILE_TOKEN (or (System/getenv "FACEBOOK_PROFILE_TOKEN")
                       "FACEBOOK_PROFILE_TOKEN"))


(defn valid-subscribe? [data token]
  (println {:data data :token token})
  (and (= (:hub.mode data) "subscribe")
       (= (:hub.verify_token data) token)))


(defn subscribe [data token]
  (if (valid-subscribe? data token)
    {:status 200 :body (:hub.challenge data)}
    {:status 403}))


(defn call-messages-api [message token]
  (let [payload {:content-type :json
                 :query-params {:access_token token}
                 :body (json/generate-string message)}]
    (http/post "https://graph.facebook.com/v2.6/me/messages" payload)))


(defn send-text-message [text id token]
  (println text id token)
  (call-messages-api {:recipient {:id id}
                      :message {:text text}}
                     token))


(defn message-handler* [message-event]
  (send-text-message (get-in message-event [:message :text])
                     (get-in message-event [:sender :id])
                     PROFILE_TOKEN))

(defn messages-handler [messages]
  (doall (map message-handler* (:messaging messages))))


(defn recive [data token]
  (when (= "page" (:object data))
    (doall (map messages-handler (:entry data))))
  {:status 200})
