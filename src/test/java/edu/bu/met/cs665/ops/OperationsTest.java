package edu.bu.met.cs665.ops;

import com.google.api.services.calendar.model.Event;
import edu.bu.met.cs665.jobs.BluesJob;
import edu.bu.met.cs665.jobs.Job;
import edu.bu.met.cs665.producers.BluesProducer;
import edu.bu.met.cs665.producers.ElementProducer;
import edu.bu.met.cs665.producers.SeniorProducer;
import edu.bu.met.cs665.request.*;
import org.junit.*;

import java.text.ParseException;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * The purpose of this class is to test Operations delegation functionality
 * getting Requests to the appropriate Element Producers and updating the
 * status of these requests.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class OperationsTest {

  private static Operations ops;
  private static SeniorProducer seniorProducer;

  public OperationsTest() {}

  @BeforeClass
  public static void setUp() {
    ops = Operations.getInstance();
    seniorProducer = SeniorProducer.getInstance();
  }

  @AfterClass
  public static void tearDown() {
    ops.getRequestsQueue().clear();
  }

  @Test
  public void TestSubmitRequest() {
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
    // Step 2) request is instantiated
    Request request = new Dd2536Request(event);
    //Operations ops = Operations.getInstance();
    PriorityQueue<Request> requests = ops.getRequestsQueue();
    Assert.assertTrue(requests.isEmpty()); // make sure nothing is in the queue yet
    ops.submitRequest(request);
    requests = ops.getRequestsQueue();
    Assert.assertTrue(requests.contains(request)); // make sure the request made it in the queue
    requests.clear(); // reset the requestsQueue for more tests.
  }

  @Test
  public void TestUpdateStatusRemovedFromQueueAfterCal() {
    // Step 1) get senior producer singleton
    //SeniorProducer seniorProducer = SeniorProducer.getInstance();
    Event event = new Event();
    try {
      event = seniorProducer.collectDetails(
        "BL",
        "2022-04-27",
        "14:00",
        "2022-04-27",
        "23:00",
        "Army Blues @ Blues Alley",
        "1073 Wisconsin Ave NW, Washington, DC 20007"
      );
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
    // Step 2) request is instantiated
    Request request = new Dd2536Request(event);
    //Operations ops = Operations.getInstance();
    PriorityQueue<Request> requests = ops.getRequestsQueue();
    ops.getRequestsQueue().clear();
    ops.submitRequest(request);
    Assert.assertTrue(requests.contains(request)); // make sure the request made it in the queue
    ElementProducer producer = ops.delegate();
    Job job = producer.findJobInCalendar("2022-04-27"); // look up by date
    request.setJob(job); // because request is from set up we need to reconnect job and request
    producer.distribute(job); // now distribute the job
    ops.updateRequestStatus(request, new ApprovedStatus(request));
    ops.updateCalendar(); // remove the Approved Request from the requestsQueue
    Assert.assertFalse(requests.contains(request));
  }

  @Test
  public void TestOperationsDelegatesJobProducerTypeCorrectly() {
    // Step 1) get senior producer singleton
    //SeniorProducer seniorProducer = SeniorProducer.getInstance();
    Event event = new Event();
    try {
      event = seniorProducer.collectDetails(
        "BL",
        "2022-04-26",
        "14:00",
        "2022-04-26",
        "23:00",
        "Army Blues @ Blues Alley",
        "1073 Wisconsin Ave NW, Washington, DC 20007"
      );
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
    // Step 2) request is instantiated
    Request request = new Dd2536Request(event);
    //Operations ops = Operations.getInstance();
    ops.submitRequest(request); // submit the request
    // calling delegate returns an Element Producer
    ElementProducer producer = ops.delegate();
    Assert.assertTrue(producer instanceof BluesProducer);

  }

  @Test
  public void TestOperationsChoosesDelegatorObjectCorrectly() {
    // Step 1) get senior producer singleton
    //SeniorProducer seniorProducer = SeniorProducer.getInstance();
    Event event = new Event();
    try {
      event = seniorProducer.collectDetails(
        "BL",
        "2022-04-28",
        "14:00",
        "2022-04-28",
        "23:00",
        "Army Blues @ Blues Alley",
        "1073 Wisconsin Ave NW, Washington, DC 20007"
      );
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
    // Step 2) request is instantiated
    Request request = new Dd2536Request(event);
    //Operations ops = Operations.getInstance();
    ops.submitRequest(request); // submit the request
    // calling delegate returns an Element Producer
    ElementProducer producer = ops.delegate(); // producer instance of BluesProducer
    // Ops will be tracking its most recent delegation in its class attribute
    Delegator delegator = ops.getDelegator();
    Assert.assertTrue(delegator instanceof BluesDelegator);
  }

  @Test
  public void TestJobTypeIsProperlyDelegated() {
    // Step 1) get senior producer singleton
    //SeniorProducer seniorProducer = SeniorProducer.getInstance();
    Event event = new Event();
    try {
      event = seniorProducer.collectDetails(
        "BL",
        "2022-04-29",
        "14:00",
        "2022-04-29",
        "23:00",
        "Army Blues @ Blues Alley",
        "1073 Wisconsin Ave NW, Washington, DC 20007"
      );
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
    // Step 2) request is instantiated
    Request request = new Dd2536Request(event);
    //Operations ops = Operations.getInstance();
    ops.submitRequest(request); // submit the request
    // calling delegate returns an Element Producer
    ElementProducer producer = ops.delegate(); // producer instance of BluesProducer
    // Ops will be tracking the most recently delegated Job in a class attribute
    Job job = ops.getJob();
    Assert.assertTrue(job instanceof BluesJob);
  }

  @Test
  public void TestSubmittedJobVsApprovedJob() {
    ops.getRequestsQueue().clear();
    Event event = new Event();
    try {
      event = seniorProducer.collectDetails(
        "BL",
        "2022-04-30",
        "14:00",
        "2022-04-30",
        "23:00",
        "Army Blues @ Blues Alley",
        "1073 Wisconsin Ave NW, Washington, DC 20007"
      );
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
    // Step 2) request is instantiated
    Request request = new Dd2536Request(event);
    //Operations ops = Operations.getInstance();
    ops.submitRequest(request);
    PriorityQueue<Request> requests = ops.getRequestsQueue();
    Request targetRequest;
    // Check submitted request status
    if (!requests.isEmpty()) {
      Iterator iterator = requests.iterator();
      while (iterator.hasNext()) {
        targetRequest = (Request) iterator.next();
        Assert.assertTrue(targetRequest.getStatus() instanceof SubmittedStatus);
      }
    }
    ElementProducer producer = ops.delegate();
    Job job = producer.findJobInCalendar("2022-04-30"); // look up by date
    request.setJob(job); // because request is from set up we need to reconnect job and request
    producer.distribute(job); // now distribute the job
    ops.updateRequestStatus(job.getRequest(), new ApprovedStatus(request));
    // check requestsQueue was updated since only request was approved.
    Assert.assertFalse(requests.contains(request));
  }
}