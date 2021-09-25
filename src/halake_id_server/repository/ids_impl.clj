(ns halake-id-server.repository.ids-impl
  (:require [clojure.java.io :as io]
            [halake-id-server.repository.ids :refer [ids-repository]]
            [halake-id-server.utils :refer [safety-mkdir rand-str]]))

(defonce id-strs "abcdefghjkmnpqrstuvwxyz0123456789")

(defn id-dir [codes-dir id]
  (let [dir (io/file codes-dir id)]
    dir))

(defrecord ids-repository-impl [codes-dir]
  ids-repository

  (gen-id [{:keys [codes-dir]}]
    (let [id (str (rand-str id-strs 3) "-" (rand-str id-strs 3))]
      (safety-mkdir (id-dir codes-dir id))
      id))

  (exists-id? [{:keys [codes-dir]} id]
    (.isDirectory (id-dir codes-dir id)))

  (upload [{:keys [codes-dir]} id filename file]
    (let [dest (io/file (id-dir codes-dir id) filename)]
      (.mkdirs (.getParentFile dest))
      (io/copy file dest))))

(defn create [codes-dir]
  (let [dir (io/file codes-dir)]
    (safety-mkdir dir)
    (->ids-repository-impl dir)))
