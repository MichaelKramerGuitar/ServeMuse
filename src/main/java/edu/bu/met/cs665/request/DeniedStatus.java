package edu.bu.met.cs665.request;

/**
 * The purpose of this class is to give a concrete Status Implementation for
 * Denied Jobs. This is state pattern concrete state. A Denied Status is currently
 * implemented as a locked/final/ending state for a request, all inherited methods throw a
 * State Transition Exception.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */

public class DeniedStatus implements Status {

  private Request request;

  public DeniedStatus(Request request) {
    this.request = request;
  }

  public Request getRequest() {
    return this.request;
  }

  /**
   * Cannot re-submit.
   * @throws StatusTransitionException already submitted.
   */
  @Override
  public void submit() throws StatusTransitionException {
    throw new StatusTransitionException("The request: "
        + request.getEvent().getDescription() + " has already been submitted!");
  }

  @Override
  public void approve() throws StatusTransitionException {
    throw new StatusTransitionException(
        "The request: "
        + request.getEvent().getDescription() + " has been denied, "
        + "Illegal State Transition: Denied -> Approved.");
  }

  @Override
  public void deny() throws StatusTransitionException {
    throw new StatusTransitionException(
        "The request: "
        + request.getEvent().getDescription() + " has been denied, "
        + "Illegal State Transition: Denied -> Denied.");
  }

  public String toString() {
    return "[DENIED]";
  }
}
