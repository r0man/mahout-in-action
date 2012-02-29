(ns mahout.test.core
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel])
  (:use clojure.test
        mahout.core))

(def intro-dataset-path "test-resources/intro.csv")

(deftest test-file-data-model
  (is (nil? (file-data-model nil)))
  (is (nil? (file-data-model "NOT-EXISTING")))
  (is (instance? FileDataModel (file-data-model intro-dataset-path))))
