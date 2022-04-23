package edu.bu.met.cs665.personnel;

import edu.bu.met.cs665.deliverables.Deliverable;
import edu.bu.met.cs665.deliverables.JobSheet;
import edu.bu.met.cs665.deliverables.Program;

/**
 * The purpose of this class is to represent a musician who will receive deliverables
 * on a job and who belongs to an ensemble.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class Musician implements SubscriberBase {

  private String email;
  private JobSheet jobSheet;
  private Program program;
  private String ensemble = "BL"; // i.e. "BL" for Blues, "DR" for Downrange

  /**
   * Constructor.
   * @param email Musicians String email
   * @param jobSheet JobSheet Musician subscribes to.
   */
  public Musician(String email, JobSheet jobSheet) {
    this.email = email;
    this.jobSheet = jobSheet;
    this.jobSheet.subscribe(this);
  }

  /**
   * Constructor.
   * @param email Musicians String email
   * @param jobSheet JobSheet Musician subscribes to.
   * @param program Program Musician subscribes to.
   */
  public Musician(String email, JobSheet jobSheet, Program program) {
    this.email = email;
    this.jobSheet = jobSheet;
    this.jobSheet.subscribe(this);
    this.program = program;
    this.program.subscribe(this);
  }

  /**
   * Constructor.
   * @param email Musicians String email
   * @param jobSheet JobSheet Musician subscribes to.
   * @param program Program Musician subscribes to.
   * @param ensemble String Ensemble musician belongs to (useful with subs).
   */
  public Musician(String email, JobSheet jobSheet, Program program, String ensemble) {
    this.email = email;
    this.jobSheet = jobSheet;
    this.jobSheet.subscribe(this);
    this.program = program;
    this.program.subscribe(this);
    this.ensemble = ensemble;
  }

  /**
   * Update Musician with job sheet or program.
   * @param deliverable can be Job Sheet or Program.
   */
  @Override
  public void updateSelf(Deliverable deliverable) {
    if (deliverable instanceof JobSheet) {
      System.out.println("Latest JobSheet update received by " + this.getEmail()
          + ": ensemble: " + this.getEnsemble());
      setJobSheet((JobSheet) deliverable);
    } else if (deliverable instanceof Program) {
      System.out.println("Latest Program update received by " + this.getEmail()
          + ": ensemble: " + this.getEnsemble());
      setProgram((Program) deliverable);
    } else {
      System.out.println("Update not received because of TypeCheck");
    }
  }

  public String getEmail() {
    return email;
  }

  public String getEnsemble() {
    return ensemble;
  }

  public JobSheet getJobSheet() {
    return jobSheet;
  }

  public Program getProgram() {
    return program;
  }

  /**
   * The purpose of this method is to update the Program attribute.
   * @param program updated
   */
  public void setProgram(Program program) {
    this.program = program;
  }

  public void setJobSheet(JobSheet jobSheet) {
    this.jobSheet = jobSheet;
  }

  public void setEnsemble(String ensemble) {
    this.ensemble = ensemble;
  }

}
