(ns dcevm-complex-demo.core
  (:require [dcevm-complex-demo.ad :as ad]))

(defn demo []
  (let [x 3.0
        y (ad/raise-to-power (ad/variable x))] 
    (println (str "x = " x ", f = " (._value y) ", dfdx = " (._deriv y)))))
