package edu.bu.met.cs665.jobs;

import edu.bu.met.cs665.deliverables.JobSheet;
import edu.bu.met.cs665.deliverables.PaoRequest;
import edu.bu.met.cs665.deliverables.Program;
import edu.bu.met.cs665.deliverables.TransRequest;

/**
 * The purpose of this class is to be a Concrete Factory in the Abstract Factory
 * Pattern.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class DownrangeJobFactory implements JobFactory {

  @Override
  public JobSheet createJobSheet() {
    return new JobSheet();
  }

  @Override
  public PaoRequest createPaoRequest() {
    return new PaoRequest();
  }

  @Override
  public Program createProgram() {
    return new Program();
  }

  @Override
  public TransRequest createTransRequest() {
    return new TransRequest();
  }

}