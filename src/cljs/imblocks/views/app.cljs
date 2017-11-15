(ns imblocks.views.app
  (:require [reagent.core :as r]
            [re-frame.core :refer [dispatch subscribe]]
            [oops.core :refer [oget ocall]]
            [clojure.string :as s]))

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

(defn report-coordinates [view e]
  (let [element  (-> e js/$)
        position (js->clj (ocall element :position) :keywordize-keys true)
        height   (ocall element :height)
        width    (ocall element :innerWidth)]
    (dispatch [:imblocks.events.ui/report-coordinates
               view
               {:top-left {:x (:left position) :y (:top position)}
                :top-right {:x (+ width (:left position)) :y (:top position)}
                :bottom-left {:x (:left position) :y (+ height (:top position))}
                :bottom-right {:x (+ width (:left position)) :y (+ height (:top position))}}])))

(defn section [view]
  (let [rels (subscribe [:imblocks.subs.model/collections view])
        scroll (subscribe [:imblocks.subs.ui/scroll])]
    (fn [view]
      @scroll
      [:div.section (str view)
       (into [:ul.ul-no-style]
             (map (fn [[prop-kw details]]
                    [:li
                     {:ref (fn [e]
                             (when e (report-coordinates
                                       (str view "." (:name details))
                                       e)))
                      :on-click (partial report-coordinates (str view "." (:name details)))}
                     (:displayName details)]) @rels))])))

(defn column []
  (fn [views]
    ;(str views)
    (into [:div.column]
          (map (fn [s] [section s]) views))))


(defn link []
  (fn [[parent child]]
    [:path.connector {:d (s/join " "
                       ["M" (:x (:top-right parent)) (:y (:top-right parent))
                        "L" (:x (:top-left child)) (:y (:top-left child))
                        "L" (:x (:bottom-left child)) (:y (:bottom-left child))
                        "L" (:x (:bottom-right parent)) (:y (:bottom-right parent))
                        "Z"])}]))


(defn svg-background []
  (let [links (subscribe [:imblocks.subs.query/links])]
    (fn []
      (js/console.log "links" @links)
      [:svg.testme
       (into [:g] (map (fn [l] [link l]) @links))])))

(defn main []
  (let [query      (subscribe [:imblocks.subs.query/query])
        classes    (subscribe [:imblocks.subs.model/classes])
        query-tree (subscribe [:imblocks.subs.query/query-tree])
        rels       (subscribe [:imblocks.subs.model/collections "Gene"])
        ]
    (fn []
      [:div
       {:on-scroll (fn [] (dispatch [:imblocks.events.ui/scroll]))}
       [svg-background]
       (into [:div.imblocks-container]
             (map (fn [c] [column c]) @query-tree))])))