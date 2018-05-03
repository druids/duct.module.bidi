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
[duct.module.bidi "0.1.0"]
```


Usage
-----

To add this module to your configuration, add the `:duct.module/bidi` key. For example:

```clojure
{:duct.core/project-ns foo
 :duct.module/bidi {"/" [:index]}
 :foo.handler/index {}}
```

And `foo/handler/index.clj` may looks like:

```clojure
(ns foo.handler.index
  (:require
    [integrant.core :as ig]))

(defmethod ig/init-key :foo.handler/index
  [_ options]
  (fn [route] ;; route will be :index
    (fn [request]
      {:status 200, :body "Index page", :headers {"Content-Type" "text/html"}})))
```

Routes can grouped into one handler:

```clojure
{:duct.core/project-ns foo
 :duct.module/bidi ["" {"/" :index
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
  (fn [route]
    (case route
      :api/users-list (fn [request] {:status 200, :body []})
      :api/users-detail (fn [request] {:status 200, :body {:id 1, ...
```
