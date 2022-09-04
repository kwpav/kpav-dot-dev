(ns kpav-dot-dev.core
  (:require
   [clojure.java.io :as io]
   [hiccup2.core :as h]
   [kpav-dot-dev.pages.index :as index]
   [kpav-dot-dev.schema :as schema]))

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

(defn spit-page!
  "Get the full path of the page, make any missing dirs, and write it."
  [{:keys [location name content] :as _page}]
  (let [file-name (str "dist/" location "/" name ".html")]
    (io/make-parents file-name)
    (spit file-name content)))

(defn spit-site!
  "Write the site to `dist/."
  [{:keys [pages] :as _site}]
  (->> pages
       (map spit-page!)))

(defn build-site!
  [site]
  (-> site
      htmlize
      spit-site!))

(def site
  {:pages [index/page]})

(defn main []
  (if (schema/valid-site? site)
    (build-site! site)
    (schema/invalid-site site)))

(comment
  (main)

  (def example-page
    {:name    "page-one"
     :location ""
     :content [:div {} [:h1 {} "Page One"] [:p {} "Page one text."]]})

  (def example-page2
    {:name    "page-two"
     :location "test"
     :content [:div {} [:h1 {} "Page Two"] [:p {} "Page two text."]]})

  (def example-site
    {:pages [example-page example-page2]})

  (build-site! example-site))
