(ns kpav-dot-dev.config
  (:require
   [clojure.edn :as edn]
   [kpav-dot-dev.schema :as schema]))

(def Config
  "Malli schema for what a config should look like."
  [:map
   {:closed true}
   [:site-name string?]
   [:content-dir string?]
   [:out-dir string?]
   [:blog {:optional true}
    [:map {:closed true}
     [:out-dir string?]
     [:content-dir string?]]]
   [:port int?]])

(def default
  {:site-name        "{:kpav-dot \"dev\"}"
   :content-dir      "content"
   :out-dir          "dist"
   :blog             {:out-dir     "blog"  ;; relative to out-dir
                      :content-dir "blog"} ;; relative to content-dir
   :port             1337})

(defn load
  "Load a config file and fill in any missing defaults.
  Default file is config.edn in the project root directory."
  ([]
    (load "config.edn"))
  ([config-file]
   (let [config (->> (slurp config-file)
                     edn/read-string
                     (merge default))]
     (if (schema/valid? Config config)
       config
       (throw (Exception. (schema/invalid Config config)))))))

(comment
  (load)
  ,)
