(ns kpav-dot-dev.config-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [kpav-dot-dev.config :as config]
   [kpav-dot-dev.schema :as schema]))

(deftest load
  (testing "able to load config.edn"
    (let [config (config/load)]
      (is (schema/valid? config/Config config)))))
