(ns kpav-dot-dev.core
  (:require [hiccup2.core :as h]
            [kpav-dot-dev.schema :as schema]
            [kpav-dot-dev.pages.index :as index]
            [malli.core :as m]))

(defn htmlize
  "Turn all :pages :content from Hiccup to HTML."
  [site]
  (letfn [(htmlize-page [page]
            (update page :content #(h/html %)))
          (htmlize-pages [pages]
            (map #(htmlize-page %) pages))]
    (update site
            :pages
            htmlize-pages)))

(defn spit-site
  "Write the site to `dist/."
  [site]
  (->> site
       :pages
       (map #(spit (str "dist/" (:name %) ".html") (:content %)))))

(defn build-site
  [site]
  (-> site
      htmlize
      spit-site))

(def site
  {:pages [index/page]})

(defn main []
  (if (schema/valid-site? site)
    (build-site site)
    (schema/invalid-site site)))

(comment
  (main)

  (def example-page
    {:name    "page_one"
     :content [:div {} [:h1 {} "Page One"] [:p {} "Page one text."]]})

  (def example-page2
    {:name    "page_two"
     :content [:div {} [:h1 {} "Page Two"] [:p {} "Page two text."]]})

  (def example-site
    {:pages [example-page example-page2]})

  (schema/valid-site? example-site)

  (build-site example-site)

  ;; (m/validate Page example-page)

  ;; (m/validate Site example-site)

  (m/validate
   [:map
    [:pages
      [:map
       [:name string?]
       [:content vector?]]]]
   example-site)

;; (m/validate Hiccup {:a :b})
;; (m/validate Hiccup (:content example-page))
,)
