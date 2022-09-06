(ns kpav-dot-dev.page
  (:require
   [kpav-dot-dev.html :as html]
   [kpav-dot-dev.schema :as schema]))

(def Page
  [:map
   {:closed true}
   [:location [:re #"^[a-z0-9]*(?:-+[/a-z0-9]+)*$"]]
   [:name [:re #"^[a-z0-9]+(?:-+[a-z0-9]+)*$"]] ;; non-empty strings with only lowercase, numbers, or dashes
   [:title {:optional true} string?] ;; blog title
   [:date {:optional true} inst?] ;; blog date
   [:content html/Hiccup]])

(defn valid?
  "Determine if this is a valid page."
  [page]
  (schema/valid? Page page))

(defn invalid
  "Describe what is wrong with a page."
  [page]
  (schema/invalid Page page))
