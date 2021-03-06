(ns imblocks.db)

(def default-db
  {:name "re-frame"
   :service {:root "http://beta.flymine.org/beta"}
   :query {:from "Gene"
           :select ["Gene.organism.name"
                    "Gene.diseases.name"
                    "Gene.diseases.publications.title"
                    "Gene.homologues.homologue.symbol"]}
   :coordinates {}})
