(ns imblocks.views.app
  (:require [reagent.core :as r]
            [re-frame.core :refer [dispatch subscribe]]))

(defn class-item []
  (fn [[class-kw {:keys [displayName] :as details}]]
    [:li {:on-click (fn []
                      (js/console.log "details" details))} displayName]))

(defn block []
  (fn [[class subclasses]]
    (js/console.log "x y" class subclasses)
    [:div.block
     #_(into [:ul.list-unstyled]
             (map (fn [class]
                    [class-item class]) (sort-by (comp :displayName second) classes)))]))

(defn main []
  (let [query      (subscribe [:imblocks.subs.query/query])
        classes    (subscribe [:imblocks.subs.model/classes])
        query-tree (subscribe [:imblocks.subs.query/query-tree])
        rels       (subscribe [:imblocks.subs.model/collections "Gene"])]
    (fn []
      (js/console.log "query-tree" @query-tree)
      (js/console.log "rels-rels" @rels)
      [:div.imblocks-container
       [block (first @query)]
       [:div.block.empty "?Block"]])))