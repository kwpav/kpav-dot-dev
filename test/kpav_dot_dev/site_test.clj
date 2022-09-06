(ns kpav-dot-dev.site-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kpav-dot-dev.site :as site]))

(deftest valid-site?
  (testing "Valid site returns true"
    (let [example-page {:name "page-one"
                        :location "blog"
                        :content [:div {} [:h1 {} "Page One"]]}
          example-site {:pages [example-page]}]
      (is (= true (site/valid? example-site))))
    (testing "Site with invalid page returns false"
      (let [example-page {:name "page-One"
                          :location "folder-name"
                          :content [:div {} [:h1 {} "Page One"]]}
            example-site {:pages [example-page]}]
        (is (= false (site/valid? example-site)))))))
