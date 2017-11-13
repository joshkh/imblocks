(ns imblocks.views
  (:require [re-frame.core :as re-frame]
            [imblocks.subs :as subs]
            [imblocks.views.app :as app]
            ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [app/main]))
