package edu.bu.met.cs665.personnel;

import edu.bu.met.cs665.deliverables.Deliverable;
import edu.bu.met.cs665.deliverables.JobSheet;
import edu.bu.met.cs665.deliverables.Program;

/**
 * The purpose of this class is to represent a tech personnel who will receive deliverables
 * on a job.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class Tech implements SubscriberBase {

  private String email;
  private JobSheet jobSheet;
  private Program program;

  /**
   * Constructor.
   * @param email Tech String email
   * @param jobSheet JobSheet Tech subscribes to.
   */
  public Tech(String email, JobSheet jobSheet) {
    this.email = email;
    this.jobSheet = jobSheet;
    this.jobSheet.subscribe(this);
  }

  /**
   * Constructor.
   * @param email Tech String email
   * @param jobSheet JobSheet Tech subscribes to.
   * @param program Program Tech subscribes to.
   */
  public Tech(String email, JobSheet jobSheet, Program program) {
    this.email = email;
    this.jobSheet = jobSheet;
    this.jobSheet.subscribe(this);
    this.program = program;
    this.program.subscribe(this);
  }

  /**
   * Update Tech with job sheet or program.
   * @param deliverable can be Job Sheet or Program.
   */
  @Override
  public void updateSelf(Deliverable deliverable) {
    if (deliverable instanceof JobSheet) {
      System.out.println("Latest JobSheet update received by " + this.getEmail()
          + ": Tech personnel");
      setJobSheet((JobSheet) deliverable);
    } else if (deliverable instanceof Program) {
      System.out.println("Latest Program update received by " + this.getEmail()
          + ": Tech personnel");
      setProgram((Program) deliverable);
    } else {
      System.out.println("Update not received because of TypeCheck");
    }
  }

  public String getEmail() {
    return email;
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

}
