(ns lambdaisland.fetch-test
  (:require
   [applied-science.js-interop :as j]
   [clojure.pprint :as pprint]
   [clojure.test :refer [deftest testing is are use-fixtures run-tests join-fixtures async]]
   [kitchen-async.promise :as p]
   [lambdaisland.fetch :as fetch]))

;; cd test_server
;; clj -X:run

(when-not (exists? js/fetch)
  (set! js/fetch (js/require "node-fetch")))

(deftest transit-default
  (async
   done
   (p/let [res (fetch/get "http://localhost:9999/hello")]
     (is (= "application/transit+json; charset=utf-8" (get-in res [:headers "content-type"])))
     (is (= {:hello "world"} (:body res)))
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

(deftest form-encoded
  (async
   done
   (p/let [res (fetch/post "http://localhost:9999/echo"
                           {:content-type :form-encoded
                            :body {:foo [1 2 3]}})]
     (is (= {"foo" ["1" "2" "3"]}
            (get-in res [:body :form-params])))
     (is (= "application/x-www-form-urlencoded"
            (get-in res [:body :headers "content-type"])))
     (done))))

(deftest add-query-params
  (async
   done
   (p/then
    (p/all
     [(p/let [res (fetch/get "http://localhost:9999/echo?hello=world&b=2"
                             {:query-params {:foo "bar"}})]
        (is (= {"hello" "world", "b" "2", "foo" "bar"}
               (get-in res [:body :params]))))

      (p/let [res (fetch/get "http://localhost:9999/echo?hello=world"
                             {:query-params {:hello "mars" :b 3}})]
        (is (= {"hello" "mars", "b" "3"}
               (get-in res [:body :params]))))

      (p/let [res (fetch/get "http://localhost:9999/echo"
                             {:query-params {:foo "bar"}})]
        (is (= {"foo" "bar"} (get-in res [:body :params]))))

      (p/let [res (fetch/get "http://localhost:9999/echo?x=y")]
        (is (= {"x" "y"} (get-in res [:body :params]))))])
    done)))

(deftest explicit-response-type
  (async
   done
   (p/let [res (fetch/get "http://localhost:9999/hello" {:accept :json :as :text})]
     (is (= "{\"hello\":\"world\"}" (:body res)))
     (done))))
