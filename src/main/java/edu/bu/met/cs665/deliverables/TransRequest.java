package edu.bu.met.cs665.deliverables;

import edu.bu.met.cs665.personnel.SubscriberBase;
import edu.bu.met.cs665.utilities.TimeValidator24HourClock;
import java.util.ArrayList;

/**
 * The purpose of this class is to house the details of the transportation
 * requirements for a job. Drivers need to subscribe to receive updates on this
 * information.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class TransRequest implements PublisherBase, Deliverable {

  private ArrayList<SubscriberBase> drivers = new ArrayList<>();
  private String transTime; // leaving time
  private String etr; // estimated return time
  private String location;


  /**
   * Trans time and Estimated Time of Return
   * needs to be manually set by a human who understands contextually
   * the day of the week and time of day the band needs to leave to make the
   * job. Eventually it might be nice to utilize the Google Maps "Best Time To
   * Leave" feature if possible.
   * @param transTime HH:MM.
   * @param etr HH:MM
   */
  public void setTransTimes(String transTime, String etr) {
    boolean validTransTime = validTime(transTime);
    boolean validEtr = validTime(etr);
    if (validTransTime && validEtr) {
      this.transTime = transTime;
      this.etr = etr;
    }
  }

  // Getters
  public String getTransTime() {
    return transTime;
  }

  public String getEtr() {
    return etr;
  }

  public String getLocation() {
    return location;
  }

  /**
   * This can be manually pulled from the job.getEvent().getLocation() if only
   * address is needed. Often more context is needed for drivers like
   * specific entrance etc.
   * @param location job.getRequest().getEvent().getLocation()
   */
  public void setLocation(String location) {
    this.location = location;
  }


  /**
   * This is the method that notifies the subscriber base since multiple attributes
   * can change and we don't want to notify every update, just cumulative
   * updates.
   */
  public void updateTransRequest() {
    this.notifySubscribers();
  }

  @Override
  public void subscribe(SubscriberBase o) {
    drivers.add(o);
  }

  @Override
  public void unsubscribe(SubscriberBase o) {
    drivers.remove(o);
  }

  @Override
  public void notifySubscribers() {
    System.out.println("Trans Request has been updated to track the following...\n"
        + "Trans time: " + transTime + "\n"
        + "Return time: " + etr + "\n"
        + "Location: " + location + "\n"
        + "...notifying SubscriberBase:");
    for (SubscriberBase o : drivers) {
      o.updateSelf(this);
    }
  }


  /**
   * The purpose of this method is to validate an HH:MM time string with
   * 24 hour clock validator.
   * @param time HH:MM desired format.
   * @return true if good 24-hour clock format, else false.
   */
  public boolean validTime(String time) {
    TimeValidator24HourClock validator = new TimeValidator24HourClock();
    boolean validTime = validator.validate(time);
    return validTime;
  }


}
