(ns metabox.core
  (:refer-clojure :exclude (val))
  (:import [metabox MetaBox]))

(defn box
  ([] (MetaBox.))
  ([v] (MetaBox. v))
  ([v meta] (MetaBox. v meta)))

(defn val
  [^MetaBox x]
  (.val x))
