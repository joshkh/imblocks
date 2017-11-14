(ns imblocks.subs.model
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub ::model
         (fn [db]
           (get-in db [:service :model])))

(reg-sub ::classes
         :<- [::model]
         (fn [model]
           (:classes model)))

