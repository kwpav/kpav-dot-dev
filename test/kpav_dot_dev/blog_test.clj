(ns kpav-dot-dev.blog-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kpav-dot-dev.blog :as blog]
   [kpav-dot-dev.html :as html]))

(deftest make-blog
  (testing "Does make-blog make valid content given valid directories"
    (let [opts           {:content-dir "content"
                          :blog        {:content-dir "blog"
                                        :out-dir     "blog"}}
          [blog-summary
           & blog-posts] (blog/make-blog opts)]
      (is (= true (html/valid? (:content blog-summary))))
      ;; should this be :hiccup-seq?
      (is (= true (every? #(html/valid? (:content %) :hiccup)
                          blog-posts))))))
