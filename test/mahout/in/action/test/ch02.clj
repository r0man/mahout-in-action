(ns mahout.in.action.test.ch02
  (:use clojure.test
        mahout.in.action.ch02))

(deftest test-recommend-intro
  (is (= [104 106] (map :item (recommend-intro 2 1 2)))))