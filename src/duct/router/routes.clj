(ns duct.router.routes
  (:require
    [integrant.core :as ig]))


(defmethod ig/init-key :duct.router/routes
  [_ routes]
  routes)
