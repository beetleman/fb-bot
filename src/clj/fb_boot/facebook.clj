(ns fb-boot.facebook)


(def TOKEN "1q2w3e4r5t6y7u8i9o0p")


(defn valid-subscribe? [data token]
  (and (= (:hub.mode data) "subscribe")
       (= (:hub.verify_token data) token)))


(defn subscribe [data token]
  (if (valid-subscribe? data token)
    {:status 200 :body (:hub.challenge data)}
    {:status 403}))
