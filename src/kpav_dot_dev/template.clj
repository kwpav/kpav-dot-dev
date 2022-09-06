(ns kpav-dot-dev.template
  (:require
   [kpav-dot-dev.templates :as templates]))

(defn add-template
  ([page]
   (add-template page templates/base-template))
  ([page template]
   (update page
           :content
           template)))
