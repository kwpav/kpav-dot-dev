(ns kpav-dot-dev.cmd
  (:require
   [kpav-dot-dev.config :as config]
   [kpav-dot-dev.core :as core]))

(def load-config config/load)

(defn build!
  [opts]
  (core/build-site! opts))

(defn clean!
  [{:keys [out-dir] :as _opts}]
  (let [delete-tree (requiring-resolve 'babashka.fs/delete-tree)]
    (doseq [dir [out-dir]]
      (println "Removing dir:" dir)
      (delete-tree dir))))

(defn serve!
  [{:keys [port out-dir] :as opts}]
  ;; run this from the CLI
  ;; using exec instead of serve so it can be stopped easier
  (let [exec (requiring-resolve 'babashka.http-server/exec)]
    (exec (merge {:port port
                  :dir  out-dir}
                 opts))))

(comment
  (def config (load-config))
  (build! config)
  (clean! config)
  (serve! config)
  ,)
