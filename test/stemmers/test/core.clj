(ns stemmers.test.core
  (:use [stemmers.core] :reload)
  (:use [clojure.test]))

(deftest basic-tokenise
  (is (= (tokenise "nothing ever happens")
         ["nothing" "ever" "happens"])))

(deftest default-stem
  (is (= (stems "the hungry dog hungrily jumped over the angry moon with a hunger, jumping with anger.")
         ["hungri" "dog" "hungrili" "jump" "over" "angri" "moon" "with" "hunger" "jump" "with" "anger"])))
