(ns dcevm-complex-demo.ad
  (:import AD))

(defn variable [x]
  (AD. (double x) 1.0))

(defn raise-to-power
  "Evaluates f(x) = x^n and f'(x), n being the AD/EXPONENT static variable"
  [^AD ad-x]
  (.raiseToPower ad-x))

