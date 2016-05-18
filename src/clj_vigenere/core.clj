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

(defn encrypt-decrypt
  [table key text]
  ;;TODO throw exception if either of these is false
  (if
    (and
      (every? #(contains? table %1) text)
      (every? #(contains? table %1) key))
    (apply str (map #(get-in table [%1 %2]) (cycle key) text))
    (throw (IllegalArgumentException. "Character not in table"))))
