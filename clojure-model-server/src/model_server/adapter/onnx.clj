(ns model-server.adapter.onnx
  (:import [ai.onnxruntime OnnxTensor]
           [java.nio FloatBuffer]))

(defn ^:private create-float-buffer [data]
  (let [size (* (count data) (count (first data)))
        buffer (FloatBuffer/allocate size)]
    (doseq [row data]
      (doseq [item row]
        (.put buffer (float item))))
    (.flip buffer)
    buffer))

(defn input->onnx-tensor
  [ort-env input]
  (let [input-size (count input)
        input-row-size (count (first input))]
  (OnnxTensor/createTensor ort-env (create-float-buffer input) (long-array [input-size input-row-size]))))

(defn model-output->response
  [inference-time]
    {:inference_time inference-time})
