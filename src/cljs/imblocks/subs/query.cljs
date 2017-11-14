(ns imblocks.subs.query
  (:require [re-frame.core :refer [reg-sub]]
            [clojure.string :as s]))

(reg-sub ::query
         (fn [db]
           (let [views (get-in db [:query :select])]
             (reduce (fn [m view]
                       (let [diced (s/split view ".")]
                         (assoc-in m diced {}))) {} views))))