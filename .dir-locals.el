((nil . ((cider-preferred-build-tool           . clojure-cli)
         (cider-default-cljs-repl              . custom)
         (cider-custom-cljs-repl-init-form     . "(user/cljs-repl)")
         (cider-clojure-cli-global-options     . "-A:dev:test")
         (cider-shadow-default-options         . ":dev")
         (cider-auto-track-ns-form-changes     . nil)
         (cider-redirect-server-output-to-repl . t)
         (cider-repl-display-help-banner       . nil)
         (cider-save-file-on-load              . nil)
         (clojure-toplevel-inside-comment-form . t)
         (eval . (progn
                   (make-variable-buffer-local 'cider-jack-in-nrepl-middlewares)
                   (add-to-list 'cider-jack-in-nrepl-middlewares "shadow.cljs.devtools.server.nrepl/middleware")))))

 (clojure . ((eval . (define-clojure-indent
                       (assoc 0)
                       (ex-info 0))))))
