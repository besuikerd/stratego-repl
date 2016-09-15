package com.besuikerd.mainrunner;

/**
 * Exception that is being thrown by the {@link MainRunner} whenever loading a
 * class or static function within that class failed.
 * @author Nicker
 *
 */
public class ClassFunctionException extends Exception{
    private static final long serialVersionUID = -7707030822313771011L;

    public ClassFunctionException(String message){
        super(message);
    }
}