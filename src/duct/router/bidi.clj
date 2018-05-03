(ns duct.router.bidi
  (:require
    [bidi.ring :as bidi]
    [integrant.core :as ig]))


(defmethod ig/init-key :duct.router/bidi
  [_ {:keys [routes handlers]}]
  (bidi/make-handler routes
                     (fn [route-name]
                       (when-let [handler (get handlers route-name)]
                         (fn [request]
                           (handler (assoc request :bidi-route route-name)))))))
