(ns imblocks.views.app
  (:require [reagent.core :as r]
            [re-frame.core :refer [dispatch subscribe]]))

(defn main []
  (fn []
    [:div.imblocks-container
     [:div.block "1Block"]
     [:div.block.empty "?Block"]]))