(ns mahout.core
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel])
  (:use [clojure.java.io :only (file)]))

(defn file-data-model [path]
  (FileDataModel. (file path)))
