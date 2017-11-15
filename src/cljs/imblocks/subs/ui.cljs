(ns imblocks.subs.ui
  (:require [re-frame.core :refer [reg-sub]]
            [imcljs.path :as path]))

(reg-sub ::coordinates-of
         (fn [db [_ view]]
           (get-in db [:coordinates view])))

(reg-sub ::coordinates
         (fn [db [_]]
           (get db :coordinates)))

(reg-sub ::scroll
         (fn [db [_]]
           (get db :scroll)))

