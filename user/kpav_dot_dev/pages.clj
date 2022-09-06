(ns kpav-dot-dev.pages
  (:require
   [kpav-dot-dev.components :as c]))

(def index
  {:location ""
   :name "index"
   :content
   [:html
    c/head
    c/header
    [:h1 [:code "{:kpav-dot :dev}"]]
    [:h2 "About"]
    [:p "Hello world."]
    c/footer]})

(def all [index])
