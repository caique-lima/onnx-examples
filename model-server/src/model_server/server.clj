(ns model-server.server
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [model-server.diplomat.http-in :as http-in]))

(defn respond-hello [request]
  (http-in/inference request))

(def routes
  (route/expand-routes
    #{["/inference" :get respond-hello :route-name :inference]}))

(defn create-server []
  (http/create-server
    {::http/routes routes
     ::http/type :jetty
     ::http/port 8890}))

(defn start []
  (http/start (create-server)))

(defn -main
  [& args]
  (start))
