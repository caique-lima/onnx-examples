import time
import model_inference

from flask import Flask, request
from toolz import memoize
from functools import wraps

app = Flask(__name__)

def generate_random_sample(batch_size):
    """Generate a random sample."""
    import numpy as np
    return np.random.rand(batch_size, 10).astype(np.float32)

@app.route("/inference")
def inference():
    batch_size = int(request.args.get('batch-size'))
    input = generate_random_sample(batch_size)
    start_time = time.time()
    output = model_inference.infer(input)
    end_time = time.time()
    return {"body": {"inference_time": (end_time - start_time) * 1000}}
