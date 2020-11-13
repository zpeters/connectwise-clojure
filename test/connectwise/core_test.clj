(ns connectwise.core-test
  (:require [clojure.test :refer [deftest testing is]]
           [connectwise.core :refer [get-connectwise get-all-connectwise]]))


(deftest get-connectwise-test
  (testing "basic gets"
    (testing "get system info" (is (= true (:isCloud (get-connectwise "/system/info")))))
    (testing "get some members, default 25" (is (= 25 (count (get-connectwise "/system/members")))))
    (testing "get some members, default 25" (is (not= 26 (count (get-connectwise "/system/members"))))))
  (testing "gets using conditions"
    (testing "get some members, page size 26" (is (= 26 (count (get-connectwise "/system/members" {:pagesize 26})))))
    (testing "get some members, just zpeters" (is (= 1 (count (get-connectwise "/system/members" {:conditions "identifier='zpeters'"})))))
    (testing "get a lot of members, just zpeters" (is (= 1 (count (get-connectwise "/system/members" {:conditions "identifier='zpeters'" :pagesize 200})))))))

(deftest get-all-connectwise-test
  (testing "get all"
    (testing "members should match"
      (is (= (get-connectwise "/system/members" {:pagesize 400}) (get-all-connectwise "/system/members"))))
    (testing "get all system members should be greater than 25"
      (is (> (count (get-all-connectwise "/system/members")) 25)))
    (testing "we should merge over a set page size"
      (is (> (count (get-all-connectwise "/system/members" {:pagesize 10})) 50)))
    (testing "members count should match count from api"
      (is (=
           (:count (get-connectwise "/system/members/count"))
           (count (get-all-connectwise "/system/members" {:fields "identifier"})))))))
