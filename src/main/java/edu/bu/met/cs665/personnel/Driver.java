package edu.bu.met.cs665.personnel;

import edu.bu.met.cs665.deliverables.Deliverable;
import edu.bu.met.cs665.deliverables.TransRequest;

/**
 * The purpose of this class is to represent a driver who will receive deliverables
 * on a job.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class Driver implements SubscriberBase {

  private String email;
  private TransRequest transRequest;

  /**
   * Constructor.
   * @param email String
   * @param transRequest TransRequest to assign.
   */
  public Driver(String email, TransRequest transRequest) {
    this.email = email;
    this.transRequest = transRequest;
    this.transRequest.subscribe(this);
  }

  // Getters
  public String getEmail() {
    return email;
  }

  public TransRequest getTransRequest() {
    return transRequest;
  }

  // Setters
  public void setTransRequest(TransRequest transRequest) {
    this.transRequest = transRequest;
  }


  @Override
  public void updateSelf(Deliverable deliverable) {
    System.out.println("Latest Trans Request update received by " + this.getEmail()
        + ": Driver");
    setTransRequest((TransRequest) deliverable);
  }
}
