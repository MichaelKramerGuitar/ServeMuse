package edu.bu.met.cs665;

import com.google.api.services.calendar.model.Event;
import edu.bu.met.cs665.deliverables.PaoRequest;
import edu.bu.met.cs665.deliverables.Program;
import edu.bu.met.cs665.deliverables.TransRequest;
import edu.bu.met.cs665.jobs.Job;
import edu.bu.met.cs665.ops.Operations;
import edu.bu.met.cs665.producers.ElementProducer;
import edu.bu.met.cs665.producers.SeniorProducer;
import edu.bu.met.cs665.request.ApprovedStatus;
import edu.bu.met.cs665.request.Dd2536Request;
import edu.bu.met.cs665.request.DeniedStatus;
import edu.bu.met.cs665.request.InvalidEnsembleException;
import java.text.ParseException;

/**
 * The purpose of this class is to test functionality and run examples.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class Main {

  /**
   * A main method to run examples.
   *
   * @param args not used
   */
  public static void main(String[] args) {

    /*
    In real life the producer interfaces with a requesting venue, in army
    lingo called the Point of Contact or POC (implied, not shown), and collects
    information which is cleared. POC then fills out a request form which is
    sent to the producer who then sends it to Operations. Below is an approximation
    of this process.
     */
    SeniorProducer seniorProducer = SeniorProducer.getInstance(); // Step 1) get producer singleton
    Event event = new Event();
    try {
      event = seniorProducer.collectDetails(
        "BL",
        "2022-04-25",
        "14:00",
        "2022-04-25",
        "23:00",
        "Army Blues @ Blues Alley",
        "1073 Wisconsin Ave NW, Washington, DC 20007"
      );
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }

    Dd2536Request request = new Dd2536Request(event); // Step 2) request is instantiated
    Operations ops = Operations.getInstance(); // Step 3) get OPS singleton
    ops.submitRequest(request); // Step 4) submit the request through ops
    // Step 5) ops delegates the request to an ensemble, status: Submitted (default)
    ElementProducer producer = ops.delegate();
    // with the producer reference we can query jobs by start date
    Job job = producer.findJobInCalendar("Alley");
    producer.distribute(job); // producer distributes the submitted job for Situational Awareness
    ops.updateRequestStatus(request, new ApprovedStatus(request)); // ops updates request status
    // once we have the job facade we can update the Deliverables
    Program prog = job.getProgram(); // pull the blank program out of job to update
    String updatedProgram = "newjazzsong1, newjazzsong2, newjazzsong3";
    prog.setProgram(updatedProgram); // updating the program notifies subscribers
    TransRequest transRequest = job.getTransRequest();
    transRequest.setTransTimes("12:00", "23:00");
    transRequest.setLocation(job.getRequest().getEvent().getLocation());
    transRequest.updateTransRequest();
    PaoRequest paoRequest = job.getPaoRequest();
    paoRequest.setEvent(job.getRequest().getEvent());
    producer.updateJob(job);
    System.out.println();
    producer.findJobInCalendar("2022-04-26"); // Blues Producer is not tracking this job...

    System.out.println();
    System.out.println();

    Event event1 = new Event();
    try {
      event1 = seniorProducer.collectDetails(
        "DR",
        "2022-07-27",
        "16:00",
        "2022-07-27",
        "22:00",
        "Downrange @ Penn's Landing",
        "201 S. Christopher Columbus Blvd. Philadelphia, Pennsylvania, 19106, USA"
      );
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
    Dd2536Request request1 = new Dd2536Request(event1);
    ops.submitRequest(request1);
    ElementProducer producer1 = ops.delegate();

    Job job1 = producer1.findJobInCalendar("2022-07-27");
    producer1.distribute(job1); // producer distributes job with submitted request for SA
    ops.updateRequestStatus(request1, new ApprovedStatus(request1)); // ops updates request status
    // once we have the job facade we can update the Deliverables
    Program prog1 = job1.getProgram(); // pull the blank program out of job to update
    String updatedProgram1 = "newrocksong1, newrocksong2, newrocksong3";
    prog1.setProgram(updatedProgram1); // updating the program notifies subscribers
    TransRequest transRequest1 = job1.getTransRequest();
    transRequest1.setTransTimes("12:00", "23:00");
    transRequest1.setLocation(job1.getRequest().getEvent().getLocation());
    transRequest1.updateTransRequest();
    PaoRequest paoRequest1 = job1.getPaoRequest();
    paoRequest1.setEvent(job1.getRequest().getEvent());
    producer1.updateJob(job1);
    System.out.println();
    // Downrange Producer is not tracking this job...
    producer1.findJobInCalendar("2022-04-25");
    System.out.println();
    System.out.println();

    Event event2 = new Event();
    try {
      event2 = seniorProducer.collectDetails(
        "CHORUS",
        "2022-12-16",
        "13:00",
        "2022-12-18",
        "22:00",
        "Chorus @ Midwest Band Clinic Chicago",
        "2233 S Martin Luther King Dr, Chicago, IL 60616"
      );
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
    Dd2536Request request2 = new Dd2536Request(event2);
    ops.submitRequest(request2);
    ElementProducer producer2 = ops.delegate();

    Job job2 = producer2.findJobInCalendar("2022-12-18");
    producer2.distribute(job2); // producer2 distributes submitted job for SA
    ops.updateRequestStatus(request2, new DeniedStatus(request2)); // ops updates request status
    // once we have the job facade we can update the Deliverables
    Program prog2 = job2.getProgram(); // pull the blank program out of job to update
    String updatedProgram2 = "newchorussong1, newchorussong2, newchorussong3";
    prog2.setProgram(updatedProgram2); // updating the program notifies subscribers
    TransRequest transRequest2 = job2.getTransRequest();
    transRequest2.setTransTimes("12:00", "23:00");
    transRequest2.setLocation(job2.getRequest().getEvent().getLocation());
    transRequest2.updateTransRequest();
    PaoRequest paoRequest2 = job2.getPaoRequest();
    paoRequest2.setEvent(job2.getRequest().getEvent());
    producer2.updateJob(job2);
    System.out.println();
    // Downrange Producer is not tracking this job...
    producer2.findJobInCalendar("2022-04-25");

  }

}
