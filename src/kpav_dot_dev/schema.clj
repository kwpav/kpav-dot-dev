(ns kpav-dot-dev.schema
  (:require [malli.core :as m]
            [malli.error :as me]))

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
   [:location [:re #"^[a-z0-9]*(?:-+[/a-z0-9]+)*$"]]
   [:name [:re #"^[a-z0-9]+(?:-+[a-z0-9]+)*$"]] ;; non-empty strings with only lowercase, numbers, or dashes
   [:content Hiccup]])

(def Site
  [:map
   {:closed true}
   [:pages
    [:vector Page]]])

(defn- invalid-schema
  "Explain why the value does not match the schema."
  [schema value]
  (-> schema
      (m/explain value)
      (me/humanize)))

(def valid? ^:private m/validate)

(defn valid-page?
  [page]
  (valid? Page page))

(defn invalid-page
  [page]
  (invalid-schema Page page))

(defn valid-site?
  [site]
  (valid? Site site))

(defn invalid-site
  [site]
  (invalid-schema Site site))
