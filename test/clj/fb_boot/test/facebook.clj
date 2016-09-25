(ns fb-boot.test.facebook
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [fb-boot.facebook :as fb]))

(def TOKEN "1234qwer")

(deftest test-facebook-token-validation
  (testing "invalid token"
    (is (= (fb/valid-subscribe? {} TOKEN) false))
    (is (= (fb/valid-subscribe? {:hub.mode "subscribe"} TOKEN) false))
    (is (= (fb/valid-subscribe? {:hub.mode "subscribe"} TOKEN) false))
    (is (= (fb/valid-subscribe? {:hub.mode "subscribe"
                                 :hub.verify_token "aa"} TOKEN) false)))

  (testing "valid token"
    (is (= (fb/valid-subscribe? {:hub.mode "subscribe"
                                 :hub.verify_token TOKEN} TOKEN) true))))
