package edu.bu.met.cs665.ops;

import edu.bu.met.cs665.jobs.BluesJob;
import edu.bu.met.cs665.jobs.BluesJobFactory;
import edu.bu.met.cs665.jobs.JobFactory;
import edu.bu.met.cs665.request.Request;

/**
 * The purpose of this class is to be a concrete Delegator.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class BluesDelegator implements Delegator {

  @Override
  public BluesJob delegate(JobFactory jobFactory, Request request) {
    return new BluesJob((BluesJobFactory) jobFactory, request);
  }
}
