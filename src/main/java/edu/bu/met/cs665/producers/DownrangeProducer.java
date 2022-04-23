package edu.bu.met.cs665.producers;

import edu.bu.met.cs665.jobs.Job;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * The purpose of this class is to keep track of and update as needed all of the
 * jobs delegated to this element/ensemble by Operations according to
 * requests.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class DownrangeProducer implements ElementProducer {

  private static DownrangeProducer downrangeProducer; // Singleton
  // priority queue organized chronological priority
  private PriorityQueue<Job> calendar = new PriorityQueue<>();

  /**
   * The purpose of this method is to ensure no other can can instantiate a
   * new instance - private constructor.
   */
  private DownrangeProducer() {}

  /**
   * The purpose of this method is to create the Producer instance if not
   * yet created, otherwise return the one that already exists.
   * @return This Instance of Producer
   */
  public static DownrangeProducer getInstance() {
    if (downrangeProducer == null) {
      downrangeProducer = new DownrangeProducer();
    }
    return downrangeProducer;
  }

  /**
   * The purpose of this method is to add a job to the calendar array list
   * of jobs attribute.
   * @param job object to add to calendar ArrayList.
   */
  public void addToCalendar(Job job) {
    calendar.add(job);
  }

  /**
   * The purpose of this method is to pass the update.
   * @param job Job which was found in calendar, removed, updated and now to
   *            be placed back in calendar.
   */
  @Override
  public void updateJob(Job job) {
    calendar.add(job); // add job back to calendar after updating.
  }


  /**
   * The purpose of this method is to distribute Deliverables associated
   * with a job.
   * @param job Job to distribute
   */
  @Override
  public void distribute(Job job) {
    job.distribute();
  }

  /**
   * The purpose of this method is to query this Element producer for a job
   * facade object to update its deliverable attributes by startDate String.
   * @param startDate String format yyyy-MM-dd
   * @return Job object matching start date or null if no match.
   */
  @Override
  public Job findJobInCalendar(String startDate) {
    Job checkJob;
    boolean tracking = false;
    if (!calendar.isEmpty()) {
      Iterator iterator = calendar.iterator();
      while (iterator.hasNext()) {
        checkJob = (Job) iterator.next();
        if (checkJob
            .getRequest()
            .getEvent()
            .getStart()
            .getDateTime()
            .toString()
            .startsWith(startDate)) {
          tracking = true;
          System.out.println(
              "Downrange Producer is tracking this job on " + startDate);
          calendar.remove(checkJob); // remove job from calendar to be updated
          return checkJob;
        }
      }
      if (!tracking) {
        System.out.println("Downrange Producer is not tracking a job on " + startDate);
      }
    } else {
      System.out.println("Downrange producer is not tracking any jobs currently...");
    }
    return null;
  }
}
