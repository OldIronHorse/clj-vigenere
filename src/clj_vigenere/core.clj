(ns clj-vigenere.core
  (:gen-class))

(defn encryption-table
  [alphabet]
  (zipmap
    alphabet
    (map
      (fn [row] (zipmap alphabet row))
      (map
        (fn [_ n] (concat (drop n alphabet) (take n alphabet)))
        alphabet (range)))))
