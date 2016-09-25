(ns fb-boot.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [fb-boot.db.core :as db]
            [fb-boot.facebook :as fb]
            [schema.core :as s]))

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "FBBot API"
                           :description "Sample fb bot"}}}}

  (context "/api" []
    :tags ["facebook boot"]

    (GET "/facebook" []
         :return String
         :query [query {:hub.challenge String
                        :hub.mode String
                        :hub.verify_token String}]
         :summary "checking token"
         (fb/subscribe query fb/TOKEN))))
