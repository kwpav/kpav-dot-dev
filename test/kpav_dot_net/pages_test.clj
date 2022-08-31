(ns kpav-dot-net.pages-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kpav-dot-dev.schema :as schema]
   [kpav-dot-dev.pages.index :as index]))

(deftest validate-index
  (testing "index is a valid page"
    (is (= true (schema/valid-page? index/page)))))
