package edu.bu.met.cs665.request;


/**
 * The purpose of this class is to give a concrete Status Implementation for
 * Approved Jobs. This is state pattern concrete state.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */

public class SubmittedStatus implements Status {

  private Request request;

  public SubmittedStatus(Request request) {
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
    if (this.request != null) {
      throw new StatusTransitionException("This request was already submitted");
    }
  }

  @Override
  public void approve() {
    System.out.println(
        "The request: "
        + request.getEvent().getDescription()
        + " is pending...[FROM APPROVE CALL]");
    request.setStatus(new ApprovedStatus(request));
  }

  @Override
  public void deny() {
    System.out.println(
        "The request: "
        + request.getEvent().getDescription()
        + " is pending...[FROM DENY CALL]");
    request.setStatus(new DeniedStatus(request));
  }

  public String toString() {
    return "[SUBMITTED]";
  }
}
