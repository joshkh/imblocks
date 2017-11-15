(ns imblocks.events.ui
  (:require [re-frame.core :refer [reg-event-db reg-fx]]
            [oops.core :refer [ocall+]]))

(reg-event-db ::report-coordinates
              (fn [db [_ view coordinates-map]]
                (assoc-in db [:coordinates view] coordinates-map)))

(reg-event-db ::scroll
              (fn [db]
                (assoc db :scroll (.getTime (js/Date.)))))