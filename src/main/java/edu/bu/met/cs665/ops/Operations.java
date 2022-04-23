package edu.bu.met.cs665.ops;

import edu.bu.met.cs665.jobs.BluesJobFactory;
import edu.bu.met.cs665.jobs.ChorusJobFactory;
import edu.bu.met.cs665.jobs.DownrangeJobFactory;
import edu.bu.met.cs665.jobs.Job;
import edu.bu.met.cs665.jobs.JobFactory;
import edu.bu.met.cs665.producers.BluesProducer;
import edu.bu.met.cs665.producers.ChorusProducer;
import edu.bu.met.cs665.producers.DownrangeProducer;
import edu.bu.met.cs665.producers.ElementProducer;
import edu.bu.met.cs665.request.ApprovedStatus;
import edu.bu.met.cs665.request.DeniedStatus;
import edu.bu.met.cs665.request.Request;
import edu.bu.met.cs665.request.Status;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * The purpose of this class is to make delegation decisions and is a Singleton
 * Pattern.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class Operations {

  private static Operations operations;
  private Delegator delegator;
  // priority queue organized chronological priority
  private PriorityQueue<Request> requestsQueue = new PriorityQueue<>();
  private Job job;

  /**
   * The purpose of this method is to ensure no other class can instantiate
   * a new instance - private constructor.
   */
  private Operations() {}

  /**
   * The purpose of this method is to create the Operations instance if not
   * yet created, otherwise return the one that already exists.
   * @return This Instance of Operations
   */
  public static Operations getInstance() {
    if (operations == null) {
      operations = new Operations();
    }
    return operations;
  }

  // Getters
  public PriorityQueue<Request> getRequestsQueue() {
    return requestsQueue;
  }

  /**
   * The purpose of this method is to get the current Delegator Ops chose.
   * Do Not Call before calling delegate().
   * @return the most recent delegator OPS has delegated.
   */
  public Delegator getDelegator() {
    return delegator;
  }

  /**
   * The purpose of this method is to get the current job Ops is delegating.
   * Do Not Call getJob() before calling delegate().
   * @return the most recent job OPS has delegated.
   */
  public Job getJob() {
    return this.job;
  }



  /**
   * The purpose of this method is to set the appropriate job delegator.
   * @param delegator The appropriate job delegator.
   */
  public void setDelegator(Delegator delegator) {
    this.delegator = delegator;
  }

  /**
   * The purpose of this method is to submit a Request to OPS and this is
   * the soft entry point to the whole system after a Request is created.
   * @param request Request to be submitted to the PriorityQueue attribute
   */
  public void submitRequest(Request request) {
    System.out.println("Request submitted! " + request.getEvent().getDescription());
    requestsQueue.add(request);
    //calendar.add(request.getEvent());
  }

  /**
   * The purpose of this method is to delegate an ensemble to a job based on
   * a request and return the Element Producer associated with that ensemble.
   */
  public ElementProducer delegate() {
    JobFactory jobFactory;
    if (!requestsQueue.isEmpty()) {
      for (int i = 0; i < requestsQueue.size(); i++) {
        Request request = requestsQueue.peek();
        if (request.getEvent().getSummary().equals("BL")) {
          System.out.println("OPS is Delegating Blues Job");
          setDelegator(new BluesDelegator());
          jobFactory = new BluesJobFactory();
          BluesProducer bluesProducer = BluesProducer.getInstance();
          job = delegator.delegate(jobFactory, request);
          // fire the delegation tasks sequence.
          delegationTasks(bluesProducer, request, job);
          return bluesProducer;
        } else if (request.getEvent().getSummary().equals("DR")) {
          System.out.println("OPS is Delegating Downrange Job");
          setDelegator(new DownrangeDelegator());
          jobFactory = new DownrangeJobFactory();
          DownrangeProducer downrangeProducer = DownrangeProducer.getInstance();
          job = delegator.delegate(jobFactory, request);
          // fire delegation sequence
          delegationTasks(downrangeProducer, request, job);
          return downrangeProducer;
        } else if (request.getEvent().getSummary().equals("CHORUS")) {
          System.out.println("OPS is Delegating Chorus Job");
          setDelegator(new ChorusDelegator());
          jobFactory = new ChorusJobFactory();
          ChorusProducer chorusProducer = ChorusProducer.getInstance();
          job = delegator.delegate(jobFactory, request);
          // fire delegation tasks
          delegationTasks(chorusProducer, request, job);
          return chorusProducer;
        }
        // TODO more ensembles
      }
    }
    return null;
  }

  /**
   * The purpose of this method is to factor out tasks common to all job
   * delegations regardless of type to facilitate code reuse.
   * @param producer The element level producer.
   * @param request The submitted request.
   * @param job The newly created job.
   */
  public void delegationTasks(ElementProducer producer,
                                 Request request,
                                 Job job) {
    producer.addToCalendar(job); // add job to element Producer's calendar
    job.setRequest(request); // pair the job and the request
    request.setJob(job);
    System.out.println(job); // I am a blues job
    //job.distribute(); // polymorphism
  }


  /**
   * The purpose of this method is to update the requests' status.
   * @param request The request to be updated.
   * @param status The new request status.
   */
  public void updateRequestStatus(Request request, Status status) {
    Request targetRequest;
    boolean tracking = false;
    if (!requestsQueue.isEmpty()) {
      Iterator iterator = requestsQueue.iterator();
      while (iterator.hasNext()) {
        targetRequest = (Request) iterator.next();
        if (targetRequest.equals(request)) {
          System.out.println("Ops is tracking this request...updating status...");
          tracking = true;
          targetRequest.setStatus(status);
        }
      }
      if (!tracking) {
        System.out.println("Ops is not tracking this request...");
      }
    } else {
      System.out.println("Ops is not currently tracking any requests...");
    }
    this.updateCalendar();
  }

  /**
   * The purpose of this method is to remove jobs that have been approved or Denied
   * This could be refactored in practice to be remove after current date is greater
   * than request.event.getEnd() DateTime.
   */
  public void updateCalendar() {
    Request targetRequest;
    for (int i = 0; i < requestsQueue.size(); i++) {
      Iterator iterator = requestsQueue.iterator();
      while (iterator.hasNext()) {
        targetRequest = (Request) iterator.next();
        if (targetRequest.getStatus() instanceof ApprovedStatus
            || targetRequest.getStatus() instanceof DeniedStatus) {
          // remove processed requests from the queue so it's not evaluated again
          requestsQueue.remove(targetRequest);
        }
      }

    }
  }
}