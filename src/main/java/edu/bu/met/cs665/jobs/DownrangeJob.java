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
public class DownrangeJob extends Job {

  private final DownrangeJobFactory jobFactory;
  // program is a String
  private static final String placeholderProgram =
      "song1, song2, song3, song4, song5";
  private final String[] downrangeRoster = new String[]{
    "vox1@gmail.com",
    "vox2@gmail.com",
    "vox3@gmail.com",
    "vox4@gmail.com",
    "guitar1@gmail.com",
    "guitar2@gmail.com",
    "keys1@gmail.com",
    "keys2@gmail.com",
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
  public DownrangeJob(DownrangeJobFactory jobFactory, Request request) {
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
      for (int i = 0; i < downrangeRoster.length; i++) { // iterate through blues roster
        new Musician(downrangeRoster[i],
            super.getJobSheet(),
            super.getProgram(),
            super.getRequest()
              .getEvent()
              .getSummary());
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
      super.getProgram().setProgram(placeholderProgram);
    }
  }


  public DownrangeJobFactory getJobFactory() {
    return jobFactory;
  }

  public String toString() {
    return "I am a Downrange Job";
  }

}
