(ns mahout.core
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel])
  (:use [clojure.java.io :only (file)]))

(defn file-data-model
  "Returns a FileDataModel from `path`, or nil if it doesn't exists."
  [path]
  (if-let [file (file path)]
    (if (.exists file)
      (FileDataModel. file))))
