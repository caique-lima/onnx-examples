import cloudpickle as pkl
import logging
import numpy as np

from sklearn.linear_model import LinearRegression


logging.basicConfig(level=logging.INFO)


def split_data(data: np.ndarray):
    """Split data into training and testing sets."""
    from sklearn.model_selection import train_test_split
    X, y = data.data, data.target
    X = X.astype(np.float32)
    return train_test_split(X, y)


def model_train(X_train: np.ndarray, y_train: np.ndarray):
    """Train a model."""
    logging.info("Training model")
    clr = LinearRegression()
    clr.fit(X_train, y_train)
    logging.info("Model trained")
    return clr


def serialize_model_onnx(clr: LinearRegression, X: np.ndarray):
    """Serialize the model."""
    from skl2onnx import to_onnx
    logging.info("Serializing model")
    onx = to_onnx(clr, X[:1])
    with open("model.onnx", "wb") as f:
        f.write(onx.SerializeToString())
    logging.info("Model serialized, saved as model.onnx")


def serialize_model_pickle(clr: LinearRegression):
    """Serialize the model."""
    logging.info("Serializing model")
    with open("model.pkl", "wb") as f:
        pkl.dump(clr, f)
    logging.info("Model serialized, saved as model.pkl")


def serialize_model(clr: LinearRegression, X: np.ndarray):
    """Serialize the model."""
    serialize_model_onnx(clr, X)
    serialize_model_pickle(clr)


def load_training_data():
    """Load the training data."""
    from sklearn.datasets import load_diabetes
    return load_diabetes()


def main():
    data = load_training_data()
    X_train, _, y_train, _ = split_data(data)
    clr = model_train(X_train, y_train)
    serialize_model(clr, X_train)

if __name__ == "__main__":
    main()
