(ns duct.router.routes-test
  (:require
    [clojure.test :refer [deftest is]]
    [duct.router.bidi :as bidi]
    [integrant.core :as ig]))


(def config
  {:duct.router/routes
   ["" {"/api" :api}]})


(deftest router-test
  (is (= (:duct.router/routes config) (:duct.router/routes (ig/init config)))))
