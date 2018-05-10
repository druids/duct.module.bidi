(ns duct.module.bidi
  "Inspirated by https://github.com/duct-framework/module.ataraxy/blob/master/src/duct/module/ataraxy.clj"
  (:require
    [clojure.string :refer [includes? split]]
    [bidi.bidi :as bidi]
    [duct.core :as duct]
    [integrant.core :as ig]))


(defn- add-ns-prefix
  [kw prefix]
  (keyword prefix
           (if-let [context (namespace kw)]
             context
             (name kw))))


(defn- infer-keys
  [route-keys prefix]
  (into {} (for [route route-keys] [route (ig/ref (add-ns-prefix route prefix))])))


(defn- infer-handlers
  [routes project-ns]
  (infer-keys (->> routes bidi/route-seq (map :handler)) (str project-ns ".handler")))


(defmethod ig/init-key :duct.module/bidi
  [_ routes]
  {:req #{:duct.core/project-ns}
   :fn (fn [config]
         (let [flatten-routes ["" (reduce merge {} routes)]]
           (duct/merge-configs
             config
             {:duct.core/handler
              {:router (ig/ref :duct.router/bidi)}

              :duct.router/routes flatten-routes

              :duct.router/bidi
              {:routes flatten-routes
               :handlers (infer-handlers flatten-routes (:duct.core/project-ns config))}})))})
