(ns mahout.test.core
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel])
  (:use clojure.test
        mahout.core))

(def intro-dataset-path "test-resources/intro.csv")

(deftest test-file-data-model
  (is (instance? FileDataModel (file-data-model intro-dataset-path))))