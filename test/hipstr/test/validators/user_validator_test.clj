(ns hipstr.test.validators.user-validator-test
  (:require [hipstr.validators.user-validator :as uv])
  (:use clojure.test))

(defn assert-error-message
  "Asserts that a given error message set contains a single error message
   and matches an expected message."
  [expected errors]
  (is (= 1 (count errors)))
  (is (= expected (first errors))))

;; username tests
(defn validate-username [username]
  "Validates the provided username for us, and returns the
   set of validation messages for the username, if any."
  (:username (uv/username-validator {:username username})))

(deftest blank-username-returns-a-username-required-message
  (assert-error-message uv/username-blank-msg (validate-username "")))

(deftest invalid-username-returns-appropriate-message
  (assert-error-message uv/username-invalid-msg (validate-username "Yea! Illegal Characters!")))

(deftest valid-username-returns-0-messages
  (let [result (validate-username "TheDude")]
    (is (= 0 (count result)))))

;; password tests
(defn validate-password [password]
  "Validates the provided password for us, and returns the
   set of validation messages for the password, if any."
  (:password (uv/password-validator {:password password})))

(deftest blank-password-returns-a-password-required-message
  (assert-error-message uv/password-blank-msg (validate-password "")))

(deftest password-must-be-at-least-8-characters-long
  (assert-error-message uv/password-invalid-msg (validate-password "1234567")))

(deftest password-must-be-less-than-100-characters-long
  (let [pwd (clojure.string/join (repeat 101 "a"))]
    (assert-error-message uv/password-invalid-msg (validate-password pwd))))

(deftest valid-albeit-crappy-password-returns-0-messages
  (let [result (validate-password "12345678")]
    (is (= 0 (count result)))))

;; email tests
(defn validate-email [email]
  "Validates the provided email for us, and returns the
   set of validation messages for the email, if any."
  (:email (uv/email-validator {:email email})))

(deftest blank-email-returns-email-is-required-message
  (assert-error-message uv/email-blank-msg (validate-email "")))

(deftest invalid-email-returns-appropriate-message
  (assert-error-message uv/email-format-msg (validate-email "hoge@gmail")))

(deftest valid-email-returns-0-messages
  (let [result (validate-email "hoge@gmail.com")]
    (is (= 0 (count result)))))