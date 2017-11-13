(ns imblocks.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [imblocks.core-test]))

(doo-tests 'imblocks.core-test)
