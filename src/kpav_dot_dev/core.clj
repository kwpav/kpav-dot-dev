(ns kpav-dot-dev.core
  (:require
   [clojure.java.io :as io]
   [kpav-dot-dev.html :as html]
   [kpav-dot-dev.site :as site]))

(defn- spit-page!
  "Get the full path of the page, make any missing dirs, and write it."
  [{:keys [location name content] :as _page} out-dir]
  (let [file-name (if (= name "index")
                    "index.html"
                    (format "%s/index.html" name))
        path      (format "%s/%s" out-dir location)
        full-path (format "%s/%s" path file-name)]
    (println "Writing:" full-path)
    (io/make-parents full-path)
    (spit full-path content)))

(defn- spit-site!
  "Write the site to out-dir."
  [{:keys [pages] :as _site} out-dir]
  (doseq [page pages]
    (spit-page! page out-dir)))

(defn build-site!
  "Make the site and write it to out-dir."
  [{:keys [out-dir] :as opts}]
  (let [site (site/make-site opts)]
    (if (site/valid? site)
      (-> site
          html/htmlize
          (spit-site! out-dir))
      (site/invalid site))))

(comment
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

  (require '[kpav-dot-dev.markdown :as md])
  (update example-site :pages (into (:pages example-site) (md/md-files->Page "content/blog" "blog")))

  (spit-site! example-site "dist")
  ,)
