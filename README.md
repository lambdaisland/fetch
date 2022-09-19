# lambdaisland/fetch

<!-- badges -->
[![cljdoc badge](https://cljdoc.org/badge/lambdaisland/fetch)](https://cljdoc.org/d/lambdaisland/fetch) [![Clojars Project](https://img.shields.io/clojars/v/lambdaisland/fetch.svg)](https://clojars.org/lambdaisland/fetch)
<!-- /badges -->

ClojureScript wrapper around the JavaScript fetch API.

``` clojure
(require '[lambdaisland.fetch :as fetch])

(fetch/get "/foo.json")
#<promise
  {:status 200
   :headers {...}
   :body #js {...}}>

(fetch/post "/foo.transit" {:query-params {:foo "123"}
                            :body {:hello "world"}})
#<promise
  {:status 200
   :headers {...}
   :body #js {...}}>
```

A more typical ClojureScript example
```clojure
(ns fetch.demo.core
  (:require [kitchen-async.promise :as p]
            [lambdaisland.fetch :as fetch]))

(p/try
  (p/let [resp (fetch/get
                "https://api.github.com/users/seisvelas/gists"
                {:accept :json
                 :content-type :json})]
    (prn (:body resp)))
  (p/catch :default e
     ;; log your exception here
    (prn :error e)))
```

An example of using fetch at the REPL
```clojure

(p/let [res (fetch ...)]
  (def res res))
```
After that you have your response map in `res` and you can inspect it to see what is in there.

- Simply uses promises (add [kitchen-async](https://github.com/athos/kitchen-async) if you like it sweeter)
- Returns a promise which delivers something akin to a ring response map
- Does basic content negotiation and encoding/decoding of request/response body
- Defaults to Transit
- Will encode/decode transit-json, json, or EDN

EDN support is opt-in, since it can increase your build size, and is not
typically needed or wanted for a production setup. Require
`lambdaisland.fetch.edn` to enable it.

## Options

`*` = default

- `:content-type`: determines the encoding of the request body and the content type header on the request. `:transit-json`, `:json`, `:edn`, `:form-encoded`, `:text`, `:html`
- `:accept`: determines the requested encoded that the server should return.
  Decoding is based on the content-type header in the response. Same values as
  `:content-type`
- `:body`: request body to be encoded. If supplied with a string it will be used as-is, otherwise it gets encoded based on `:content-type`

- `:mode`: `:no-cors`, *`:cors`, `same-origin`
- `:cache`: *`:default`, `:no-cache`, `:reload`, `:force-cache`, `:only-if-cached`
- `:credentials` : `:include`, *`:same-origin`, `:omit`
- `:redirect` : `:manual`, *`:follow`, `:error`
- `:referrer-policy` : `:no-referrer`, *`:client`
- `:headers`: map from string to string, note that the server must supply
  `Access-Control-Allow-Headers` in a preflight response
- `:body`: Clojure data structure to be encoded based on the `:content-type`
- `:transit-json-writer`: a custom transit writer `(t/writer :json your-opts)` if you want to add additional handlers or options
- `:transit-json-reader`: a custom transit reader `(t/reader :json your-opts)` 

## Examples

A simple JSON get request.

``` clojure
(require '[lambdaisland.fetch :as fetch])
(require '[goog.object :as gobj])

(-> (fetch/get "https://reqres.in/api/users/2")
    (.then (fn [resp]
             (-> resp
                 :body
                 ;; the actual response is a js object, not a clojure map
                 (gobj/get "data"))))
    (.then (fn [data]
             (js/console.log (gobj/get data "id")))))
```

Same example as above but using `kitchen-async`:

``` clojure
(require '[lambdaisland.fetch :as fetch])
(require '[kitchen-async.promise :as p])
(require '[goog.object :as gobj])

(p/let [resp (fetch/get "https://reqres.in/api/users/2")
        data (-> resp
                 :body
                 (gobj/get "data"))]
  (js/console.log (gobj/get data "id")))
```

<!-- opencollective -->
## Lambda Island Open Source

<img align="left" src="https://github.com/lambdaisland/open-source/raw/master/artwork/lighthouse_readme.png">

&nbsp;

fetch is part of a growing collection of quality Clojure libraries created and maintained
by the fine folks at [Gaiwan](https://gaiwan.co).

Pay it forward by [becoming a backer on our Open Collective](http://opencollective.com/lambda-island),
so that we may continue to enjoy a thriving Clojure ecosystem.

You can find an overview of our projects at [lambdaisland/open-source](https://github.com/lambdaisland/open-source).

&nbsp;

&nbsp;
<!-- /opencollective -->

<!-- contributing -->
## Contributing

Everyone has a right to submit patches to fetch, and thus become a contributor.

Contributors MUST

- adhere to the [LambdaIsland Clojure Style Guide](https://nextjournal.com/lambdaisland/clojure-style-guide)
- write patches that solve a problem. Start by stating the problem, then supply a minimal solution. `*`
- agree to license their contributions as MPL 2.0.
- not break the contract with downstream consumers. `**`
- not break the tests.

Contributors SHOULD

- update the CHANGELOG and README.
- add tests for new functionality.

If you submit a pull request that adheres to these rules, then it will almost
certainly be merged immediately. However some things may require more
consideration. If you add new dependencies, or significantly increase the API
surface, then we need to decide if these changes are in line with the project's
goals. In this case you can start by [writing a pitch](https://nextjournal.com/lambdaisland/pitch-template),
and collecting feedback on it.

`*` This goes for features too, a feature needs to solve a problem. State the problem it solves, then supply a minimal solution.

`**` As long as this project has not seen a public release (i.e. is not on Clojars)
we may still consider making breaking changes, if there is consensus that the
changes are justified.
<!-- /contributing -->

<!-- license-mpl -->
## License

Copyright &copy; 2020-2021 Arne Brasseur and Contributors

Licensed under the term of the Mozilla Public License 2.0, see LICENSE.
<!-- /license-mpl -->
