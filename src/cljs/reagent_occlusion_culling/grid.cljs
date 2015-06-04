(ns reagent-occlusion-culling.grid
  (:require [reagent.core :as reagent :refer [atom]]))

(defn grid-table-data
  [data]
  [:tbody
    (for [item data]
      ^{:key (:id item)}
      [:tr
        [:td (:id item)]
        [:td (:text item)]])])

(defn grid-container
  []
  (let [row-height 20
        row-data '[{:id 1 :text "test"} {:id 2 :text "one"} {:id 3 :text "389hw"} {:id 4 :text "328hg"}
                   {:id 5 :text "test"} {:id 6 :text "one"} {:id 7 :text "389hw"} {:id 8 :text "328hg"}
                   {:id 9 :text "test"} {:id 10 :text "one"} {:id 11 :text "389hw"} {:id 12 :text "328hg"}]
        full-height (* row-height (count row-data))
        top (atom 0)
        grid-height 150]
    (fn []
      (let [first-item (bit-or (/ @top row-height) 0)
            last-item (min (count row-data) (+ first-item (bit-or (/ grid-height row-height) 0) 2))]
      [:div
        [:span first-item]
        [:span last-item]
        [:div.Grid {:style {:height grid-height
                            :overflow "auto"}
                    :on-scroll #(reset! top (->> %
                                                 .-target
                                                 .-scrollTop))}
          [:div.Grid__wrapper {:style {:height full-height}}
            [:table.Grid__table {:style {:position :relative
                                         :top @top}}
              [grid-table-data (subvec row-data first-item last-item)]]]]]))))
