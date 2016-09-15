package com.besuikerd.mainrunner;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Exception that is thrown while executing a static function by the 
 * {@link MainRunner}. This function wraps the exception that was thrown by the
 * executing static function
 * @author Nicker
 *
 */
public class ExecutionException extends Exception{

    private static final long serialVersionUID = -4744639846604048371L;

    /**
     * Underlying exception that was thrown
     */
    private Throwable throwable;

    public ExecutionException(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public synchronized Throwable getCause() {
        return throwable;
    }

    @Override
    public String getLocalizedMessage() {
        return throwable.getLocalizedMessage();
    }

    @Override
    public String getMessage() {
        return throwable.getMessage();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return throwable.getStackTrace();
    }

    @Override
    public void printStackTrace() {
        throwable.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        throwable.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        throwable.printStackTrace();
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", getClass().getSimpleName(), throwable);
    }
}