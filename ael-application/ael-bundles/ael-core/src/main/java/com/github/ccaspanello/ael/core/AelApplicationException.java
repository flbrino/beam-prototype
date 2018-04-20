package com.github.ccaspanello.ael.core;

/**
 * Created by ccaspanello on 4/20/18.
 */
public class AelApplicationException extends RuntimeException {

  public AelApplicationException( String message ) {
    super( message );
  }

  public AelApplicationException( String message, Throwable throwable ) {
    super( message, throwable );
  }
}
