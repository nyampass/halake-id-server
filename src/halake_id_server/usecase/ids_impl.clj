(ns halake-id-server.usecase.ids-impl
  (:require   [halake-id-server.usecase.ids :refer [ids-usecase]]
              [halake-id-server.repository.ids :refer :all]))

(defrecord ids-usecase-impl [repo]
  ids-usecase

  (gen-id [{:keys [repo]}] (gen-id repo))

  (upload [{:keys [repo]} id filename file]
    (if (and (exists-id? repo id) filename file)
      (do
        (upload repo id filename file)
        filename)
      (throw (Exception. "invalid params")))))

(defn create [repository]
  (->ids-usecase-impl repository))
