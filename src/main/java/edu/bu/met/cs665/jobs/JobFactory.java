package edu.bu.met.cs665.jobs;

import edu.bu.met.cs665.deliverables.JobSheet;
import edu.bu.met.cs665.deliverables.PaoRequest;
import edu.bu.met.cs665.deliverables.Program;
import edu.bu.met.cs665.deliverables.TransRequest;

/**
 * The purpose of this class is to be the Abstract Factory Class in the
 * Abstract Factory Pattern.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */

public interface JobFactory {

  JobSheet createJobSheet();

  TransRequest createTransRequest();

  PaoRequest createPaoRequest();

  Program createProgram();
}
