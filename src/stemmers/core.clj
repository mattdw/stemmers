;; ## Stemmers

;; A top-level interface to the stemmers, handling tokenising of
;; sentences and phrases, as well as removing extra-short and -long
;; words, and excluding common noisy words (see `*excluded-words*`).

(ns stemmers.core
  (:require [clojure.string :as str]
            [stemmers.porter]))

;; For later (internal) use.
(def default-stemmer ^{:private true} stemmers.porter/stem)

;; Set of specific words to exclude from stemming.
(def *excluded-words*
  #{"the" "and" "was" "are" "not" "you" "were" "that" "this" "did"
    "etc" "there" "they" "our" "their"})

;; Ignore words shorter than this.
(def *min-word-length* 3)

;; Ignore words longer than this.
(def *max-word-length* 30)

(defn excluded-word?
  "`true` if `word` matches our exclusion criteria."
  [word]
  (or (not (<= *min-word-length* (count word) *max-word-length*))
      (*excluded-words* word)))

(defn remove-excluded-words
  "Remove short and blacklisted words (see `*excluded-words*`)."
  [word-seq]
  (filter (complement excluded-word?)
          word-seq))

(defn expand-hyphenated-words
  "Expand any hyphenated words in seq into `[hyphenated-word hyphenated word]`."
  [word-seq]
  (mapcat (fn [^String w]
            (if (.contains w "-") (conj (seq (.split w "-")) w) [w]))
          word-seq))

;; ## Top-level interface

(defn tokenise
  "Tokenise a phrase, respecting exclusion criteria. e.g.:

       => (tokenise \"searching a set of words\")
       (\"searching\" \"set\" \"words\")"
  [^String txt]
  (-> (str/replace txt #"[^-\d\w]+" " ")
      (.toLowerCase)
      (str/split #"\s+")
      expand-hyphenated-words
      remove-excluded-words))

(defn stems
  "Stem all words in a phrase or sentence, with reference to `*max-` and `*min-word-length*` and `*excluded-words*`."
  ([phrase] (stems phrase default-stemmer))
  ([phrase stemmer-func]
     (map stemmer-func (tokenise phrase))))
