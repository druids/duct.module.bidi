(ns duct.router.bidi-testing
  (:require
    [bidi.bidi :as bidi]))


(defn route
  "This function is suitable for testing. It assocs a route name into a given `request` (`:bidi-route` key)
   if a `uri` from the `request` matches to any route in given `routes`."
  [request routes]
  (let [{:keys [handler route-params] :as match-context} (bidi/match-route* routes (:uri request) request)]
    (-> request
        (assoc :bidi-route handler)
        (update :params merge route-params)
        (update :route-params merge route-params))))
