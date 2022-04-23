package edu.bu.met.cs665.deliverables;

import edu.bu.met.cs665.personnel.SubscriberBase;

/**
 * The purpose of this class is to allow for deliverables and requests to inherit
 * these methods and notify subscribed classes of state changes.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public interface PublisherBase {

  void subscribe(SubscriberBase o);

  void unsubscribe(SubscriberBase o);

  void notifySubscribers();

}
