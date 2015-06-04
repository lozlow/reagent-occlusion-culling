(ns reagent-occlusion-culling.grid
  (:require [reagent.core :as reagent :refer [atom]]
            [clojure.string :refer [split]]))

(defn grid-table-data
  [data]
  [:tbody
    (for [item data
          :let [classname (if (even? (:idx item)) "Grid__row--even" "Grid__row--odd")]]
      ^{:key (:idx item)}
      [:tr.Grid__row {:class classname}
        [:td.Grid__cell (:id item)]
        [:td.Grid__cell (:text item)]])])

(def paragraph
  (split "Aliquam vitae congue ipsum. Pellentesque varius, est sed feugiat eleifend, justo purus bibendum mi, a interdum ligula felis eu tellus. Aenean nec dui molestie, mollis nisi ac, placerat diam. Ut a nibh dictum, tincidunt erat eget, luctus metus. Vivamus maximus justo nec sapien commodo, non ullamcorper velit congue. Donec vestibulum mauris sed metus gravida, efficitur lobortis ex dictum. Ut imperdiet elementum imperdiet. Donec mollis ac arcu vel dictum. Etiam vehicula pretium ligula, ac rhoncus erat hendrerit pharetra. Vestibulum sed elit ut massa fringilla venenatis nec ut justo. Quisque luctus dolor nec urna aliquam, at ullamcorper dui semper. Mauris sagittis sapien."
         " "))

(defn random-data
  [count]
  (vec (map (fn [item] {:id item :text (rand-nth paragraph)}) (range 1 count))))

(def grid-data
  (atom (random-data 101)))

(defn grid-container
  [data]
  (let [row-height 20
        top (atom 0)
        grid-height 150]
    (fn [data]
      (let [data (vec (map-indexed #(assoc %2 :idx (inc %)) @data))
            first-item (bit-or (/ @top row-height) 0)
            full-height (* row-height (count data))
            last-item (min (count data) (+ first-item (bit-or (/ grid-height row-height) 0) 2))]
      [:div
        [:span (str "Showing row indices " (inc first-item) " to " last-item)]
        [:div.Grid {:style {:height grid-height
                            :overflow "auto"}
                    :on-scroll #(reset! top (->> % .-target .-scrollTop))}
          [:div.Grid__wrapper {:style {:height full-height}}
            [:table.Grid__table {:style {:position :relative
                                         :top @top}}
              [grid-table-data (subvec data first-item last-item)]]]]]))))
