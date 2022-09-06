(ns kpav-dot-dev.blog
  (:require
   [kpav-dot-dev.markdown :as md]))

(defn- post-summary
  [{:keys [name title date] :as _blog-page}]
  [:li
   [:a {:href (format "/blog/%s/" name)}
    [:h2 title]]
   [:span (str date)]])

(defn- blog-summary
  [blog-pages]
  (->> blog-pages
       (map post-summary)
       (into [:ul])))

(defn- make-blog-list
  "Make a list of blog posts sorted by date."
  ;; only supports a single page, I'll worry about that if/when I ever have enough blog posts
  ;; used only for the front page now, but it will be useful if/when I add tags
  [blog-posts blog-out-dir]
  (let [content (->> blog-posts
                     (sort-by :date)
                     reverse
                     blog-summary)]
    {:name     "index"
     :location blog-out-dir
     :content  content}))

(defn- make-posts
  "Make pages from a directory containing blog posts in markdown."
  [blog-content-dir blog-out-dir]
  (md/md-files->Page blog-content-dir blog-out-dir))

(defn make-blog
  "Make a collection of all the blog posts and the blog's front page."
  [{:keys [content-dir blog] :as _opts}]
  (let [blog-content-dir (format "%s/%s" content-dir (:content-dir blog))
        blog-out-dir     (:out-dir blog)
        blog-posts       (make-posts blog-content-dir blog-out-dir)
        blog-summary     (make-blog-list blog-posts blog-out-dir)]
    (conj blog-posts blog-summary)))

(comment
  (-> (make-posts "content/blog" "blog")
      (make-blog-list "blog"))
  ,)
