(ns kpav-dot-dev.html
  (:require
   [kpav-dot-dev.schema :as schema]
   [pod.retrogradeorbit.bootleg.utils :as utils]))

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

(def HiccupSeq
  "Collection of hiccup, this is what bootleg parses md to."
  [:sequential Hiccup])

(defn valid?
  "Determine if this is valid HTML.
  Pass a keyword type to specify which type of HTML:
  - :hiccup     this is the default
  - :hiccup-seq"
  ([html]
   (valid? html :hiccup))
  ([html type]
   (case type
     :hiccup     (schema/valid? Hiccup html)
     :hiccup-seq (schema/valid? HiccupSeq html))))

(defn htmlize
  "Turn all :pages :content from Hiccup to HTML."
  [site]
  (letfn [(htmlize-page [page]
            (update page :content #(utils/convert-to % :html)))
          (htmlize-pages [pages]
            (map #(htmlize-page %) pages))]
    (update site
            :pages
            htmlize-pages)))
