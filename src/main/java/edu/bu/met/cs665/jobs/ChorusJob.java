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
public class ChorusJob extends Job {

  private final ChorusJobFactory jobFactory;
  // program is a String then
  private static final String placeholderProgram =
      "chorussong1, chorussong2, chorussong3, chorussong4, chorussong5";
  private final String[] chorusRoster = new String[]{
    "soprano.vocals1@gmail.com",
    "soprano.vocals2@gmail.com",
    "soprano.vocals3@gmail.com",
    "soprano.vocals4@gmail.com",
    "soprano.vocals5@gmail.com",
    "alto.vocals1@gmail.com",
    "alto.vocals2@gmail.com",
    "alto.vocals3@gmail.com",
    "alto.vocals4@gmail.com",
    "alto.vocals5@gmail.com",
    "tenor.vocals1@gmial.com",
    "tenor.vocals2@gmail.com",
    "tenor.vocals3@gmail.com",
    "tenor.vocals4@gmail.com",
    "bass.vocals1@gmail.com",
    "bass.vocals2@gmail.com",
    "bass.vocals3@gmail.com",
    "bass.vocals4@gmail.com",
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
  public ChorusJob(ChorusJobFactory jobFactory, Request request) {
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
      for (int i = 0; i < chorusRoster.length; i++) { // iterate through chorus roster
        new Musician(
            chorusRoster[i],
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


  public ChorusJobFactory getJobFactory() {
    return jobFactory;
  }

  public String toString() {
    return "I am a Chorus Job";
  }

}
