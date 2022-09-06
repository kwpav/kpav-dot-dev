#!/usr/bin/env bb

(require '[clojure.test :as t]
         '[babashka.classpath :as cp])

(cp/add-classpath "src:test")

(require 'kpav-dot-dev.schema-test
         'kpav-dot-dev.pages-test
         'kpav-dot-dev.components-test)

(def test-results
  (t/run-tests 'kpav-dot-dev.schema-test
               'kpav-dot-dev.pages-test
               'kpav-dot-dev.components-test))

(def failures-and-errors
  (let [{:keys [:fail :error]} test-results]
    (+ fail error)))

(System/exit failures-and-errors)
