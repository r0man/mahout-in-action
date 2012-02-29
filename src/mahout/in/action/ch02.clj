(ns mahout.in.action.ch02
  (:import [org.apache.mahout.cf.taste.impl.model.file FileDataModel]
           [org.apache.mahout.cf.taste.impl.similarity PearsonCorrelationSimilarity]
           [org.apache.mahout.cf.taste.impl.recommender GenericUserBasedRecommender]
           [org.apache.mahout.cf.taste.impl.neighborhood NearestNUserNeighborhood]
           [org.apache.mahout.cf.taste.eval RecommenderBuilder RecommenderIRStatsEvaluator]
           [org.apache.mahout.cf.taste.impl.eval AverageAbsoluteDifferenceRecommenderEvaluator RMSRecommenderEvaluator GenericRecommenderIRStatsEvaluator]
           [org.apache.mahout.common RandomUtils]
           [java.io File]))
