(defproject duct.module.bidi "0.3.0"
  :description "Duct module and router for the Bidi routing library"
  :url "https://github.com/druids/duct.module.bidi"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}

  :dependencies [[bidi "2.1.3"]
                 [duct/core "0.6.2"]
                 [integrant "0.6.3"]]

  :profiles {:dev {:plugins [[lein-cloverage "1.0.10"]
                             [lein-kibit "0.1.6"]
                             [jonase/eastwood "0.2.5"]
                             [venantius/ultra "0.5.2"]]
                   :dependencies [[org.clojure/clojure "1.9.0"]]
                   :source-paths ["src" "dev/src"]}
             :test {:dependencies [[org.clojure/clojure "1.9.0"]]}}
  :aliases {"coverage" ["with-profile" "test" "cloverage" "--fail-threshold" "95" "-e" "dev|user"]})
