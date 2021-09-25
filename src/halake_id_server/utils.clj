(ns halake-id-server.utils)

(defn safety-mkdir [dir]
  (when-not (.isDirectory dir)
    (.mkdir dir)))

(defn rand-str [strs len]
  (apply str (take len (repeatedly #(rand-nth strs)))))
