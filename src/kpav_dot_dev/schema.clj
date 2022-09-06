(ns kpav-dot-dev.schema
  (:require
   [malli.core :as m]
   [malli.error :as me]))

(defn invalid
  "Explain why the value does not match the schema."
  [schema value]
  (-> schema
      (m/explain value)
      (me/humanize)))

(def valid? m/validate)
