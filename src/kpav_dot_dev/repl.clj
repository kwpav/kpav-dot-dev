(ns kpav-dot-dev.repl)

(def ^:private ratom atom)

(defonce log (ratom []))

(defn- tap-fn [x]
  (swap! log conj x))

(add-tap tap-fn)

(comment
  @log
  (reset! log [])
  ,)
