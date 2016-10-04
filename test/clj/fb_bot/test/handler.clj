(ns fb-bot.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [fb-bot.facebook :as fb]
            [fb-bot.handler :refer :all]))

(def subscribe-challenge "qwerty")
(def subscribe-url
  (str "/api/facebook?"
       "hub.challenge=" subscribe-challenge "&"
       "hub.mode=subscribe&"
       "hub.verify_token=" fb/TOKEN))

(deftest test-app
  (testing "subscribe"
    (let [response ((app) (request :get subscribe-url))]
      (is (= 200 (:status response)))
      (is (= subscribe-challenge (-> response :body))))))
