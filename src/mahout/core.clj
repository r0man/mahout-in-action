(ns mahout.core
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel]
           [org.apache.mahout.cf.taste.impl.similarity PearsonCorrelationSimilarity]
           [org.apache.mahout.cf.taste.impl.neighborhood NearestNUserNeighborhood]
           java.io.File))

(defprotocol IFileDataModel
  (-file-data-model [obj] "Convert `obj` into a FileDataModel."))

(defn file-data-model
  "Returns a FileDataModel from `path`, or nil if it doesn't exists."
  [path] (-file-data-model path))

(defn nearest-n-user-neighborhood
  "Returns a neighborhood computation consisting of the nearest N
  users to a given user."
  [n min-similarity user-similarity data-model & [sampling-rate]]
  (if-let [data-model (file-data-model data-model)]
    (NearestNUserNeighborhood. n min-similarity user-similarity data-model (or sampling-rate 1))))

(defn pearson-correlation-similarity
  "Returns the pearson correlation similarity for `data-model`."
  [data-model]
  (if-let [data-model (file-data-model data-model)]
    (PearsonCorrelationSimilarity. data-model)))

(extend-protocol IFileDataModel
  nil
  (-file-data-model [_]
    nil)
  File
  (-file-data-model [file]
    (if (.exists file)
      (FileDataModel. file)))
  FileDataModel
  (-file-data-model [data-model]
    data-model)
  String
  (-file-data-model [string]
    (-file-data-model (File. string))))
