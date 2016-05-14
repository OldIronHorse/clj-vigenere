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

(defn decryption-table
  [alphabet]
  (zipmap
    alphabet
    (map
      (fn [row] (zipmap alphabet row))
      (map
        ;; TODO: tidy this up. Extract rotate fn?
        (fn [_ n]
          (reverse
            (concat 
              (drop n (reverse alphabet))
              (take n (reverse alphabet)))))
        alphabet (range)))))
