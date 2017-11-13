(ns imblocks.fx
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [re-frame.core :refer [reg-fx dispatch]]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))


(reg-fx
  ::io
  (fn [{:keys [channel
               on-success
               on-error
               on-unauthorised]}]
    (go (let [{:keys [status body]} (<! channel)]
          (cond
            (<= 200 status 399) (when on-success (dispatch (conj on-success body)))
            (<= 400 status 499) (when on-unauthorised (dispatch (conj on-unauthorised body)))
            (>= status 599) (when on-error (dispatch (conj on-error body)))
            :else nil)))))