(ns duct.module.bidi-test
  (:require
    [clojure.test :refer [deftest is testing]]
    [duct.core :as duct]
    [duct.module.bidi :as bidi]
    [integrant.core :as ig]))


(duct/load-hierarchy)


(def config
  {:duct.core/project-ns 'foo
   :duct.module/bidi '[{"/bar" :bar}
                       {"/api/users" {"" :api/users-list
                                      ["/" :id] :api/users-detail}}]
   :foo.handler/bar (fn [_] {:status 200, :body ""})
   :foo.handler/api (fn [_] {:status 200, :body ""})})


(deftest module-test
  (testing "basic config"
    (is (= (merge config
                  {:duct.core/handler
                   {:router (ig/ref :duct.router/bidi)}
                   :duct.router/routes
                    ["" {"/bar" :bar
                         "/api/users" {"" :api/users-list
                                       ["/" :id] :api/users-detail}}]
                   :duct.router/bidi
                   {:routes
                    ["" {"/bar" :bar
                         "/api/users" {"" :api/users-list
                                       ["/" :id] :api/users-detail}}]
                    :handlers {:bar (ig/ref :foo.handler/bar)
                               :api/users-list (ig/ref :foo.handler/api)
                               :api/users-detail (ig/ref :foo.handler/api)}}})
           (duct/prep config)))))
