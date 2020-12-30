(ns lambdaisland.fetch
  (:refer-clojure :exclude [get])
  (:require [kitchen-async.promise :as p]
            [clojure.core :as c]
            [clojure.string :as str]
            [clojure.set :as set]
            [lambdaisland.uri :as uri]
            [lambdaisland.uri.normalize :as uri-normalize]
            [applied-science.js-interop :as j]
            [cognitect.transit :as transit]))

;; fetch(url, {
;;             method: 'POST', // *GET, POST, PUT, DELETE, etc.
;;             mode: 'cors', // no-cors, *cors, same-origin
;;             cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
;;             credentials: 'same-origin', // include, *same-origin, omit
;;             headers: {
;;                       'Content-Type': 'application/json'
;;                       // 'Content-Type': 'application/x-www-form-urlencoded',
;;                       },
;;             redirect: 'follow', // manual, *follow, error
;;             referrerPolicy: 'no-referrer', // no-referrer, *client
;;             body: JSON.stringify(data) // body data type must match "Content-Type" header
;;             });

(def content-types
  {:transit-json "application/transit+json"
   :json         "application/json"
   :form-encoded "application/x-www-form-urlencoded"
   :text         "text/plain"
   :html         "text/html"})

(def transit-json-writer
  (delay (transit/writer :json)))

(def transit-json-reader
  (delay (transit/reader :json)))

(defmulti encode-body (fn [content-type body opts] content-type))

(defmethod encode-body :default [_ body opts]
  body)

(defmethod encode-body :transit-json [_ body opts]
  (transit/write (:transit-json-writer opts @transit-json-writer) body))

(defmethod encode-body :json [_ body opts]
  (js/JSON.stringify (clj->js body)))

(defmulti decode-body (fn [content-type bodyp opts] content-type))

(defmethod decode-body :default [_ bodyp opts]
  (p/let [body bodyp]
    (j/call bodyp :text)))

(defmethod decode-body :transit-json [_ bodyp opts]
  (p/let [text (j/call bodyp :text)]
    (let [decoded (transit/read (:transit-json-reader opts @transit-json-reader) text)]
      (if (satisfies? IWithMeta decoded)
        (vary-meta decoded assoc ::raw text)
        decoded))))

(defmethod decode-body :json [_ bodyp opts]
  (p/let [body bodyp]
    (j/call bodyp :json)))

(defn fetch-opts [{:keys [method accept content-type]
                   :or   {method       :get
                          accept       :transit-json
                          content-type :transit-json}}]
  #js {:method   (str/upper-case (name method))
       :headers  #js {"Accept"       (c/get content-types accept)
                      "Content-Type" (c/get content-types content-type)}
       :redirect "follow"})

(defn request [url & [{:keys [method accept content-type query-params body]
                       :as   opts
                       :or   {accept       :transit-json
                              content-type :transit-json}}]]
  (let [url     (-> url
                    uri/uri
                    (assoc :query (uri/map->query-string query-params))
                    str)
        request (cond-> (fetch-opts opts)
                  body
                  (j/assoc! :body (encode-body content-type body opts)))]
    (p/let [response (js/fetch url request)]
      (p/try
        (let [headers             (j/get response :headers)
              header-map          (into {} (map vec) (es6-iterator-seq (j/call headers :entries)))
              content-type-header (j/call headers :get "Content-Type")
              content-type        (when content-type-header
                                    (c/get (set/map-invert content-types)
                                           (str/replace content-type-header #";.*" "")))]
          (p/let [body (decode-body content-type response opts)]
            ^{::request  (j/assoc! request :url url)
              ::response response}
            {:status  (j/get response :status)
             :headers header-map
             :body    body}))
        (p/catch :default e
          ^{::request  (j/assoc! request :url url)
            ::response response}
          {:error e})))))

(def get request)

(defn post [url & [opts]]
  (request url (assoc opts :method :post)))

(defn put [url & [opts]]
  (request url (assoc opts :method :put)))

(defn delete [url & [opts]]
  (request url (assoc opts :method :delete)))

(defn head [url & [opts]]
  (request url (assoc opts :method :head)))


(comment
  (p/let [result (get "/as400/paginated/VSBSTAMDTA.STOVKP"
                      {:query-params {:page 1
                                      :page-size 20}})]
    (def xxx result))

  (p/let [body (:body xxx)]
    (def body body))

  (p/let [res (head "/as400/paginated/VSBSTAMDTA.STOVKP")]
    (def xxx res)))
