(ns kpav-dot-dev.components)

(def head
  [:head
   [:meta {:charset "utf-8"}]
   [:meta {:name    "viewport"
           :content "width=device-width, initial-scale=1.0"}]
   [:title "kpav dot dev"]])

(def header
  [:header
   [:p "{:kpav-dot :dev}"]
   [:nav
    [:ul
     [:li [:a {:href "/"} "Home"]]
     [:li [:a {:href "/blog"} "Blog"]]]]])

(def footer
  [:footer [:hr] "2022 © Kevin Pavao | Built with λ"])

#_(defn header-anchor
  [tag id href content]
  [tag
   {:id id}
   ;; TODO this probably isnt best way to do this
   ;; see md/make-header-anchor hack comment
   (into
    [:a
     {:href href}]
    content)])

#_(defn md-page
  [title hiccup-seq]
  (into [:div [:h1 title]] hiccup-seq)
  #_[:div [:h1 title] content])

;; (defn post-summary
;;   [{:keys [name title date] :as _blog-page}]
;;   [:li
;;    [:a {:href (format "/blog/%s/" name)}
;;     [:h2 title]]
;;    [:span (str date)]])

;; (defn blog-summary
;;   [blog-pages]
;;   #_[:ul (mapv post-summary blog-pages)]
;;   (->> blog-pages
;;        (map post-summary)
;;        (into [:ul])))
