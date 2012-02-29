(ns mahout.core
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel]
           [org.apache.mahout.cf.taste.impl.model GenericDataModel]
           [org.apache.mahout.cf.taste.impl.neighborhood NearestNUserNeighborhood]
           [org.apache.mahout.cf.taste.impl.recommender GenericUserBasedRecommender]
           [org.apache.mahout.cf.taste.impl.similarity PearsonCorrelationSimilarity]
           [org.apache.mahout.common RandomUtils]
           java.io.File))

(defprotocol IDataModel
  (-data-model [obj] "Convert `obj` into a FileDataModel."))

(defn data-model
  "Returns a FileDataModel from `path`, or nil if it doesn't exists."
  [path] (-data-model path))

(defn generic-user-based-recommender [model neighborhood similarity]
  (GenericUserBasedRecommender. model neighborhood similarity))

(defn nearest-n-user-neighborhood
  "Returns a neighborhood computation consisting of the nearest N
  users to a given user."
  [n user-similarity model]
  (if-let [model (data-model model)]
    (NearestNUserNeighborhood. n user-similarity model)))

(defn pearson-correlation-similarity
  "Returns the Pearson correlation similarity for `model`.
  See: http://en.wikipedia.org/wiki/Pearson_product-moment_correlation_coefficient"
  [model]
  (if-let [model (data-model model)]
    (PearsonCorrelationSimilarity. model)))

(defn recommend
  "Returns a seq of recommendations from `recommender`."
  [recommender user-id how-many]
  (if (pos? how-many)
    (map #(hash-map :item (.getItemID %1) :value (.getValue %1))
         (.recommend recommender user-id how-many))
    []))

(defn use-test-seed
  "Use the same seed for testing purposes."
  [] (RandomUtils/useTestSeed))

(extend-protocol IDataModel
  nil
  (-data-model [_]
    nil)
  File
  (-data-model [file]
    (if (.exists file)
      (FileDataModel. file)))
  FileDataModel
  (-data-model [model]
    model)
  GenericDataModel
  (-data-model [model]
    model)
  String
  (-data-model [string]
    (-data-model (File. string))))
