(ns model-server.diplomat.http-in
  (:require [clojure.core.reducers :as r]
            [model-server.controller.inference :as c.inference]
            [model-server.adapter.onnx :as a.onnx]))

(defn random-matrix
  "Generates a random matrix of the given dimensions.
  rows - number of rows
  cols - number of columns"
  [rows cols]
  (let [random-row (fn [] (repeatedly cols #(rand-int 100)))]
    (repeatedly rows random-row)))

(defmacro ^:private inference-profiler
  [expr]
  `(let [start# (. System (nanoTime))
         ret# ~expr
         end# (. System (nanoTime))
         execution-time# (/ (double (- end# start#)) 1000000.0)]
    {:output ret# :execution-time execution-time#}))

(defn inference [request ort ort-env]
   (let [batch-size (Integer/parseInt (get-in request [:query-params :batch-size]))
         input-tensor (a.onnx/input->onnx-tensor ort-env (random-matrix batch-size 10))
         inference-profile (inference-profiler (c.inference/infer input-tensor ort))]
    (a.onnx/model-output->response (:execution-time inference-profile))))
