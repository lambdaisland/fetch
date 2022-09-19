#!/bin/bash

pushd test_server
exec clojure -X:run
