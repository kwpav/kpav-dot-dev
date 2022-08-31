(ns kpav-dot-dev.schema
  (:require [malli.core :as m]))

;; from malli README
;; https://github.com/metosin/malli#parsing-values
(def Hiccup
  [:schema {:registry {"hiccup" [:orn
                                 [:node [:catn
                                         [:name keyword?]
                                         [:props [:? [:map-of keyword? any?]]]
                                         [:children [:* [:schema [:ref "hiccup"]]]]]]
                                 [:primitive [:orn
                                              [:nil nil?]
                                              [:boolean boolean?]
                                              [:number number?]
                                              [:text string?]]]]}}
   "hiccup"])

(def Page
  [:map
   {:closed true}
   [:name [:re #"^[a-z0-9]+(?:_+[a-z0-9]+)*$"]] ;; non-empty strings with only lowercase, numbers, or underscores
   [:content Hiccup]])

(def Site
  [:map
   {:closed true}
   [:pages
    [:vector Page]]])

(def valid-page?
  (m/validator Page))

(defn invalid-page
  [page]
  (m/explain Page page))

(def valid-site?
  (m/validator Site))

(defn invalid-site
  [site]
  (m/explain Site site))
