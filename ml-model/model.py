import numpy as np
import logging

from sklearn.ensemble import RandomForestClassifier


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
    clr = RandomForestClassifier()
    clr.fit(X_train, y_train)
    logging.info("Model trained")
    return clr


def serialize_model(clr: RandomForestClassifier, X: np.ndarray):
    """Serialize the model."""
    from skl2onnx import to_onnx
    logging.info("Serializing model")
    onx = to_onnx(clr, X[:1])
    with open("model.onnx", "wb") as f:
        f.write(onx.SerializeToString())
    logging.info("Model serialized, saved as model.onnx")


def load_training_data():
    """Load the training data."""
    from sklearn.datasets import load_iris
    return load_iris()


def main():
    data = load_training_data()
    X_train, _, y_train, _ = split_data(data)
    clr = model_train(X_train, y_train)
    serialize_model(clr, X_train)

if __name__ == "__main__":
    main()
