(ns clj-vigenere.core-test
  (:require [clojure.test :refer :all]
            [clj-vigenere.core :refer :all]))

(deftest test-encryption-table
  (testing "Short alphabet"
    (is (= {\A {\A \A, \B \B, \C \C, \D \D}
            \B {\A \B, \B \C, \C \D, \D \A}
            \C {\A \C, \B \D, \C \A, \D \B}
            \D {\A \D, \B \A, \C \B, \D \C}}
           (encryption-table "ABCD")))))

(deftest test-decryption-table
  (testing "Short alphabet"
    (is (= {\A {\A \A, \B \B, \C \C, \D \D},
            \B {\A \D, \B \A, \C \B, \D \C},
            \C {\A \C, \B \D, \C \A, \D \B},
            \D {\A \B, \B \C, \C \D, \D \A}},
           (decryption-table "ABCD")))))

