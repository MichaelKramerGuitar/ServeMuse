package edu.bu.met.cs665.request;

import com.google.api.services.calendar.model.Event;
import edu.bu.met.cs665.jobs.Job;

/**
 * The purpose of this class is to provide an abstraction for a Request
 * of the band.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public interface Request {

  public Status getStatus();

  public Event getEvent(); // Event passed in to construct a Request

  public Job getJob();

  public void setStatus(Status status);

  public void setJob(Job job);
}
