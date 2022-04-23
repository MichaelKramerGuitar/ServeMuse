package edu.bu.met.cs665.request;

/**
 * The purpose of this class is to give a concrete Status Implementation for
 * Approved Jobs. This is state pattern concrete state.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class ApprovedStatus implements Status {

  private Request request;

  public ApprovedStatus(Request request) {
    this.request = request;
  }

  public Request getRequest() {
    return this.request;
  }

  /**
   * Cannot resubmit.
   * @throws StatusTransitionException already submitted.
   */
  @Override
  public void submit() throws StatusTransitionException {
    throw new StatusTransitionException(
        "The request: "
        + request.getEvent().getDescription() + " has been submitted!");
  }

  /**
   * Cannot re-approve.
   * @throws StatusTransitionException already approved.
   */
  @Override
  public void approve() throws StatusTransitionException {
    throw new StatusTransitionException(
        "The request: "
        + request.getEvent().getDescription() + " has already been approved!");
  }

  @Override
  public void deny() {
    request.setStatus(new DeniedStatus(request));
  }

  public String toString() {
    return "[APPROVED]";
  }

}
