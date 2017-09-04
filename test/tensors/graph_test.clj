(ns tensors.graph-test
  (:require [tensors.graph :refer :all]
            [clojure.test :refer :all]))

(deftest post-order-nodes-test
  (let [g {:node :a :children [{:node :b} {:node :c}]}]
    (is [:b :c :a] (map :node (post-order-nodes g)))))

(deftest top-down-walk-test
  (testing "top-down walk"
    (let [g {:val :a :children [{:val :b :children [{:val :b1}]} {:val :c}]}
          counter (atom 0)
          walk-fn (fn [n _]
                    (assoc n :stamp (swap! counter inc)))
          walked (top-down-walk g walk-fn)]
      (doseq [n (post-order-nodes walked)
              c (:children n)]
        (is (< (:stamp n) (:stamp c)))))))
