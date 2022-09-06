(ns kpav-dot-dev.markdown
  (:require
   [clojure.instant :as inst]
   [clojure.java.io :as io]
   [clojure.string :as str]
   [pod.retrogradeorbit.bootleg.markdown :as md]))

(defn header-anchor
  [tag id href content]
  [tag
   {:id id}
   ;; TODO this probably isnt best way to do this
   ;; see HACK below
   (into
    [:a
     {:href href}]
    content)])

(defn make-header-anchor
  "Create anchor link using the {#header-text} at the end of headers."
  [[tag & content :as _header]]
  (let [text        (last content)
        new-text    (str/replace text #" \{\S+\}$" "")
        ;; HACK handle case for when content isnt only text
        ;; but it turns it into a collection even if only text,
        ;; so it is not alqays proper hiccup
        new-content (as-> content %
                      (drop-last %)
                      (conj % new-text)
                      (filter seq %))
        anchor-href (-> (re-find #"\{\S+\}$" text)
                        (str/replace #"\{|\}" ""))
        id          (str/replace anchor-href "#" "")]
    (header-anchor tag id anchor-href new-content)))

(defn add-header-anchors
  "Add anchor links to headers with {#header}."
  [hiccup-seq]
  (let [headers [:h1 :h2 :h3 :h4 :h5 :h6]]
    (->> hiccup-seq
         (map (fn [[tag _text :as element]]
                (if (and (coll? element) (some #(= tag %) headers))
                  (make-header-anchor element)
                  element))))))

;; TODO figure out a way do this without assuming order
(defn parse-hugo-data
  "Parse the hugo header stuff in +++ title... +++
  This assumes that the order of things is:
  title = ... author = ... date = ... draft = ..."
  [[_tag hugo-data]]
  (let [title (-> (re-find #"(?<=title = ).*(?= author)" hugo-data)
                  (str/replace "&quot;" ""))
        date  (->> hugo-data
                   (re-find #"(?<=date = ).*(?= draft)")
                   inst/read-instant-date)]
    {:title title
     :date  date}))

;; TODO:
;; - remove hugo default?
;; - theres probably a better way than a hugo? flag, maybe keyword?
(defn md-file->Page
  ([file out-dir]
   (md-file->Page file out-dir true))
  ([file out-dir hugo?]
   (let [[name]   (-> file .getName (str/split #"\."))
         content  (-> file slurp (md/markdown :data :hiccup-seq))
         location out-dir
         page     {:name     name
                   :location location
                   :content  content}]
     (if hugo?
       (let [[hugo-data & hiccup] content
             {:keys [title date]} (parse-hugo-data hugo-data)
             hugo-content         (->> hiccup
                                       add-header-anchors
                                       (into [:div [:h1 title]]))]
         (merge page
                {:date    date
                 :title   title
                 :content hugo-content}))
       page))))

(defn md-files->Page
  [in-dir out-dir]
  (->> in-dir
       io/file
       .listFiles
       (map #(md-file->Page % out-dir))))

(comment
  (make-header-anchor
   [:h1 "Whether to Declare Bankruptcy or to Refinance {#whether-to-declare-bankruptcy-or-to-refinance}"])

  (make-header-anchor
   [:h1 [:code "Basemacs"] " {#whether-to-declare-bankruptcy-or-to-refinance}"])

  (def meta [:p "+++ title = &quot;Restarting with ox-hugo&quot; author = [&quot;Kevin Pavao&quot;] date = 2020-10-20T23:43:00-05:00 draft = false +++"])

  (parse-hugo-data meta)
  ,)
