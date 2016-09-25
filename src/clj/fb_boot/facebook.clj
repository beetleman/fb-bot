(ns fb-boot.facebook
  (:require [aleph.http :as http]
            [fb-boot.config :refer [env]]))

(def TOKEN (get env :facebook-acces-token "example"))
(def PROFILE_TOKEN (get env :facebook-profile-token "example"))

(defn valid-subscribe? [data token]
  (and (= (:hub.mode data) "subscribe")
       (= (:hub.verify_token data) token)))


(defn subscribe [data token]
  (if (valid-subscribe? data token)
    {:status 200 :body (:hub.challenge data)}
    {:status 403}))


(defn call-messages-api [message token]
  (let [payload {:content-type :json
                 :query-params {:access_token token}
                 :json-opts message}]
    (http/post "https://graph.facebook.com/v2.6/me/messages" payload)))


(defn send-text-message [text id token]
  (call-messages-api {:recipient {:id id}
                      :message {:text text}}))


(defn message-handler* [message-event]
  (cond
    (:message message-event)
    (send-text-message (get-in message-event [:message :text])
                       (get-in message-event [:sender :id])
                       PROFILE_TOKEN)))

(defn messages-handler [messages]
  (do (map message-handler* messages)))


(defn recive [data token]
  (when (= "page" (:object data))
    (do (map messages-handler (:entry data))))
  {:status 200})
