(ns stemmers.test.porter
  (:use
     clojure.test
     stemmers.porter
     stemmers.test.porter-test-cases))

(deftest stems
  (doseq [[input output] test-cases]
    (is (= (stem input) output) (str "for " input))))
