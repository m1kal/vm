(ns vm.core
  (:require [reagent.core :as reagent]
            [cljs.test :as test :refer [is testing]]
            [cljs.reader :as reader]
            [devcards.core :as dc]))

(enable-console-print!)

(defonce regs (reagent/atom [0 0 0 0 0 0 0 0]))
(defonce prog (reagent/atom (vec (repeat 32 "nop"))))
(defonce sel-op (reagent/atom "+"))
(def pc (reagent/atom 0))

(defn oper []
  (condp = @sel-op
    "+" +
    "-" -
    "*" *
    "/" /))


(defn regbank [regs]
  (let [r @regs]
    [:div
      [:table {:style {:border "solid" :margin-left "20px"}}
        [:thead [:tr (for [i (range 8)] ^{:key i} [:th (inc i)])]]
        [:tbody
          [:tr
            (for [i (range 8)] ^{:key i}
              [:td {:style {:border :solid :width "20px" :text-align :center
                            :background (condp = i 0 "#E0FFE0" 1 "#E0FFE0" 7 "#40FFFF" "#FFFFFF")}} (r i)])]]]]))

(defn help []
  [:div {:style {:position :relative :top "-350px" :left "600px"}}
   "Instructions:"
   [:ul
    [:li "set X Y - sets register X to value Y"]
    [:li "copy X Y - sets register Y to the value of register Y"]
    [:li "inc X - increments register X by 1"]
    [:li "dec X - decrements register X by 1"]
    [:li "setop X - sets operation to X. Supported values: +, -, *, /"]
    [:li "goto X - jumps to instruction X"]
    [:li "eval - adds registers 0 and 1, the sum is stored in register 7"]]])

(defn progmem [prog pc]
  (let [p @prog c @pc]
    [:div
     [:div "Program:"]
     (for [i (range 32)]
       ^{:key i}
       [:input {:type "text"
                :style {:background (if (= c i) "#40FF40" "#C0C0C0")}
                :on-change #(swap! prog assoc i (.-value (.-target %))) :value (p i)}])

     ]))


(defn run-op [op]
  (let [[a & b] (clojure.string/split op " ")]
    (condp = a
      "set" (swap! regs assoc (dec (reader/read-string (first b))) (reader/read-string (second b)))
      "inc" (swap! regs update (dec (reader/read-string (first b))) inc)
      "dec" (swap! regs update (dec (reader/read-string (first b))) dec)
      "copy" (swap! regs assoc (dec (reader/read-string (last b))) (@regs (dec (reader/read-string (first b)))))
      "eval" (swap! regs assoc 7 ((oper) (@regs 0) (@regs 1)))
      "goto" (reset! pc (dec (reader/read-string (first b))))
      "setop" (reset! sel-op (first b))
      nil)))

(defn run-single []
  (let [p @pc]
    (swap! pc inc)  
    (run-op (@prog p))))

(defn vm [regs]
  (fn []
    [:div {:style {:width "400px" :border :solid }}
     [:div
      [:div "Registers"]
      [:div [regbank regs]]
      [:div {:style {:position :relative :left "320px" :top "-60px" }} "Op " @sel-op]]]))

; (dc/defcard-rg a-node
;   [:div "a" [:div "b"]])

; (dc/deftest node-test
;   (testing "is-nil"
;     (is (nil? nil))))

(defn page []
  [:div
   "The VM"
   [vm regs]
   [progmem prog pc]
   [:button {:on-click #(doseq [i @prog] (run-op i))} "Run"]
   [:button {:on-click #(run-single)} "Run single op"]
   [:button {:on-click #(reset! prog (vec (repeat 32 "nop")))} "Reset program memory"]
   [:button {:on-click #(reset! pc 0)} "Restart program"]
   [help]])

(defn ^{:export true} main []
  (reagent/render page (.getElementById js/document "app")))

(main)