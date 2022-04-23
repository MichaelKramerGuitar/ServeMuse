package edu.bu.met.cs665.personnel;

import edu.bu.met.cs665.deliverables.Deliverable;

/**
 * The purpose of this class is to allow for classes with attributes
 * that need to be updated based on the state of other classes or attributes in
 * other classes to update themselves as needed.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public interface SubscriberBase {

  void updateSelf(Deliverable deliverable);

}
