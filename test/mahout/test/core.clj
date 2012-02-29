(ns mahout.test.core
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel]
           [org.apache.mahout.cf.taste.impl.similarity PearsonCorrelationSimilarity])
  (:use clojure.test
        mahout.core))

(def intro-dataset-path "test-resources/intro.csv")

(deftest test-file-data-model
  (is (nil? (file-data-model nil)))
  (is (nil? (file-data-model "NOT-EXISTING")))
  (is (instance? FileDataModel (file-data-model intro-dataset-path))))

(deftest test-pearson-correlation-similarity
  (is (nil? (pearson-correlation-similarity nil)))
  (is (nil? (pearson-correlation-similarity "NOT-EXISTING")))
  (is (instance? PearsonCorrelationSimilarity (pearson-correlation-similarity intro-dataset-path))))