(ns kpav-dot-dev.site
  (:require
   [kpav-dot-dev.blog :as blog]
   [kpav-dot-dev.page :as page]
   [kpav-dot-dev.pages :as pages]
   [kpav-dot-dev.schema :as schema]
   [kpav-dot-dev.template :as template]))

(def Site
  [:map
   {:closed true}
   [:pages
    [:vector page/Page]]])

(defn valid?
  "Determine if this is a valid page."
  [site]
  (schema/valid? Site site))

(defn invalid
  "Describe what is wrong with a site."
  [site]
  (schema/invalid Site site))

(defn make-site
  "Make the collection of pages for the site.
  If blog options exist in the config, then generate the blog."
  [{blog-opts :blog :as opts}]
  {:pages
   (if (seq blog-opts)
     (let [blog (->> (blog/make-blog opts)
                     (mapv template/add-template))]
       (into blog pages/all))
     pages/all)})

(comment
  (def site (make-site {:blog {:out-dir "blog" :content-dir "blog"}}))
  (valid? site)
  (invalid site)
,)
