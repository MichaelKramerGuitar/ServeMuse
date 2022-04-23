package edu.bu.met.cs665.request;


/**
 * The purpose of this class is to notify when a Status Transition call is
 * not allowed.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class StatusTransitionException extends Exception {

  public StatusTransitionException(String message) {
    super(message);
  }
}
