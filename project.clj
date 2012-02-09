(defproject refheap "1.0.0"
  :description "This is like, totally a pastebin, dude."
  :url "https://refheap.com"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [noir "1.3.0-alpha8"]
                 [congomongo "0.1.8"]
                 [clj-config "0.2.0"]
                 [clj-http "0.3.1"]
                 [clavatar "0.2.0"]
                 [clj-time "0.3.3"]
                 [amalloy/mongo-session "0.0.1"]]
  :main refheap.server)

