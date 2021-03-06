(ns fb-bot.routes.services
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [fb-bot.facebook :as fb]
            [schema.core :as s]))


(s/defschema FB-Event {:object String
                       :entry [{:messaging [{:sender {:id String}
                                             :recipient {:id String}
                                             :timestamp Long
                                             :message {:text String
                                                       :mid String
                                                       :seq Integer}}]
                                :time Long
                                :id String}]})


(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "FBBot API"
                           :description "Sample fb bot"}}}}

  (context "/api" []
    :tags ["facebook bot"]

    (GET "/facebook" []
         :query [query {:hub.challenge String
                        :hub.mode String
                        :hub.verify_token String}]
         :summary "checking token"
         (fb/subscribe query fb/TOKEN))

    (POST "/facebook" []
          :body [event FB-Event]
          :summary "fb event handler"
          (fb/recive event fb/PROFILE_TOKEN))))
