(ns imblocks.subs.model
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub ::classes
         (fn [db]
           (get-in db [:service :model :classes])))