(ns kpav-dot-dev.schema-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kpav-dot-dev.schema :as schema]))

#_(deftest valid-site?
  (testing "Valid site returns true"
    (let [example-page {:name "page-one"
                        :location "blog"
                        :content [:div {} [:h1 {} "Page One"]]}
          example-site {:pages [example-page]}]
      (is (= true (schema/valid-site? example-site))))
    (testing "Site with invalid page returns false"
      (let [example-page {:name "page-One"
                          :location "folder-name"
                          :content [:div {} [:h1 {} "Page One"]]}
            example-site {:pages [example-page]}]
        (is (= false (schema/valid-site? example-site)))))))

#_(deftest valid-page?
  (testing "Page with invalid name - 'page-One' - returns false"
      (let [example-page {:name "page-One"
                          :location "folder-name"
                          :content [:div {} [:h1 {} "Page One"]]}]
        (is (= false (schema/valid-page? example-page)))))
  (testing "Page with invalid hiccup in :content returns false"
      (let [example-page {:name "page-one"
                          :location "folder-name"
                          :content {:h1 "Page One"}}]
        (is (= false (schema/valid-page? example-page)))))
  (testing "Page with location - 'Folder-name' - returns false"
      (let [example-page {:name "page-one"
                          :location "Folder-name"
                          :content {:h1 "Page One"}}]
        (is (= false (schema/valid-page? example-page))))))
