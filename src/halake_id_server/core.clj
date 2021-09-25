(ns halake-id-server.core
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [halake-id-server.routes :refer [routes]]))

(defn create-server []
  (http/create-server
   {::http/routes #(route/expand-routes (deref #'routes))
    ::http/type   :jetty
    ::http/port   8890
    ::http/resource-path  "/public"
    ::http/join? false
    :env :dev}))

(http/start (create-server))
