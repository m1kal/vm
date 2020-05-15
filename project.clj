(defproject vm "1.3.0"
  :description "Simple virtual machine in ClojureScript with Reagent"
  :url "https://github.com/m1kal/vm"
  :license "MIT"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [reagent "0.8.1"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.17"]]
  :figwheel {:nrepl-port 7888}
  :cljsbuild
    {:builds
      {:dev
        {:source-paths ["src/cljs"]
         :figwheel true
         :compiler
         {:main "vm.core"
          :asset-path "js/out"
          :output-to "resources/public/js/app.js"
          :output-dir "resources/public/js/out"
          :source-map true
          :optimizations :none}}}})
