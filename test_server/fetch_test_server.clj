(ns fetch-test-server
  (:require [lambdaisland.webstuff.http :as http]
            [clojure.pprint :as pprint]))

(defn response [r]
  (merge
   {:status 200
    :headers {"Access-Control-Allow-Origin" "*"
              "Access-Control-Allow-Headers" "Content-Type, Accept, X-Extra-Header"}
    :view (fn [body]
            [:pre (with-out-str (pprint/pprint body))])}
   r))

(defn run-test-server [_]
  (println "Starting test server...")
  (http/start-jetty!
   {:port 9999
    :build-handler
    #(http/ring-handler
      {:routes
       [["/hello"
         {:name :hello
          :handler
          (fn [req]
            (response
             {:body {:hello "world"}
              :view (fn [body]
                      [:p "Hello, " (:hello body) "!"])}))}]
        ["/echo"
         {:name :echo
          :handler
          (fn [req]
            (response
             {:body (select-keys req [:params
                                      :form-params
                                      :query-params
                                      :body-params
                                      :headers])}))}]
        ["/exit"
         {:name :exit
          :handler
          (fn [_]
            (println "Exiting...")
            (System/exit 0)
            {:status 200
             :body "OK"})}]]})}))
