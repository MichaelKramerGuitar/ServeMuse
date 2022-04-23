package edu.bu.met.cs665.jobs;

import edu.bu.met.cs665.personnel.Driver;
import edu.bu.met.cs665.personnel.Musician;
import edu.bu.met.cs665.personnel.PaoRep;
import edu.bu.met.cs665.personnel.Tech;
import edu.bu.met.cs665.request.Request;
import edu.bu.met.cs665.request.SubmittedStatus;

/**
 * The purpose of this class is to be a Concrete Product in the Abstract Factory
 * Pattern where Job is the Abstract Product and JobFactory is the Abstract Factory.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class BluesJob extends Job {

  private final BluesJobFactory jobFactory;
  // program is a String then
  private static final String placeholderProgram =
      "song1, song2, song3, song4, song5";
  private final String[] bluesRoster = new String[]{
      "alto1@gmail.com",
      "alto2@gmail.com",
      "tenor1@gmail.com",
      "tenor2@gmail.com",
      "bari@gmail.com",
      "trumpet1@gmail.com",
      "trumpet2@gmail.com",
      "trumpet3@gmail.com",
      "trumpet4@gmail.com",
      "trumpet5@gmail.com",
      "trombone1@gmial.com",
      "trombone2@gmail.com",
      "trombone3@gmail.com",
      "basstrombone@gmail.com",
      "piano@gmail.com",
      "guitar@gmail.com",
      "bass@gmail.com",
      "drums@gmail.com",
  };
  private final String[] techRoster = new String[]{
      "techgroupleader@gmail.com",
      "leadengineer@gmail.com"
  };
  private final String[] drivers = new String[]{
      "transportation@gmail.com",
      "transportationmanager@gmail.com"
  };
  private final String[] publicAffairs = new String[]{
      "paomanager@gmail.com",
      "paoofficial@gmail.com"
  };

  // Callback pattern, Job takes a request/callback object
  public BluesJob(BluesJobFactory jobFactory, Request request) {
    this.jobFactory = jobFactory;
    super.setRequest(request);
  }

  /**
   * The purpose of this method is to distribute all of the deliverables associated
   * with a job.
   */
  public void distribute() {
    // perform any operations here before call back method
    System.out.println("performing callback before synchronous task");
    // might not be applicable, could be some preparation activity

    if (super.getRequest().getStatus() instanceof SubmittedStatus) {
      System.out.println("Job is submitted and being distributed...");
      super.setJobSheet(jobFactory.createJobSheet());
      super.setPaoRequest(jobFactory.createPaoRequest());
      super.setProgram(jobFactory.createProgram());
      super.setTransRequest(jobFactory.createTransRequest());
      for (int i = 0; i < bluesRoster.length; i++) { // iterate through blues roster
        new Musician(bluesRoster[i], super.getJobSheet(), super.getProgram());
      }
      for (int j = 0; j < techRoster.length; j++) { // iterate through tech roster
        new Tech(techRoster[j], super.getJobSheet(), super.getProgram());
      }
      for (int k = 0; k < drivers.length; k++) {
        new Driver(drivers[k], super.getTransRequest());
      }
      for (int l = 0; l < publicAffairs.length; l++) {
        new PaoRep(publicAffairs[l], super.getPaoRequest());
      }
      // notify subscribers to jobsheet submitted
      super.getJobSheet().updateStatus(super.getRequest().getStatus());
      super.getRequest().setJob(this);
      super.getProgram().setProgram(placeholderProgram);
    }
  }


  public BluesJobFactory getJobFactory() {
    return jobFactory;
  }

  public String toString() {
    return "I am a Blues Job";
  }

}
