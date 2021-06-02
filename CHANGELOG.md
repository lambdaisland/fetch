# 1.0.33 (2021-06-02 / cfb45a8)

## Added

- Added optional EDN support. Require the `lambdaisland.fetch.edn` namespace, this will register the necessary multimethods.

# 0.0.23 (2021-01-04 / b8a521a)

## Fixed

- Fix query-params encoding issue and path/query-params normalization (@den1k)

## Changed

- Dependency version bumps: lambdaisland/uri, js-interop, transit-cljs
- Remove direct dependency on Clojure/ClojureScript, people will generally bring them themselves

# 0.0.16 (2020-12-01 / d5f92bd)

## Changed

* Replaced `kitchen-async` dependency with `mhuebert/kitchen-async`, which is the same lib with a fixed version on Clojars