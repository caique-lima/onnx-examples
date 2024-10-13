import time

from toolz import memoize

@memoize
def load_trained_model():
    """Load the trained model."""
    import pickle as pkl
    with open("model.pkl", "rb") as f:
        return pkl.load(f)

def infer(input):
    """Infer the model."""
    model = load_trained_model()
    return model.predict(input)
