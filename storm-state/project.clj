(defproject storm/storm-state "0.8.0-SNAPSHOT"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-options {:debug "true" :fork "true"}
  :repositories {"cloudera" "https://repository.cloudera.com/artifactory/cloudera-repos/"}
  :dependencies [[org.apache.hadoop/hadoop-core "0.20.2-cdh3u4"]
                 ]
  :dev-dependencies [[org.clojure/clojure "1.4.0"]
                     [storm "0.8.0-SNAPSHOT"]]
)
