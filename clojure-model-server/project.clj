(defproject model-server "0.1.0-SNAPSHOT"
  :description "An example on how to serve a ONNX model using Clojure"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.microsoft.onnxruntime/onnxruntime "1.18.0"]
                 [io.pedestal/pedestal.jetty "0.7.1"]
                 [org.slf4j/slf4j-simple "2.0.10"]
                 [cheshire/cheshire "5.10.1"]]
  :resource-paths ["resources"]
  :main ^:skip-aot model-server.server
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
