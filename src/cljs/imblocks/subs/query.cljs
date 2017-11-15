(ns imblocks.subs.query
  (:require [re-frame.core :refer [reg-sub subscribe]]
            [clojure.string :as s]
            [imblocks.subs.model :as subs-model]
            [imblocks.subs.ui :as subs-ui]
            [imcljs.path :as path]
            ))

(reg-sub ::query
         (fn [db]
           (get db :query)))

(defn safe-take
  "If more is taken than the length of the collection then return nil"
  [n coll]
  (if (>= (count coll) n) (take n coll) nil))



(reg-sub ::query-tree
         :<- [::subs-model/model]
         :<- [::query]
         (fn [[model query]]
           (let [views (:select query)]
             (when model
               (let [views       views
                     class-views (distinct (keep (partial path/trim-to-last-class model) views))
                     split-views (map #(s/split % #"\.") class-views)
                     max-columns (apply max (map count split-views))]
                 (map keys (remove empty? (reduce (fn [total columns]
                                                    (conj total (dissoc (group-by (comp (partial s/join ".") (partial safe-take columns)) split-views) "")))
                                                  [] (range 0 (inc max-columns))))))))))

(reg-sub ::links
         :<- [::query-tree]
         :<- [::subs-ui/coordinates]
         (fn [[query-tree coordinates]]
           (let [linked-views (mapcat (fn [column] (map (fn [view] [(s/join "." (butlast (s/split view #"\."))) view]) column)) query-tree)]
             (map (fn [[parent child]]
                    [(get coordinates parent)
                     (get coordinates child)]) linked-views))))

