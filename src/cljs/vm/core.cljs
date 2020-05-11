(ns vm.core
  (:require [reagent.core :as reagent]
            [cljs.test :as test :refer [is testing]]
            [cljs.reader :as reader]
            [devcards.core :as dc]))

(enable-console-print!)



(defonce regs (reagent/atom [0 0 0 0 0 0 0 0]))
(defonce prog (reagent/atom (vec (repeat 32 "nop"))))

(defn regbank [regs]
  (let [r @regs]
  [:div {:style {:display :inline}}
   [:table {:style {:border "solid"}}
    [:thead [:tr
     (for [i (range 8)]
       ^{:key i}
       [:th i])]]
    [:tbody
    [:tr
     (for [i (range 8)]
       ^{:key i}
       [:td {:style {:border :solid :width "20px" :text-align :center}} (r i)])
     ]]
    ]]))

(defn progmem [prog]
  (let [p @prog]
  [:div
   [:div "Program:"]
   (for [i (range 32)]
     ^{:key i}
     [:input {:type "text" :on-change #(swap! prog assoc i (.-value (.-target %))) :value (p i)}]
     )
   [:div "Instructions:"
    [:ul
     [:li "set X Y - sets register X to value Y"]
     [:li "copy X Y - sets register Y to the value of register Y"]
     [:li "inc X - increments register X by 1"]
     [:li "eval - adds registers 0 and 1, the sum is stored in register 7"]
     ]]
   ]))


(defn run [op]
  (let [[a & b] (clojure.string/split op " ")]
    (condp = a
      "set" (swap! regs assoc (reader/read-string (first b)) (reader/read-string (second b)))
      "inc" (swap! regs update (reader/read-string (first b)) inc)
      "dec" (swap! regs update (reader/read-string (first b)) dec)
      "copy" (swap! regs assoc (reader/read-string (last b)) (@regs (reader/read-string (first b))))
      "eval" (swap! regs assoc 7 (+ (@regs 0) (@regs 1)))
      nil
      )))

(defn vm [regs]
 (fn []
   [:div {:style {:width "400px" :border :solid }}
    [:div {:style {:display :inline}}
    [:div
     "Registers"]
    [:div {:style {:display "inline"}}
     [regbank regs]
     "+"
     ]
    ]]))

(dc/defcard-rg a-node
  [:div "a" [:div "b"]])


(dc/deftest node-test
  (testing "is-nil"
    (is (nil? nil))))

(defn page []
  [:div
   "The VM" [vm regs] [progmem prog]
    [:button {:on-click #(doseq [i @prog] (run i))} "Run"]
   ])

(defn ^{:export true} main []
  (prn "loaded")
  (reagent/render page (.getElementById js/document "app")))

(main)