package edu.bu.met.cs665.request;

/**
 * The purpose of this class is to provide an abstraction for a request state
 * to inherit these methods. This is State Pattern interface. Status starts
 * by default at Submitted and from there can go either to Approved or Denied.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public interface Status {

  void submit() throws StatusTransitionException;

  void approve() throws StatusTransitionException;

  void deny() throws StatusTransitionException;

}
