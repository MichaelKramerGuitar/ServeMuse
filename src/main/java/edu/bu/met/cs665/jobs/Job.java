package edu.bu.met.cs665.jobs;

import edu.bu.met.cs665.deliverables.JobSheet;
import edu.bu.met.cs665.deliverables.PaoRequest;
import edu.bu.met.cs665.deliverables.Program;
import edu.bu.met.cs665.deliverables.TransRequest;
import edu.bu.met.cs665.request.Request;

/**
 * The purpose of this class is to be the Abstract Product in the Abstract
 * Factory Pattern.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public abstract class Job implements Comparable {

  private Request request; // listener field for callback
  private JobSheet jobSheet;
  private TransRequest transRequest;
  private PaoRequest paoRequest;
  private Program program;

  /**
   * The purpose of this method is to distribute all of the deliverables associated
   * with a job.
   */
  public abstract void distribute();

  // Getters
  public JobSheet getJobSheet() {
    return jobSheet;
  }

  public PaoRequest getPaoRequest() {
    return paoRequest;
  }

  public Program getProgram() {
    return program;
  }

  public Request getRequest() {
    return request;
  }

  public TransRequest getTransRequest() {
    return transRequest;
  }

  /**
   * The purpose of this method is to set the request and from that the event
   * attribute.
   * @param request Request to be set.
   */
  public void setRequest(Request request) {
    this.request = request;
  }

  public void setJobSheet(JobSheet jobSheet) {
    this.jobSheet = jobSheet;
  }

  public void setPaoRequest(PaoRequest paoRequest) {
    this.paoRequest = paoRequest;
  }

  public void setProgram(Program program) {
    this.program = program;
  }

  public void setTransRequest(TransRequest transRequest) {
    this.transRequest = transRequest;
  }

  /**
   * In order to implement a priority queue of requests we need to implement
   * Comparable and override the compareTo method.
   */
  @Override
  public int compareTo(Object o) {
    Job job = (Job) o;
    if (job.getRequest().getEvent().getStart().getDateTime().getValue()
        < this.getRequest().getEvent().getStart().getDateTime().getValue()) {
      return -1;
    }
    return 0;
  }

}
