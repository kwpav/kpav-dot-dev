(ns kpav-dot-dev.page-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kpav-dot-dev.page :as page]))

(deftest valid-page?
  (testing "Page with invalid name - 'page-One' - returns false"
      (let [example-page {:name "page-One"
                          :location "folder-name"
                          :content [:div {} [:h1 {} "Page One"]]}]
        (is (= false (page/valid? example-page)))))
  (testing "Page with invalid hiccup in :content returns false"
      (let [example-page {:name "page-one"
                          :location "folder-name"
                          :content {:h1 "Page One"}}]
        (is (= false (page/valid? example-page)))))
  (testing "Page with location - 'Folder-name' - returns false"
      (let [example-page {:name "page-one"
                          :location "Folder-name"
                          :content {:h1 "Page One"}}]
        (is (= false (page/valid? example-page))))))
