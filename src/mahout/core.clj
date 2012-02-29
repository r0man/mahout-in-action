(ns mahout.core
  (:import java.io.File
           [org.apache.mahout.cf.taste.impl.model.file FileDataModel]))

(defprotocol IFileDataModel
  (-file-data-model [obj] "Convert `obj` into a FileDataModel."))

(defn file-data-model
  "Returns a FileDataModel from `path`, or nil if it doesn't exists."
  [path] (-file-data-model path))

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
