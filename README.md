# ONNX examples

This repository contains a set of examples for [ONNX](https://onnx.ai/).
We bring both code to serialize a Python model to ONNX and to run inference using ONNX Runtime.

For this example we used [Clojure](https://clojure.org/) and [ONNX Runtime](https://microsoft.github.io/onnxruntime/).
But it can be applied to any other language supported by ONNX Runtime.

## Model

For this example we chose to create a model based on [diabetes dataset](https://www4.stat.ncsu.edu/~boos/var.select/diabetes.html).
With this dataset we can create a simple Linear Regression model that tries to predict the progression of diabetes.

Model here is used for teaching purposes only, it is not intended to be used in production.

## Serving

To give an example of how to serve a model, we created both a Python and Clojure server.
The Python model uses flask to serve the model, and load its pickle file.
Clojure version uses ONNX Runtime to load the model and serve it using [Pedestal](http://pedestal.io/pedestal/0.7/index.html).

## Environment

You can check the version of each library in the requirements file or in the project.clj file, we chose to pin all of them for a better reproducibility.
For Python version we are using Python 3.8.18 and for Clojure we are using Clojure 1.11.1.

All tests were performed in a Macbook Pro 2018, 2,2 GHZ 6-Core Intel Core i7, 16GB 2400 MHz DDR4, with Ventura 13.6.7.

## Presentation

This content is part of a presentation called "Deploying ML models in a Clojure environment"
Slides will be added soon.

- [PT-BR] Machine Learning em um ambiente CLojure-First - [Nubank DS & ML Meetup](https://www.meetup.com/pt-BR/machine-learning-big-data-engenharia/) #90 - [Video](https://www.youtube.com/watch?v=xN_5vj7ijgE)
- [EN] Soon - [Clojure/Conj 2024](https://2024.clojure-conj.org/)
