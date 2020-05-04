# dcevm-complex-demo

Investigate HotswapAgent loading issues.

## Usage

To demonstrate the issue, launch a REPL.

Evaluate this code:
```
dcevm-complex-demo.ad> (raise-to-power (variable 3.0))
```
and see the result:
```
#object[AD 0x45e16b0b "AD(value=27.0, deriv=27.0)"]
```
Then change some line in `src/java/AD.java`, e.g. from `public static int EXPONENT = 4;` to `public static int EXPONENT = 3;`. And compile with `lein javac`.

You will see a messgae lit this:
```
HOTSWAP AGENT: 10:00:21.894 RELOAD (org.hotswap.agent.config.PluginManager) - Reloading classes [AD] (autoHotswap)
```
which indicates that the class AD was reloaded.

Try to evaluate the above line again:
```
dcevm-complex-demo.ad> (raise-to-power (variable 3.0))
```
and we get this error:
``` 
Execution error (LinkageError) at dcevm-complex-demo.ad/raise-to-power (ad.clj:10).
loader constraint violation: when resolving method 'AD AD.raiseToPower()' the class loader clojure.lang.DynamicClassLoader @e7685af of the current class, dcevm_complex_demo/ad$raise_to_power, and the class loader 'app' for the method's defining class, AD, have different Class objects for the type AD used in the signature (dcevm_complex_demo.ad$raise_to_power is in unnamed module of loader clojure.lang.DynamicClassLoader @e7685af, parent loader clojure.lang.DynamicClassLoader @c8e92fa; AD is in unnamed module of loader 'app')
dcevm-complex-demo.ad> 
```

## License

Copyright © 2020 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
