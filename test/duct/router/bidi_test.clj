(ns duct.router.bidi-test
  (:require
    [clojure.test :refer [deftest is]]
    [duct.router.bidi :as bidi]
    [integrant.core :as ig]))


(defn hello-handler
  [request]
  (is (= :hello (:bidi-route request)))
  {:status 200, :headers {}, :body (str "Hello " (-> request :params :name))})


(def config
  {:duct.router/bidi
   {:routes '["" {["/hello/" :name] :hello}]
    :handlers {:hello hello-handler}}})


(deftest router-test
  (let [handler (:duct.router/bidi (ig/init config))]
    (is (= (handler {:request-method :get, :uri "/hello/world"})
           {:status 200, :headers {}, :body "Hello world"}))))
