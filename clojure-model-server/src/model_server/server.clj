(ns model-server.server
  (:import [ai.onnxruntime OrtEnvironment])
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [clojure.java.io :as io]
            [model-server.diplomat.http-in :as http-in]))

(def ^:private ort-env
  (OrtEnvironment/getEnvironment))

(defn ^:private ort-session
  [ort-env onnx-file]
  (.createSession ort-env onnx-file))

(defn ^:private to-ort-session
  [artifact-path]
  (ort-session ort-env artifact-path))

(def ^:private ort
  (to-ort-session (.getPath (io/resource "model.onnx"))))

(defn respond-inference [request]
  (let [response (http-in/inference request ort ort-env)]
  (-> {:status 200
       :body response}
      (http/json-response))))

(def routes
  (route/expand-routes
    #{["/inference" :get respond-inference :route-name :inference]}))

(defn create-server []
  (http/create-server
    {::http/routes routes
     ::http/type :jetty
     ::http/port 8890}))

(defn start []
  (http/start (create-server)))

(defn -main
  [& args]
  (start))
