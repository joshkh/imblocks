(ns imblocks.subs.model
  (:require [re-frame.core :refer [reg-sub]]
            [imcljs.path :as path]))

(reg-sub ::model
         (fn [db]
           (get-in db [:service :model])))

(reg-sub ::classes
         :<- [::model]
         (fn [model]
           (:classes model)))

(reg-sub ::collections
         :<- [::model]
         (fn [model [_ path]]
           (when model (sort-by :displayName (path/relationships model path)))))

