(ns halake-id-server.repository.ids)

(defprotocol ids-repository
  (gen-id [this])
  (exists-id? [this id])
  (upload [this id filename file]))
