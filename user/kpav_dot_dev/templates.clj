(ns kpav-dot-dev.templates
  (:require
   [kpav-dot-dev.components :as c]))

(defn base-template
  [content]
  [:html
   c/head
   c/header
   content
   c/footer])
