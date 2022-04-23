package edu.bu.met.cs665.deliverables;

import edu.bu.met.cs665.personnel.SubscriberBase;
import edu.bu.met.cs665.request.Status;
import java.util.ArrayList;

/**
 * The purpose of this class is to provide details to Musicians and Tech assigned
 * to the job and who make up the subscriber base as they need to be notified
 * when a job comes in and when its status is confirmed.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class JobSheet implements PublisherBase, Deliverable { // extends BluesJob

  // keep list of subscribers to notify of state changes
  private ArrayList<SubscriberBase> personnel = new ArrayList<SubscriberBase>();
  private String title; // grab details from Job.event.getDescription();
  private String details; // grab details from Job.event.getLocation();
  private Status status;

  public JobSheet() {}

  /**
   * The purpose of this method is to update the Status of the job for which
   * this job sheet was created as a deliverable and notify the subscribers
   * of this update.
   * @param status new Status.
   */
  public void updateStatus(Status status) {
    this.status = status;
    this.notifySubscribers();
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public Status getStatus() {
    return status;
  }

  @Override
  public void subscribe(SubscriberBase o) {
    personnel.add(o);
  }

  @Override
  public void unsubscribe(SubscriberBase o) {
    personnel.remove(o);
  }

  @Override
  public void notifySubscribers() {
    System.out.println("JobSheet has been updated "
        + "...notifying SubscriberBase request status: " + getStatus().toString());
    for (SubscriberBase o : personnel) {
      o.updateSelf(this);
    }
  }

  public String toString() {
    return "Job Title: " + title + " Location: " + details;
  }
}
