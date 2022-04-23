package edu.bu.met.cs665.request;

import com.google.api.services.calendar.model.Event;
import edu.bu.met.cs665.jobs.Job;

/**
 * The purpose of this class is to be a concrete implementation of a request
 * to request an ensemble in the band for an event.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class Dd2536Request implements Request, Comparable {

  private Status status;
  private Event event;
  private Job job;

  /**
   * Constructor.
   * @param event Event associated with this Request.
   */
  public Dd2536Request(Event event) {
    this.event = event;
    this.status = new SubmittedStatus(this); // default status is Submitted
    this.event.setStatus("submitted"); // distribute down to event as well, maybe refactor this.
  }

  @Override
  public Status getStatus() {
    return this.status;
  }

  @Override
  public Event getEvent() {
    return this.event;
  }

  @Override
  public Job getJob() {
    return this.job;
  }

  @Override
  public void setStatus(Status status) {
    this.status = status;
    if (status instanceof ApprovedStatus) {
      System.out.println("Request is updating status to Approved...");
      this.event.setStatus("approved");
      //this.job.getJobSheet().updateStatus(this.getStatus()); // callback
    } else if (status instanceof DeniedStatus) {
      System.out.println("Request is updating status to Denied...");
      this.event.setStatus("denied");
      //this.job.getJobSheet().updateStatus(this.getStatus()); // callback
    } else { // Submitted
      System.out.println("Request is updating associated job sheet...");
      //this.job.getJobSheet().updateStatus(this.getStatus()); // callback
    }
    if (this.job.getJobSheet() != null) {
      this.job.getJobSheet().updateStatus(this.getStatus()); // callback
    }
  }

  public void setJob(Job job) {
    this.job = job;
  }

  /**
   * In order to implement a priority queue of requests we need to implement
   * Comparable and override the compareTo method.
   */
  @Override
  public int compareTo(Object o) {
    Request request = (Request) o;
    if (request.getEvent().getStart().getDateTime().getValue()
        < this.getEvent().getStart().getDateTime().getValue()) {
      return -1;
    }
    return 0;
  }

  /**
   * The purpose of this method is to define an equals in addition to compareTo
   * above.
   */
  @Override
  public boolean equals(Object o) {
    if (o != null) {
      if (getClass() != o.getClass()) {
        return false;
      }
      Request request = (Request) o;
      // this works because of composition relationship event -<> request
      return this.compareTo(request) == 0;
    }
    return false;
  }

  /**
   * This override basically means we cannot insert DD2536 into a HashMap/Table.
   * @return
   */
  @Override
  public int hashCode() {
    assert false : "hashCode not designed";
    return 42; // any arbitrary constant will do
  }
}
