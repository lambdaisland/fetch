# Unreleased

## Changed

- Passed in query-params now get merged into query parameters on the URL, rather
  than replacing them.

# 1.0.41 (2021-09-07 / 6696b7a)

## Added

- Added support for all options that `js/fetch` understands. Option values can
  be supplied as keyword or string. `:headers` is expected to be a Clojure map
  from string to string: `:headers`, `:redirect`, `:mode`, `:cache`,
  `:credentials`, `:referrer-policy`
- There is now encoding implemented for `:content-type :form-encoded`

## Changed

- Supplying a body as a string will not encode it, but use the string unchanged
  as the body

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
