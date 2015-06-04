(ns reagent-occlusion-culling.app
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent-occlusion-culling.grid :as grid]))

(defn some-component []
  [:div
   [:h3 "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red"]
    " text."]])

(defn reset-with-component-dimensions!
  [atom component]
  )

(defn calling-component []
  [:div "Parent component"
   [some-component]
   [grid/grid-container]])

(defn init []
  (reagent/render-component [calling-component]
                            (.getElementById js/document "container")))
