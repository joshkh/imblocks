(ns imblocks.events
  (:require [re-frame.core :as re-frame]
            [imblocks.db :as db]
            [imblocks.events.im]))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/default-db))
