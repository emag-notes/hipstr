(ns hipstr.test.handler_test
  (:use clojure.test
        ring.mock.request
        hipstr.handler))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= 200 (:status response)))))

  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= 404 (:status response))))))

(deftest missing-email-address-redisplayes-the-from
  (let [response (app (request :get "/signup" {:usernmae "TheDude" :password "12345678"}))]
    (is (= 200 (:status response)))))