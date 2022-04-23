package edu.bu.met.cs665.ops;

import edu.bu.met.cs665.jobs.ChorusJob;
import edu.bu.met.cs665.jobs.ChorusJobFactory;
import edu.bu.met.cs665.jobs.JobFactory;
import edu.bu.met.cs665.request.Request;

/**
 * The purpose of this class is to be a concrete Delegator.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class ChorusDelegator implements Delegator {

  @Override
  public ChorusJob delegate(JobFactory jobFactory, Request request) {
    return new ChorusJob((ChorusJobFactory) jobFactory, request);
  }
}
