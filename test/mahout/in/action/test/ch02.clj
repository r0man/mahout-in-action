(ns mahout.in.action.test.ch02
  (:use clojure.test
        mahout.core
        mahout.in.action.ch02))

(deftest test-recommend-intro
  (is (= [104 106] (map :item (recommend-intro 2 1 2)))))

(deftest test-evaluate-intro
  (use-test-seed)
  (is (= 1.0 (evaluate-intro 2 0.7 1.0))))

(deftest test-precision-recall-intro
  (use-test-seed)
  (let [stats (precision-recall-intro 2)]
    (is (= 0.75 (.getPrecision stats)))
    (is (= 1.0 (.getRecall stats)))))