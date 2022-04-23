package edu.bu.met.cs665.personnel;

import edu.bu.met.cs665.deliverables.Deliverable;
import edu.bu.met.cs665.deliverables.PaoRequest;

/**
 * The purpose of this class is to represent a public affairs rep who will receive deliverables
 * on a job.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class PaoRep implements SubscriberBase {

  private String email;
  private PaoRequest paoRequest;

  /**
   * Constructor.
   * @param email String
   * @param paoRequest PaoRequest to assign.
   */
  public PaoRep(String email, PaoRequest paoRequest) {
    this.email = email;
    this.paoRequest = paoRequest;
    this.paoRequest.subscribe(this);
  }

  public String getEmail() {
    return email;
  }

  public PaoRequest getPaoRequest() {
    return paoRequest;
  }

  public void setPaoRequest(PaoRequest paoRequest) {
    this.paoRequest = paoRequest;
  }

  @Override
  public void updateSelf(Deliverable deliverable) {
    setPaoRequest((PaoRequest) deliverable);
    System.out.println("Latest Public Affairs Request update received by " + this.getEmail()
        + ": Public Affairs Rep");


  }
}
