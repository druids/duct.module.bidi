(ns duct.router.bidi-testing-test
  (:require
    [clojure.test :refer [deftest is]]
    [duct.router.bidi-testing :as bidi]))


(def routes ["" {["/hello/" :id] {:get :hello}}])


(deftest route-test
  (let [request {:request-method :get, :uri "/hello/1"}]
    (is (= (-> request (assoc :bidi-route :hello) (assoc :params {:id "1"}) (assoc :route-params {:id "1"}))
           (bidi/route request routes)))))
