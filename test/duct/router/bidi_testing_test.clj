(ns duct.router.bidi-testing-test
  (:require
    [clojure.test :refer [deftest is]]
    [duct.router.bidi-testing :as bidi]))


(def routes ["" {"/hello" {:get :hello}}])


(deftest route-test
  (let [request {:request-method :get, :uri "/hello"}]
    (is (= (assoc request :bidi-route :hello)
           (bidi/route request routes)))))
