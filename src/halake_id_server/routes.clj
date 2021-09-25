(ns halake-id-server.routes
  (:require
   [io.pedestal.http :refer [json-response]]
   [io.pedestal.http.ring-middlewares :as middlewares]
   [clojure.java.io :as io]
   [halake-id-server.usecase.ids :refer [gen-id upload]]
   [halake-id-server.usecase.ids-impl :as ids-usecase-impl]
   [halake-id-server.repository.ids-impl :as ids-repository-impl]))

(defonce usecase
  (ids-usecase-impl/create
   (ids-repository-impl/create (io/file (or (System/getenv "CODES_PATH") "./resources/public/codes")))))

(defn gen-id-handler [_]
  (json-response (gen-id usecase)))

(defn upload-resource-handler
  [{{{file :tempfile} "file"} :params {:keys [id filename]} :path-params}]
  (prn id filename file)
  (try
    (if file
      (json-response {:path (upload usecase id filename file)})
      (throw (Exception. "no file")))
    (catch Exception e
      (prn e)
      (assoc (json-response "bad request") :status 400))))

(def routes
  #{["/api/ids" :post #'gen-id-handler
     :route-name :gen-id]

    ["/api/ids/:id/resources/*filename"
     :post [(middlewares/multipart-params) #'upload-resource-handler]
     :route-name :upload-resource]})
