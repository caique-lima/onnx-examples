(ns model-server.diplomat.http-in
  (:require [model-server.controller.inference :as c.inference]))


(defn inference [request]
  (let [model-output (c.inference/infer request)]
    {:status 200 :body model-output}))
