(ns mahout.core
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel]
           [org.apache.mahout.cf.taste.impl.neighborhood NearestNUserNeighborhood]
           [org.apache.mahout.cf.taste.impl.recommender GenericUserBasedRecommender]
           [org.apache.mahout.cf.taste.impl.similarity PearsonCorrelationSimilarity]
           java.io.File))

(defprotocol IFileDataModel
  (-file-model [obj] "Convert `obj` into a FileDataModel."))

(defn file-model
  "Returns a FileDataModel from `path`, or nil if it doesn't exists."
  [path] (-file-model path))

(defn generic-user-based-recommender [model neighborhood similarity]
  (GenericUserBasedRecommender. model neighborhood similarity))

(defn nearest-n-user-neighborhood
  "Returns a neighborhood computation consisting of the nearest N
  users to a given user."
  [n user-similarity model]
  (if-let [model (file-model model)]
    (NearestNUserNeighborhood. n user-similarity model)))

(defn pearson-correlation-similarity
  "Returns the Pearson correlation similarity for `model`.
  See: http://en.wikipedia.org/wiki/Pearson_product-moment_correlation_coefficient"
  [model]
  (if-let [model (file-model model)]
    (PearsonCorrelationSimilarity. model)))

(defn recommend
  "Returns a seq of recommendations from `recommender`."
  [recommender user-id how-many]
  (if (pos? how-many)
    (map #(hash-map :item (.getItemID %1) :value (.getValue %1))
         (.recommend recommender user-id how-many))
    []))

(extend-protocol IFileDataModel
  nil
  (-file-model [_]
    nil)
  File
  (-file-model [file]
    (if (.exists file)
      (FileDataModel. file)))
  FileDataModel
  (-file-model [model]
    model)
  String
  (-file-model [string]
    (-file-model (File. string))))
