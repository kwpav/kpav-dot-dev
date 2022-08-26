(ns kpav-dot-dev.pages.index)

(def head
  [:head
   [:meta {:charset "utf-8"}]
   [:title "kpav dot dev"]])

(def header
  [:header
   [:nav
    [:ul
     [:li [:a {:href "index.html"} "Home"]]
     #_[:li [:a {:href "about.html"} "About"]]
     #_[:li [:a {:href "blog.html"} "Blog"]]]]])

(def footer
  [:footer [:hr] "2022 © Kevin Pavao | Built with λ"])

(def content
  [:h1 "{:kpav/dev \"coming soon\"}"])

(def page
  {:name "index"
   :content
   [:html
    head
    header
    content
    footer]})
