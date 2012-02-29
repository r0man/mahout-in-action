(ns mahout.test.core
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel]
           [org.apache.mahout.cf.taste.impl.similarity PearsonCorrelationSimilarity]
           [org.apache.mahout.cf.taste.impl.recommender GenericUserBasedRecommender]
             [org.apache.mahout.cf.taste.impl.neighborhood NearestNUserNeighborhood])
  (:use clojure.test
        mahout.core))

(def intro-dataset-path "test-resources/intro.csv")

(deftest test-file-model
  (is (nil? (file-model nil)))
  (is (nil? (file-model "NOT-EXISTING")))
  (is (instance? FileDataModel (file-model intro-dataset-path))))

(deftest test-nearest-n-user-neighborhood
  (let [user-similarity (pearson-correlation-similarity intro-dataset-path)]
    (let [neighborhood (nearest-n-user-neighborhood 1 user-similarity intro-dataset-path)]
      (is (instance? NearestNUserNeighborhood neighborhood)))))

(deftest test-pearson-correlation-similarity
  (is (nil? (pearson-correlation-similarity nil)))
  (is (nil? (pearson-correlation-similarity "NOT-EXISTING")))
  (is (instance? PearsonCorrelationSimilarity (pearson-correlation-similarity intro-dataset-path))))

(deftest test-generic-user-based-recommender
  (let [model (file-model intro-dataset-path)
        similarity (pearson-correlation-similarity model)
        neighborhood (nearest-n-user-neighborhood 2 similarity model)]
    (is (instance? GenericUserBasedRecommender (generic-user-based-recommender model neighborhood similarity)))))

(deftest test-recommend
  (let [model (file-model intro-dataset-path)
        similarity (pearson-correlation-similarity model)
        neighborhood (nearest-n-user-neighborhood 2 similarity model)
        recommender (generic-user-based-recommender model neighborhood similarity)]
    (is (= [] (recommend recommender 1 0)))
    (is (= [104] (map :item (recommend recommender 1 1))))))
