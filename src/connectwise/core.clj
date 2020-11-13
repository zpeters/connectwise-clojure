(ns connectwise.core
  (:require
   [config.core :refer [env]]
   [clj-http.client :as client]
   [cheshire.core :refer [parse-string]]
   [clojure.string :refer [includes?]]
   [tupelo.base64 :as b64])
  (:gen-class))

(defn- public-key [] (:connectwise-public-key env))
(defn- private-key [] (:connectwise-private-key env))
(defn- company-id [] (:connectwise-company-id env))
(defn- client-id [] (:connectwise-client-id env))

(defn- gen-connectwise-url
  "Generates a connectwise url including the correct site and version"
  [path]
  (let
      [api {:SiteUrl "api-na.myconnectwise.net" :Codebase "v4_6_release/"}]
    (str "https://" (:SiteUrl api) "/" (:Codebase api) "apis/3.0" path)))

(defn- gen-auth-header
  "generates a header (map) with everything needed to authenticate"
  [public-key private-key company-id client-id]
  {"Authorization" (str "Basic " (b64/encode-str (str company-id "+" public-key ":" private-key)))
   "clientid" client-id})

(defn get-connectwise "gets a connectwise url" [path & [params]]
  (->
   (gen-connectwise-url path)
   (client/get {:headers (gen-auth-header (public-key) (private-key) (company-id) (client-id))
                :query-params params
                :socket-timeout 30000 :connection-timeout 30000})
   (get :body)
   (as-> body (parse-string body true))))

(defn- get-connectwise-page "gets a connectwise url page" [path page & [params]]
  (->
   (gen-connectwise-url path)
   (client/get {:headers (gen-auth-header (public-key) (private-key) (company-id) (client-id))
                :query-params (merge params {:page page :pagesize 1000})
                :socket-timeout 30000 :connection-timeout 30000
                :pagination-type "forward-only"})))

(defn- next-page? "is there a next page" [http-response]
  (->
   (:headers http-response)
   (as-> page (get page "Link" "Missing"))
   (includes? "next")))

(defn get-all-connectwise "gets a connectwise url, gets all results through pagination" [path & [params]]
  (loop
   [acc []
    current-page 1]
    (let [page (get-connectwise-page path current-page params)
          body (parse-string (:body page) true)]
      (if (not (next-page? page))
        (concat acc body)
        (recur (concat acc body) (inc current-page))))))
