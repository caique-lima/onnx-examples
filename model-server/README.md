# Model inference

This is a simple example of how to use the trained model to make predictions on new data.
The code is written in Clojure and uses ONNX Runtime to load the model and make predictions.

## How to run

1. Install Clojure and Leiningen
2. Run the following command to run the code:

```bash
clojure model_inference.clj
```

This assumes that a file named `model.onnx` exists in `ml-model` folder.
