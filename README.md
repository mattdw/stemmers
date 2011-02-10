# stemmers

A collection of stemmers in Clojure, ready to plug into your own code.

## Usage

All stemmers export a `stem` top-level function, which takes a single
word and returns its stem. Additionally, `stemmers.core` provides
basic plumbing with the `stems` function:

    (require '(stemmers core soundex porter))
    ;; defaults to porter stemmer
    (stemmers.core/stems "a phrase for stemming")
    => ("phrase" "for" "stem")
    ;; other stemmers are easily used
    (stemmers.core/stems "a phrase for stemming" stemmers.soundex/stem)
    => ("P620" "F600" "S355")

## License

Copyright (C) 2010 Matt Wilson

Distributed under the Eclipse Public License, the same as Clojure.
