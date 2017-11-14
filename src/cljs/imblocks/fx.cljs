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
    (go (let [{:keys [statusCode] :as response} (<! channel)]
          (dispatch (conj on-success response))))))