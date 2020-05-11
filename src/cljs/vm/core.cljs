(ns vm.core
  (:require [reagent.core :as reagent]
            [cljs.test :as test :refer [is testing]]
            [devcards.core :as dc]))


(defn page []
  [:div
    [:div "Started"]])

(defn ^{:export true} main []
  (prn "loaded")
  (reagent/render page (.getElementById js/document "app"))
)


(dc/defcard-rg a-node
  [:div "a"])

(dc/deftest node-test
  (testing "is-nil"
    (is (nil? nil))))


