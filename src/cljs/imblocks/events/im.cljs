(ns imblocks.events.im
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]
            [imcljs.fetch :as fetch]
            [imblocks.fx :as fx]))

(reg-event-fx ::fetch-model
              (fn [{db :db} [_ service]]
                {:db db
                 ::fx/io {:channel (fetch/model service)
                          :on-success [::store-model]}}))

(reg-event-db ::store-model
              (fn [db [_ model]]
                (assoc-in db [:service :model] model)))