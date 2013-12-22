(ns metabox.core-test
  (:require [clojure.test :refer :all]
            [metabox.core :refer (box)]))

(deftest metabox-test
  (testing "(box)"
    (let [mb (box)]
      (is (= nil @mb))
      (is (= nil (deref mb)))
      (is (= nil (meta mb)))
      (is (= {:band false} (meta (with-meta mb {:band false}))))))
  (testing "(box 20)"
    (let [mb (box 20)]
      (is (= 20 @mb))
      (is (= 20 (deref mb)))
      (is (= nil (meta mb)))
      (is (= {:band false} (meta (with-meta mb {:band false}))))
      (is (= "20" (str mb)))))
  (testing "(box 20 {:band false})"
    (let [mb (box 20 {:band false})]
      (is (= 20 @mb))
      (is (= 20 (deref mb)))
      (is (= {:band false} (meta mb)))
      (is (= {} (meta (with-meta mb {}))))
      (is (= "20" (str mb))))))
