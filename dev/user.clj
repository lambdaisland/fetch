(ns user)

(defmacro jit [sym]
  `(requiring-resolve '~sym))

(defn cljs-repl
  ([]
   (cljs-repl :dev))
  ([build-id]
   ((jit shadow.cljs.devtools.server/start!))
   ((jit shadow.cljs.devtools.api/watch) build-id)
   (loop []
     (when (nil? @@(jit shadow.cljs.devtools.server.runtime/instance-ref))
       (Thread/sleep 250)
       (recur)))
   ((jit shadow.cljs.devtools.api/nrepl-select) build-id)))
