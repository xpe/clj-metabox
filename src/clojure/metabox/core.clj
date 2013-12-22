(ns metabox.core
  (:import [metabox MetaBox]))

(defn box
  ([] (MetaBox.))
  ([v] (MetaBox. v))
  ([v meta] (MetaBox. v meta)))
