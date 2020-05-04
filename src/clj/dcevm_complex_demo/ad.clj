(ns dcevm-complex-demo.ad
  (:import AD))

(defn variable [x]
  (AD. x 1.0))

(defn raise-to-power
  "Evaluates f(x) = x^n and f'(x)"
  [^AD ad-x]
  (.raiseToPower ad-x))

