package edu.bu.met.cs665.deliverables;

import com.google.api.services.calendar.model.Event;
import edu.bu.met.cs665.personnel.SubscriberBase;
import java.util.ArrayList;

/**
 * The purpose of this class is to house advertising details associated with a
 * particular job. A paoRep needs to be subscribed to receive updated information
 * about these details.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class PaoRequest implements PublisherBase, Deliverable {

  private ArrayList<SubscriberBase> paoReps = new ArrayList<>();
  private Event event;

  // Getters
  public Event getEvent() {
    return event;
  }

  // Setters
  public void setEvent(Event event) {
    this.event = event;
    this.notifySubscribers();
  }

  @Override
  public void subscribe(SubscriberBase o) {
    paoReps.add(o);
  }

  @Override
  public void unsubscribe(SubscriberBase o) {
    paoReps.remove(o);
  }

  @Override
  public void notifySubscribers() {
    System.out.println("Public Affairs Request has been updated tracking the following...\n"
        + "Event Description: " + event.getDescription() + "\n"
        + "Status: " + event.getStatus() + "\n"
        + "Location: " + event.getLocation() + "\n"
        + "Start: " + event.getStart().getDateTime().toString() + "\n"
        + "End: " + event.getEnd().getDateTime().toString() + "\n"
        + "...notifying SubscriberBase:");
    for (SubscriberBase o : paoReps) {
      o.updateSelf(this);
    }
  }
}
