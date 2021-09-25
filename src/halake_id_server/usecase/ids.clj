(ns halake-id-server.usecase.ids)

(defprotocol ids-usecase
  (gen-id [this])
  (exists-id? [this id])
  (upload [this id filename file]))
