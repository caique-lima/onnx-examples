(ns model-server.controller.inference
  (:import [ai.onnxruntime OrtEnvironment OnnxTensor]
           [java.nio ByteBuffer ByteOrder FloatBuffer])
  (:require [clojure.java.io :as io]))

;; ONNX related code
(def ^:private ort-env
  (OrtEnvironment/getEnvironment))

(defn ort-session
  [ort-env onnx-file]
  (.createSession ort-env onnx-file))

(defn to-ort-session
  [artifact-path]
  (ort-session ort-env artifact-path))

;; Tensor transformation
(defn create-float-buffer [data]
  (let [size (* (count data) (count (first data)))
        buffer (FloatBuffer/allocate size)]
    (doseq [row data]
      (doseq [item row]
        (.put buffer (float item))))
    (.flip buffer)
    buffer))

(defn to-float-tensor
  [ort-env input]
  (let [input-size (count input)
        input-row-size (count (first input))]
  (OnnxTensor/createTensor ort-env (create-float-buffer input) (long-array [input-size input-row-size]))))

;; Output parsing
(defn get-value
  [results key]
  (.getValue (.get (.get results key))))

(defn get-probabilities
  [results]
  (map #(.getValue %) results))

(defn parse-iris-model-output
  [results]
  (let [output-label (map double (get-value results "output_label"))
        output-probability (get-probabilities (get-value results "output_probability"))]
    {:output-label output-label
     :output-probability output-probability}))

;; Inference
(defn infer [& args]
  (let [ort (to-ort-session (.getPath (io/resource "model.onnx")))
        input-tensor (to-float-tensor ort-env [[1.0 2.0 3.0 4.0]
                                               [0.0 0.0 0.0 14.0]])
        results (.run ort {"X" input-tensor})
        parsed-results (parse-iris-model-output results)]
    (println parsed-results)))
