# lambdaisland/fetch

<!-- badges -->
[![CircleCI](https://circleci.com/gh/lambdaisland/fetch.svg?style=svg)](https://circleci.com/gh/lambdaisland/fetch) [![cljdoc badge](https://cljdoc.org/badge/lambdaisland/fetch)](https://cljdoc.org/d/lambdaisland/fetch) [![Clojars Project](https://img.shields.io/clojars/v/lambdaisland/fetch.svg)](https://clojars.org/lambdaisland/fetch)
<!-- /badges -->

ClojureScript wrapper around the JavaScript fetch API.

```
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

- Simply uses promises (add [kitchen-async](https://github.com/athos/kitchen-async) if you like it sweeter)
- Returns a promise which delivers something akin to a ring response map
- Does basic content negotiation and encoding/decoding of request/response body
- Defaults to Transit

## License

Copyright &copy; 2020 Arne Brasseur

Licensed under the term of the Mozilla Public License 2.0, see LICENSE.

Available under the terms of the Eclipse Public License 1.0, see LICENSE.txt
