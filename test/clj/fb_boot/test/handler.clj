(ns fb-boot.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [fb-boot.facebook :as fb]
            [fb-boot.handler :refer :all]))

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
      (is (= (str \" subscribe-challenge \") (-> response :body slurp))))))
