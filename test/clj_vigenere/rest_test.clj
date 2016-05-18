(ns clj-vigenere.rest-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [clj-vigenere.core :refer :all]
            [clj-vigenere.rest :refer :all]))

(deftest test-encrypt
  (testing "encrypt"
    (with-redefs
      [encryption-table
        (fn [alphabet]
          (is (= "alphabettouse" alphabet))
          :encryptiontable)
       encrypt-decrypt
        (fn [table key text]
          (is (= :encryptiontable table))
          (is (= "keytouse" key))
          (is (= "plaintexttouse" text))
          "cyphertext")]
      (let [response (app-routes
                        (-> (request :post "/encrypt")
                            (content-type "application/json")
                            (assoc
                              :body
                              {:key "keytouse"
                               :alphabet "alphabettouse"
                               :plaintext "plaintexttouse"})))]
        (is (= 200 (:status response)))
        (is (= "cyphertext" (-> response :body :cyphertext))))))
  (testing "decrypt"
    (with-redefs
      [decryption-table
        (fn [alphabet]
          (is (= "alphabettouse" alphabet))
          :decryptiontable)
       encrypt-decrypt
        (fn [table key text]
          (is (= :decryptiontable table))
          (is (= "keytouse" key))
          (is (= "cyphertexttouse" text))
          "plaintext")]
    (let [response (app-routes
                      (-> (request :post "/decrypt")
                          (content-type "application/json")
                          (assoc
                            :body
                            {:key "keytouse"
                             :alphabet "alphabettouse"
                             :cyphertext "cyphertexttouse"})))]
      (is (= 200 (:status response)))
      (is (= "plaintext" (-> response :body :plaintext)))))))
