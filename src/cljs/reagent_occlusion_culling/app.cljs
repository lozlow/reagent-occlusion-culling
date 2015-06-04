(ns reagent-occlusion-culling.app
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent-occlusion-culling.grid :as grid]))

(defn reset-with-component-dimensions!
  [atom component]
  )

(defn add-row-button
  []
  (let [input (atom nil)]
  (fn []
    [:div
      [:input {:on-change #(reset! input (->> %
                                              .-target
                                              .-value))} @input]
      [:button {:on-click #(swap! grid/grid-data conj {:id (+ (count @grid/grid-data) 1) :text @input})} "Add row"]])))

(defn random-data-button
  []
  [:button {:on-click #(reset! grid/grid-data (grid/random-data 101))} "New random data"])

(defn add-random-rows-button
  []
  (let [input (atom nil)]
  (fn []
    [:div
      [:input {:on-change #(reset! input (->> %
                                              .-target
                                              .-value))} @input]
      [:button {:on-click #(reset! grid/grid-data (into (grid/random-data (inc @input)) @grid/grid-data))} "Push number of rows"]])))

(defn calling-component []
  [:div
   [add-row-button]
   [random-data-button]
   [add-random-rows-button]
   [grid/grid-container grid/grid-data]])

(defn init []
  (reagent/render-component [calling-component]
                            (.getElementById js/document "container")))
