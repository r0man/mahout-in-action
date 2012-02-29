(ns mahout.in.action.ch02
  (:use mahout.core))

(defn make-recommender [path n]
  (let [model (file-model path)
        similarity (pearson-correlation-similarity model)
        neighborhood (nearest-n-user-neighborhood n similarity model)]
    (generic-user-based-recommender model neighborhood similarity)))

(defn recommend-intro [n user-id how-many]
  (let [recommender (make-recommender "test-resources/intro.csv" n)]
    (recommend recommender user-id how-many)))
