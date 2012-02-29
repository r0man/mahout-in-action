(ns mahout.in.action.ch02
  (:import [org.apache.mahout.cf.taste.impl.eval AverageAbsoluteDifferenceRecommenderEvaluator RMSRecommenderEvaluator GenericRecommenderIRStatsEvaluator]
           [org.apache.mahout.cf.taste.eval RecommenderBuilder RecommenderIRStatsEvaluator])
  (:use mahout.core))

(def intro-dataset-path "test-resources/intro.csv")

(defn make-recommender [model n]
  (let [similarity (pearson-correlation-similarity model)
        neighborhood (nearest-n-user-neighborhood n similarity model)]
    (generic-user-based-recommender model neighborhood similarity)))

(defn recommend-intro [n user-id how-many]
  (let [recommender (make-recommender (data-model intro-dataset-path) n)]
    (recommend recommender user-id how-many)))

(defn evaluate-intro [n train-percent eval-percent]
  (let [evaluator (AverageAbsoluteDifferenceRecommenderEvaluator.)
        builder (reify RecommenderBuilder
                  (buildRecommender [_this model]
                    (make-recommender model n)))]
    (.evaluate evaluator builder nil (data-model intro-dataset-path) train-percent eval-percent)))

(defn precision-recall-intro [n]
  (let [evaluator (GenericRecommenderIRStatsEvaluator.)
        builder (reify RecommenderBuilder
                  (buildRecommender [_this model]
                    (make-recommender model n)))]
    (.evaluate evaluator builder nil (data-model intro-dataset-path) nil n GenericRecommenderIRStatsEvaluator/CHOOSE_THRESHOLD 1.0)))