(ns duct.router.bidi
  (:require
    [bidi.ring :as bidi]
    [integrant.core :as ig]))


(defmethod ig/init-key :duct.router/bidi
  [_ {:keys [routes handlers]}]
  (bidi/make-handler routes
                     (fn [route-name]
                       (get handlers route-name))))
