package edu.bu.met.cs665.producers;

import edu.bu.met.cs665.jobs.Job;

/**
 * The purpose of this class is to update any deliverable associated with a job.
 * An element producer will have an ArrayList of Jobs associated with their
 * element (ensemble).
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public interface ElementProducer {

  /**
   * Replace in calendar job after updating deliverables.
   * @param job Job with updated deliverables to replace in calendar.
   */
  void updateJob(Job job);

  /**
   * Search a calendar for job by date string and remove job to update its
   * deliverables. Could be a start date or end date - up to the Producer
   * to implement. 
   * @param date String format yyyy-MM-dd
   * @return Job with matching start date or null.
   */
  Job findJobInCalendar(String date);

  void addToCalendar(Job job);

  void distribute(Job job);

}
