(ns lambdaisland.fetch-test
  (:require [clojure.test :refer [deftest testing is are use-fixtures run-tests join-fixtures async]]
            [kitchen-async.promise :as p]
            [lambdaisland.fetch :as fetch]
            [applied-science.js-interop :as j]))

;; cd test_server
;; clj -X:run

(deftest transit-default
  (async
   done
   (p/let [res (fetch/get "http://localhost:9999/hello")]
     (is (= {:status 200, :headers {"content-length" "24", "content-type" "application/transit+json; charset=utf-8"}, :body {:hello "world"}}
            res))
     (done))))

(deftest json-support
  (async
   done
   (p/let [res (fetch/post "http://localhost:9999/echo"
                           {:accept :json
                            :content-type :json
                            :body {:msg "Here it goes"}})]
     (is (= "application/json;charset=utf-8"
            (get-in res [:headers "content-type"])))
     (is (= "Here it goes"
            (j/get-in (:body res) [:body-params :msg])))
     (is (= "application/json"
            (j/get-in (get-in res [:body]) [:headers :content-type])))
     (is (= "application/json"
            (j/get-in (get-in res [:body]) [:headers :accept])))
     (done))))

(deftest custom-header
  (async
   done
   (p/let [res (fetch/post "http://localhost:9999/echo"
                           {:headers
                            {"X-Extra-Header" "Mango chutney"}})]
     (is (= "Mango chutney"
            (get-in res [:body :headers "x-extra-header"])))
     (done))))
