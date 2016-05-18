(ns clj-vigenere.rest-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            (clj-vigenere.core :refer :all]
            [clj-vigenere.rest :refer :all]))

(deftest test-encrypt
  (testing "encrypt"
    (with-redefs
      [encryption-table
        (fn [alphabet]
          (is (= "alphabettouse" alphabet)))]
      (let [response (app-routes
                        (-> (request :post "/encrypt")
                            (content-type "application/json")
                            (assoc
                              :body
                              {:key "keytouse"
                               :alphabet "alphabettouse"
                               :plaintext "plaintexttouse"})))]
        (is (= 200 (:status response)))
        (is (= "cyphertext" (-> response :body :cyphertext)))))))
