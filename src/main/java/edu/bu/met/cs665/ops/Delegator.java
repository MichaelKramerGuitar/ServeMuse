package edu.bu.met.cs665.ops;

import edu.bu.met.cs665.jobs.Job;
import edu.bu.met.cs665.jobs.JobFactory;
import edu.bu.met.cs665.request.Request;

/**
 * The purpose of this class is be the abstraction for delegating jobs.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public interface Delegator {

  public Job delegate(JobFactory jobFactory, Request request);

}
