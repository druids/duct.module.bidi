Duct module.bidi
================

A [Duct](https://github.com/duct-framework/duct) module that sets [Bidi]() as the router for your application.

[![CircleCI](https://circleci.com/gh/druids/duct.module.bidi.svg?style=svg)](https://circleci.com/gh/druids/duct.module.bidi)
[![Dependencies Status](https://jarkeeper.com/druids/duct.module.bidi/status.png)](https://jarkeeper.com/druids/duct.module.bidi)
[![License](https://img.shields.io/badge/MIT-Clause-blue.svg)](https://opensource.org/licenses/MIT)


Leiningen/Boot
--------------

To install, add the following to you project `:dependencies`:

```clojure
[duct.module.bidi "0.4.0"]
```


Usage
-----

To add this module to your configuration, add the `:duct.module/bidi` key. For example:

```clojure
{:duct.core/project-ns foo
 :duct.module/bidi [{"/index" :index}]
 :foo.handler/index {}}
```

And `foo/handler/index.clj` may looks like:

```clojure
(ns foo.handler.index
  (:require
    [integrant.core :as ig]))

(defmethod ig/init-key :foo.handler/index
  [_ options]
  (fn [request] ;; :bidi-route will be :index
    {:status 200, :body "Index page", :headers {"Content-Type" "text/html"}})))
```

A route name is in a request under `:bidi-route` key.

Routes can grouped into one handler:

```clojure
{:duct.core/project-ns foo
 :duct.module/bidi [{"/" :index
                     "/api/users" {"" :api/users-list
                                   ["/" :id] :api/users-detail}}]
 :foo.handler/index {}
 :foo.handler/api {}}
```

And `foo/handler/api.clj` may looks like:

```clojure
(ns foo.handler.api
  (:require
    [integrant.core :as ig]))

(defmethod ig/init-key :foo.handler/api
  [_ options]
  (fn [request]
    (case (:bidi-route request)
      :api/users-list {:status 200, :body []})
      :api/users-detail {:status 200, :body {:id 1, ...
```

Routes can be composed from multiple components:

```clojure
{:duct.core/project-ns foo
 :foo/private-routes {"/private" {"/foo" :foo, "/bar" :bar}}
 :foo/public-routes {"/public" {"/biz" :biz, "/baz" :baz}}
 :duct.module/bidi [#ig/ref :foo/private-routes
                    #ig/ref :foo/public-routes]}
```
This is useful e.g. for development only routes.

When a server started you can find a route composition under `:duct.router/routes` key.

E.g.

```clojure
(:duct.router/routes config)
[""
 {"/private" {"/foo" :foo, "/bar" :bar}}
  "/public" {"/biz" :biz, "/baz" :baz}}]
```

Thus you can reverse routes (e.g. when sending an e-mail message) by calling

```clojure
(require '[bidi.bidi :as bidi])

(bidi/route-for (:duct.router/routes config) :bar)
;; "/private/bar"
```


Testing
-------

For testing there is a function `route` that associates a matched route into a request, if a uri matches to any given
 routes. Example:

```clojure
(require '[duct.routes.bidi-testing :as bidi])
(require '[integrant.core :as ig])
(require '[ring.mock.request :as mock])

(let [routes ["" {"/hello" {:get :api/hello}}]
      handler (ig/init-key :myapp.handler/api {})
      response (handler (-> :get (mock/request "/hello") (bidi/route routes)))]]
                                                          ^--- it sets `:api/hello` under `:bidi-route` key
  ...)
```
