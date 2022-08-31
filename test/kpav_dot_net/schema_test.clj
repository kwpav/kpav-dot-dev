(ns kpav-dot-net.schema-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kpav-dot-dev.schema :as schema]))

(deftest valid-site?
  (testing "Valid site returns true"
    (let [example-page {:name "page_one"
                        :content [:div {} [:h1 {} "Page One"]]}
          example-site {:pages [example-page]}]
      (is (= true (schema/valid-site? example-site))))
    (testing "Site with invalid page name - 'page_One' - returns false"
      (let [example-page {:name "page_One"
                          :content [:div {} [:h1 {} "Page One"]]}
            example-site {:pages [example-page]}]
        (is (= false (schema/valid-site? example-site)))))
    (testing "Site containing page with invalid hiccup returns false"
      (let [example-page {:name "page_one"
                          :content {:h1 "Page One"}}
            example-site {:pages [example-page]}]
        (is (= false (schema/valid-site? example-site)))))))
