import json
import glob


def get_file_name_prefix(batch_size):
    return f"inference_{batch_size}_sample"


def get_all_files(model_server, batch_size):
    return glob.glob(f"{model_server}/{get_file_name_prefix(batch_size)}*")


def process_files(model_server, batch_size):
    all_files = get_all_files(model_server, batch_size)
    all_results = []
    for file in all_files:
        with open(file, "r") as f:
            all_results.append(json.load(f)["body"]["inference_time"])
    return all_results


def compute_p90(all_results):
    all_results.sort()
    p90_index = int(0.9 * len(all_results))
    return all_results[p90_index]


if __name__ == "__main__":
    for batch_size in [1, 10, 500, 1000, 5000, 10000, 100000, 1000000]:
        results = {}
        for model_server in ["clojure", "python"]:
            all_results = process_files(model_server, batch_size)
            results[model_server] = compute_p90(all_results)
        print(batch_size, results["clojure"], results["python"], results["python"]/results["clojure"])
