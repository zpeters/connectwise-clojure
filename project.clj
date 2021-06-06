(defproject org.clojars.zpeters/connectwise "0.1.4"
  :description "Connectwise Manage interface"
  :url "https://github.com/zpeters/connectwise-clojure"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.2"]
                 [clj-http "3.12.2"]
                 [cheshire "5.10.0"]
                 [yogthos/config "1.1.7"]
                 [tupelo "21.01.26b"]
                 ]
  :jvm-opts ["-Dconfig=test-config.edn"]
  :repl-options {:init-ns connectwise.core})
