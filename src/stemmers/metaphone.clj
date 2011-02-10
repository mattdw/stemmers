(ns stemmers.metaphone
  (:require [clojure.string :as str]))

(defn stem
  [word]
  (-> (.toLowerCase word)

      ;; Drop duplicate adjacent letters, except for C.

      (str/replace #"([^c])\1" "$1")

      ;; If the word begins with 'KN', 'GN', 'PN', 'AE', 'WR', drop
      ;; the first letter.

      (str/replace #"^(kn|gn|pn|ae|wr)" (fn [[m _]] (str (rest m))))

      ;; Drop 'B' if after 'M' and if it is at the end of the word.

      (str/replace #"mb$" "m")

      ;; 'C' transforms to 'X' if followed by 'IA' or 'H' (unless in
      ;; latter case, it is part of '-SCH-', in which case it
      ;; transforms to 'K'). 'C' transforms to 'S' if followed by 'I',
      ;; 'E', or 'Y'. Otherwise, 'C' transforms to 'K'.

      (str/replace #"([^^])sch([^$])" "$1skh$2")
      (str/replace #"c(ia|h)"  "x$1")
      (str/replace #"c([iey])" "s$1")
      (str/replace "c" "k")

      ;; 'D' transforms to 'J' if followed by 'GE', 'GY', or
      ;; 'GI'. Otherwise, 'D' transforms to 'T'.

      (str/replace #"d(ge|gy|gi)" "j$1")
      (str/replace "d" "t")

      ;; Drop 'G' if followed by 'H' and 'H' is not at the end or
      ;; before a vowel. Drop 'G' if followed by 'N' or 'NED' and is
      ;; at the end.

      (str/replace #"g(h[^$aeiou])" "$1")
      (str/replace #"gn(ed)?$" "n$1")

      ;; 'G' transforms to 'J' if before 'I', 'E', or 'Y', and it is
      ;; not in 'GG'. Otherwise, 'G' transforms to 'K'. Reduce 'GG' to
      ;; 'G'.

      (str/replace #"([^g])g([iey])" "$1j$2")
      (str/replace #"([^g])g([^g])"  "$1k$2")
      (str/replace "gg" "g")

      ;; Drop 'H' if after vowel and not before a vowel.
      
      (str/replace #"([aeiou])h([^aeiou])" "$1$2")

      ;; 'CK' transforms to 'K'.
      
      (str/replace "ck" "k")

      ;; 'PH' transforms to 'F'.

      (str/replace "ph" "f")
      
      ;; 'Q' transforms to 'K'.

      (str/replace "q" "k")

      ;; 'S' transforms to 'X' if followed by 'H', 'IO', or 'IA'.

      (str/replace #"s(h|io|ia)" "x$1")

      ;; 'T' transforms to 'X' if followed by 'IA' or 'IO'. 'TH'
      ;; transforms to '0'. Drop 'T' if followed by 'CH'.
      
      (str/replace #"t(ia|io)" "x$1")
      (str/replace "th" "0")
      (str/replace "tch" "ch")

      ;; 'V' transforms to 'F'.

      (str/replace "v" "f")

      ;; 'WH' transforms to 'W' if at the beginning. Drop 'W' if not
      ;; followed by a vowel.

      (str/replace #"^wh" "w")
      (str/replace #"w([^aeiou]|$)" "$1")

      ;; 'X' transforms to 'S' if at the beginning. Otherwise, 'X'
      ;; transforms to 'KS'.

      (str/replace #"^x" "s")
      (str/replace "x" "ks")

      ;; Drop 'Y' if not followed by a vowel.

      (str/replace #"y([^aeiou]|$)" "$1")

      ;; 'Z' transforms to 'S'.

      (str/replace "z" "s")

      ;; Drop all vowels unless it is the beginning.

      (str/replace #"([^^])[aeiou]" "$1")
      ))
