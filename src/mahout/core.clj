(ns mahout.core
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel]
           [org.apache.mahout.cf.taste.impl.similarity PearsonCorrelationSimilarity]
           java.io.File))

(defprotocol IFileDataModel
  (-file-data-model [obj] "Convert `obj` into a FileDataModel."))

(defn file-data-model
  "Returns a FileDataModel from `path`, or nil if it doesn't exists."
  [path] (-file-data-model path))

(defn pearson-correlation-similarity
  "Returns the pearson correlation similarity of `model`."
  [model]
  (if-let [model (file-data-model model)]
    (PearsonCorrelationSimilarity. model)))

(extend-protocol IFileDataModel
  nil
  (-file-data-model [_]
    nil)
  File
  (-file-data-model [file]
    (if (.exists file)
      (FileDataModel. file)))
  FileDataModel
  (-file-data-model [model]
    model)
  String
  (-file-data-model [string]
    (-file-data-model (File. string))))
