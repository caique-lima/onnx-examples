(ns model-server.controller.inference)

(defn infer
  [input ort]
    (.run ort {"X" input}))
