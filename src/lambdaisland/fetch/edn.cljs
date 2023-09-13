(ns lambdaisland.fetch.edn
  "EDN read/write support

  Split out so as not to blow up the build if EDN support isn't needed."
  (:require [applied-science.js-interop :as j]
            [clojure.edn :as edn]
            [kitchen-async.promise :as p]
            [lambdaisland.fetch :as fetch]))

(defmethod fetch/encode-body :edn [_ body opts]
  (pr-str body))

(defmethod fetch/decode-body :edn [_ bodyp opts]
  (p/let [text (j/call bodyp :text)]
    (edn/read-string (cond-> {}
                       (:edn/readers opts)
                       (assoc :readers (:edn/readers opts)))
                     text)))
