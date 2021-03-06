(ns vm.core
  (:require [reagent.core :as reagent]
            [cljs.reader :as reader]
            [vm.text :as t]))

(enable-console-print!)

(defonce language (reagent/atom "polish"))
(defonce registers (reagent/atom [0 0 0 0 0 0 0 0]))
(defonce program (reagent/atom (vec (repeat 32 "nop"))))
(defonce operator (reagent/atom "+"))
(defonce screen-content (reagent/atom ""))
(def pc (reagent/atom 0))

(defn display [text]
  (text (condp = @language "polish" t/polish t/english)))

(defn oper []
  (condp = @operator "+" + "-" - "*" * "/" /))

(defn run-instruction [input]
  (let [[opcode & args] (clojure.string/split input " ")]
    (prn opcode args)
    (condp = opcode
      (display :op-set) (swap! registers assoc (dec (reader/read-string (first args))) (reader/read-string (second args)))
      (display :op-inc) (swap! registers update (dec (reader/read-string (first args))) inc)
      (display :op-dec) (swap! registers update (dec (reader/read-string (first args))) dec)
      (display :op-copy) (swap! registers assoc (dec (reader/read-string (last args))) (@registers (dec (reader/read-string (first args)))))
      (display :op-eval) (swap! registers assoc 7 ((oper) (@registers 0) (@registers 1)))
      (display :op-goto) (reset! pc (dec (reader/read-string (first args))))
      (display :op-setop) (reset! operator (first args))
      (display :op-if) (if (zero? (@registers (dec (reader/read-string (first args))))) (run-instruction (clojure.string/join " " (rest args))))
      (display :op-disp) (reset! screen-content (str (@registers (dec (reader/read-string (first args))))))
      (display :op-disp-add) (swap! screen-content #(str % " " (@registers (dec (reader/read-string (first args))))))
      (display :op-cls) (reset! screen-content "")
      nil)))

(defn execute []
  (let [current-pc @pc]
    (swap! pc inc)
    (run-instruction (@program current-pc))))

; Reagent components

(defn regbank [registers]
  (let [regs @registers]
    [:div
      [:table {:style {:border "solid" :margin-left "20px"}}
        [:thead [:tr (for [idx (range 8)] ^{:key idx} [:th (inc idx)])]]
        [:tbody
          [:tr
            (for [idx (range 8)] ^{:key idx}
              [:td {:style {:border :solid :width "20px" :text-align :center
                            :background (condp = idx 0 "#E0FFE0" 1 "#E0FFE0" 7 "#40FFFF" "#FFFFFF")}}
                   (regs idx)])]]]]))

(defn vm [registers]
  (fn []
    [:div {:style {:width "400px" :border :solid }}
      [:div (display :registers)]
      [:div [regbank registers]]
      [:div {:style {:position :relative :left "320px" :top "-60px" }} "Op " @operator]]))

(defn progmem [program pc]
  (let [code @program pc @pc]
    [:div {:style {:width "200px"}}
     [:div (display :program)]
     (for [idx (range 32)] ^{:key idx}
       [:div {:style {:width "200px"}}
         [:span {:style {:display :inline-block :width "40px"}} (inc idx)]
         [:input {:type "text"
                  :style {:width "160px" :background (if (= pc idx) "#40FF40" "#C0C0C0")}
                  :on-change #(swap! program assoc idx (.-value (.-target %))) :value (code idx)}]])]))

(defn screen [content]
  [:div {:style {:position :relative :left "220px"
                 :top (str "-" (* 32 22) "px")
                 :border "solid"
                 :width "300px"
                 :height "200px"
                 :font-size "50"
                 :padding "10px"}}
    @content])

(defn app []
  [:div {:style {:position :relative}}
   [display :vm]
   [vm registers]
   [:select {:on-change #(reset! language (.-value (.-target %)))}
    [:option {:value :polish} "Polski"] [:option {:value :english} "English"]]
   [:button {:on-click #(execute)} [display :execute]]
   [:button {:on-click #(reset! program (vec (repeat 32 "nop")))} [display :reset]]
   [:button {:on-click #(reset! pc 0)} [display :restart]]
   [progmem program pc]
   [screen screen-content]
   [display :help]])

(reagent/render app (.getElementById js/document "app"))