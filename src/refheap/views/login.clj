(ns refheap.views.login
  (:require [refheap.models.login :as login]
            [stencil.core :as stencil]
            [noir.session :as session]
            [refheap.views.common :refer [body logged-in]]
            [compojure.core :refer [defroutes GET POST]]
            [noir.response :refer [redirect json]]))

(defn create-user-page [email]
  (session/flash-put! :email email)
  (stencil/render-file
    "refheap/views/templates/createuser"
    {:error (when-let [error (session/flash-get :error)]
              {:message error})}))

(defn create-user [{:keys [name]}]
  (if-let [email (session/flash-get :email)]
    (if (login/create-user email name)
      (redirect "/paste")
      (create-user-page email))))

(defn logout-user []
  (session/remove! :user)
  (redirect "/paste"))

(defn verify-user [host {:keys [assertion]}]
  (when-let [{:keys [email]} (login/verify-assertion host assertion)]
    (json
     (if-let [username (login/user-exists email)]
       {:login-html (logged-in username)}
       {:chooselogin-html (body (create-user-page email))}))))

(defroutes login-routes
  (POST "/user/create" {:keys [params]}
    (create-user params))
  (GET "/user/logout" []
    (logout-user))
  (POST "/user/verify" {params :params {host "host"} :headers}
    (verify-user host params)))