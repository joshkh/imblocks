(ns imblocks.subs
  (:require [re-frame.core :as re-frame]
            [imblocks.subs.model]
            [imblocks.subs.query]
            [imblocks.subs.ui]
            ))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))
