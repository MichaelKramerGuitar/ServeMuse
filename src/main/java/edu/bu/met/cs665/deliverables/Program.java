package edu.bu.met.cs665.deliverables;

import edu.bu.met.cs665.personnel.SubscriberBase;
import java.util.ArrayList;

/**
 * The purpose of this class is to house the details of the musical program such
 * that musicians are informed and can prepare if needed. Musicians need to
 * subscribe to Program.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class Program implements PublisherBase, Deliverable {

  // keep list of subscribers to notify of state changes
  private ArrayList<SubscriberBase> personnel = new ArrayList<SubscriberBase>();

  //private String title;
  private ArrayList<String> songs = new ArrayList<>();

  public Program() {}

  /**
   * The purpose of this method is to update the songs ArrayList of this program.
   * @param s is a COMMA SEPARATED LIST of songs
   */
  public void setProgram(String s) {
    String[] sgs = s.split(",");
    for (int i = 0; i < sgs.length; i++) {
      this.songs.add(sgs[i]);
    }
    this.notifySubscribers();
  }

  // TODO more updateProgram methods, addSong, reWriteProgram etc.

  @Override
  public void subscribe(SubscriberBase o) {
    personnel.add(o);
  }

  @Override
  public void unsubscribe(SubscriberBase o) {
    personnel.add(o);
  }

  @Override
  public void notifySubscribers() {
    System.out.println("Program has been updated "
        + "...notifying SubscriberBase:");
    for (SubscriberBase o : personnel) {
      o.updateSelf(this);
    }
  }
}
