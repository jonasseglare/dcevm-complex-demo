# dcevm-complex-demo

Investigate HotswapAgent loading issues.

## Detailed instructions for reproducing it

### Setup (Clojure and JVM)

  1. Install [Leiningen](https://github.com/technomancy/leiningen) by following [these instructions](https://github.com/technomancy/leiningen#installation).
  2. Edit the line starting with `:java-cmd` in [project.clj](project.clj) so that it points at your DCEVM `java` executable. Or, if DCEVM is already the default JVM on your system, you can comment out this line.

### Reproducing the problem

Open a terminal from the root folder of this project and type `lein clean` to clean up the project.

The launch a repl with `lein repl`. In the repl, type `(raise-to-power (variable 3.0))`. You will see something like:
```
jonas@jonas-ThinkPad-W530:~/prog/clojure/dcevm-complex-demo$ lein repl
HOTSWAP AGENT: 11:16:16.436 INFO (org.hotswap.agent.HotswapAgent) - Loading Hotswap agent {1.4.0} - unlimited runtime class redefinition.
HOTSWAP AGENT: 11:16:16.596 INFO (org.hotswap.agent.config.PluginRegistry) - Plugin 'org.hotswap.agent.plugin.hotswapper.HotswapperPlugin' initialized in ClassLoader 'jdk.internal.loader.ClassLoaders$AppClassLoader@277050dc'.
HOTSWAP AGENT: 11:16:16.657 INFO (org.hotswap.agent.config.PluginRegistry) - Discovered plugins: [JdkPlugin, Hotswapper, WatchResources, ClassInitPlugin, AnonymousClassPatch, Hibernate, Hibernate3JPA, Hibernate3, Spring, Jersey1, Jersey2, Jetty, Tomcat, ZK, Logback, Log4j2, MyFaces, Mojarra, Omnifaces, ELResolver, WildFlyELResolver, OsgiEquinox, Owb, Proxy, WebObjects, Weld, JBossModules, ResteasyRegistry, Deltaspike, GlassFish, Vaadin, Wicket, CxfJAXRS, FreeMarker, Undertow, MyBatis]
Starting HotswapAgent '/home/jonas/local/dcevm-11.0.7+1/lib/hotswap/hotswap-agent.jar'
nREPL server started on port 35115 on host 127.0.0.1 - nrepl://127.0.0.1:35115
REPL-y 0.4.4, nREPL 0.6.0
Clojure 1.10.1
Dynamic Code Evolution 64-Bit Server VM 11.0.7+1-202005222046
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
 Results: Stored in vars *1, *2, *3, an exception in *e

dcevm-complex-demo.ad=> (raise-to-power (variable 3.0))
#object[AD 0x27d20f5d "AD(value=81.0, deriv=108.0)"]
```

Open another terminal window and go to the root folder of this project. Edit the line `public static int EXPONENT = 4;` in the file `src/java/AD.java` so that `EXPONENT` has a different value, e.g. 3. Then exit the editor and type `lein javac` in the terminal:
```
jonas@jonas-ThinkPad-W530:~/prog/clojure/dcevm-complex-demo$ emacs src/java/AD.java &
[1] 16646
jonas@jonas-ThinkPad-W530:~/prog/clojure/dcevm-complex-demo$ lein javac
HOTSWAP AGENT: 11:16:46.851 INFO (org.hotswap.agent.HotswapAgent) - Loading Hotswap agent {1.4.0} - unlimited runtime class redefinition.
HOTSWAP AGENT: 11:16:47.015 INFO (org.hotswap.agent.config.PluginRegistry) - Plugin 'org.hotswap.agent.plugin.hotswapper.HotswapperPlugin' initialized in ClassLoader 'jdk.internal.loader.ClassLoaders$AppClassLoader@277050dc'.
HOTSWAP AGENT: 11:16:47.081 INFO (org.hotswap.agent.config.PluginRegistry) - Discovered plugins: [JdkPlugin, Hotswapper, WatchResources, ClassInitPlugin, AnonymousClassPatch, Hibernate, Hibernate3JPA, Hibernate3, Spring, Jersey1, Jersey2, Jetty, Tomcat, ZK, Logback, Log4j2, MyFaces, Mojarra, Omnifaces, ELResolver, WildFlyELResolver, OsgiEquinox, Owb, Proxy, WebObjects, Weld, JBossModules, ResteasyRegistry, Deltaspike, GlassFish, Vaadin, Wicket, CxfJAXRS, FreeMarker, Undertow, MyBatis]
Starting HotswapAgent '/home/jonas/local/dcevm-11.0.7+1/lib/hotswap/hotswap-agent.jar'
Compiling 1 source files to /home/jonas/prog/clojure/dcevm-complex-demo/target/classes
```
Now go back to the terminal where you started. You will see that `DCEVM` printed out a message that the class was reloaded. Type in the same expression as before, that is `(raise-to-power (variable 3.0))`. We get an error:
```
dcevm-complex-demo.ad=> HOTSWAP AGENT: 11:16:48.833 RELOAD (org.hotswap.agent.config.PluginManager) - Reloading classes [AD] (autoHotswap)


dcevm-complex-demo.ad=> (raise-to-power (variable 3.0))
Execution error (LinkageError) at dcevm-complex-demo.ad/raise-to-power (ad.clj:10).
loader constraint violation: when resolving method 'AD AD.raiseToPower()' the class loader clojure.lang.DynamicClassLoader @7c891ba7 of the current class, dcevm_complex_demo/ad$raise_to_power, and the class loader 'app' for the method's defining class, AD, have different Class objects for the type AD used in the signature (dcevm_complex_demo.ad$raise_to_power is in unnamed module of loader clojure.lang.DynamicClassLoader @7c891ba7, parent loader clojure.lang.DynamicClassLoader @5b84f14; AD is in unnamed module of loader 'app')
```

## License

Copyright © 2020 Jonas Östlund

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
