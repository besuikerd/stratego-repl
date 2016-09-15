package com.besuikerd.mainrunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that can load class files dynamically and execute static functions on
 * the fly. This class uses java reflection to achieve this and should only be
 * used to simplify the debugging of the YAPL compiler.
 * @author Nicker
 *
 */
public class MainRunner {
    /**
     * singleton instance of the {@link MainRunner}
     */
    private static MainRunner instance;

    /**
     * classloader for the com.besuikerd.mainrunner.MainRunner
     */
    private URLClassLoader loader;

    /**
     * Classes already loaded by the classloader
     */
    private Map<String, Class<?>> loadedClasses;

    /**
     * private constructor to prevent instantiation
     */
    private MainRunner(){
        loadedClasses = new HashMap<String, Class<?>>();
        this.loader = new URLClassLoader(new URL[0]);
    }

    /**
     * retrieves the singleton instance of com.besuikerd.mainrunner.MainRunner
     * @return singleton instance of com.besuikerd.mainrunner.MainRunner
     */
    public static MainRunner getInstance(){
        if(instance == null){
            instance = new MainRunner();
        }
        return instance;
    }

    /**
     * appends a path to the classloader from where classes can be loaded
     * @param path path to add to the classloader
     * @throws MalformedURLException whenever an invalid URI as path is given
     */
    public void addToClassPath(String path) throws MalformedURLException{
        if(!path.matches("^file://")) path = "file://" + path.replaceAll("\\\\", "/");
        if(!path.endsWith("/")) path = path + "/";
        this.loader = URLClassLoader.newInstance(new URL[]{new URL(path)}, loader);
    }

    /**
     * Tries to execute the static main function of the given class, without arguments
     * @param cls name of the class to execute the main function from
     * @throws ExecutionException Whenever the executed function throws an exception
     * @throws ClassFunctionException Whenever the function could not be executed
     */
    public void tryMain(String cls) throws ExecutionException, ClassFunctionException{
        tryMain(cls, new String[0]);
    }

    /**
     * Tries to execute the static main function of the given class, with the given arguments
     * @param cls name of the class to execute the main function from
     * @param args arguments to execute the function with
     * @throws ExecutionException Whenever the executed function throws an exception
     * @throws ClassFunctionException Whenever the function could not be executed
     */
    public void tryMain(String cls, String... args) throws ExecutionException, ClassFunctionException{
        tryStatic(cls, "main", (Object) args);
    }

    /**
     * Tries to execute a static method of the given class, with the given arguments.
     * @param className name of the class to execute the main function from
     * @param method of the method to execute
     * @param args arguments for that function
     * @throws ExecutionException Whenever the executed function throws an exception
     * @throws ClassFunctionException Whenever the function could not be executed
     */
    public void tryStatic(String className, String method, Object... args) throws ExecutionException, ClassFunctionException{
        Class<?> cls = loadedClasses.get(className);
        if(cls == null){
            try {
                cls = loader.loadClass(className);
            } catch (ClassNotFoundException e) {

                throw new RuntimeException("class not found: " + e.getMessage());
            }
            loadedClasses.put(className, cls);
        }
        if(cls != null){
            Class<?>[] classes = Arrays.stream(args).map((arg) -> arg.getClass()).toArray(Class<?>[]::new);
            Method m;
            try {
                m = cls.getMethod(method, classes);
            } catch (NoSuchMethodException | SecurityException e) {
                throw new ClassFunctionException(e.getMessage());
            }
            if(m != null){
                try {
                    m.invoke(cls, args);
                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    throw new ExecutionException(e.getCause());
                }
            }
        }
    }
}