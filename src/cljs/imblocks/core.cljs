(ns imblocks.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [imblocks.events :as events]
            [imblocks.views :as views]
            [imblocks.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (re-frame/dispatch [:imblocks.events.im/fetch-model {:root "http://beta.flymine.org/beta"}])
  (dev-setup)
  (mount-root))
