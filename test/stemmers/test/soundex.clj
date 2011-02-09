(ns stemmers.test.soundex
  (:use clojure.test
        stemmers.soundex))

(deftest rupert-robert-rubin
  (is (= (stem "robert") "R163"))
  (is (= (stem "rupert") "R163"))
  (is (= (stem "rubin") "R150")))

(deftest ashcraft-ashcroft
  (is (= (stem "ashcraft") (stem "ashcroft") "A261")))

(deftest jumanji-fruittrees
  (is (= (stem "jumanji") "J552"))
  (is (= (stem "fruittress") "F636")))
