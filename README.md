# lambdaisland/fetch

<!-- badges -->
[![CircleCI](https://circleci.com/gh/lambdaisland/fetch.svg?style=svg)](https://circleci.com/gh/lambdaisland/fetch) [![cljdoc badge](https://cljdoc.org/badge/lambdaisland/fetch)](https://cljdoc.org/d/lambdaisland/fetch) [![Clojars Project](https://img.shields.io/clojars/v/lambdaisland/fetch.svg)](https://clojars.org/lambdaisland/fetch)
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

- Simply uses promises (add [kitchen-async](https://github.com/athos/kitchen-async) if you like it sweeter)
- Returns a promise which delivers something akin to a ring response map
- Does basic content negotiation and encoding/decoding of request/response body
- Defaults to Transit

<!-- opencollective -->

&nbsp;

<img align="left" src="https://github.com/lambdaisland/open-source/raw/master/artwork/lighthouse_readme.png">

&nbsp;

## Support Lambda Island Open Source

fetch is part of a growing collection of quality Clojure libraries and
tools released on the Lambda Island label. If you are using this project
commercially then you are expected to pay it forward by
[becoming a backer on Open Collective](http://opencollective.com/lambda-island#section-contribute),
so that we may continue to enjoy a thriving Clojure ecosystem.

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

Copyright &copy; 2020 Arne Brasseur and Contributors

Licensed under the term of the Mozilla Public License 2.0, see LICENSE.
<!-- /license-mpl -->