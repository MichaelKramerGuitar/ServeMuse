package edu.bu.met.cs665.request;

import com.google.api.services.calendar.model.Event;
import edu.bu.met.cs665.jobs.Job;
import edu.bu.met.cs665.ops.Operations;
import edu.bu.met.cs665.producers.ElementProducer;
import edu.bu.met.cs665.producers.SeniorProducer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;

/**
 * The purpose of this class is to test the State Transitions of the Status
 * Classes.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class StatusTest {

  private static Operations ops;
  private static SeniorProducer seniorProducer;

  public StatusTest() {}

  @BeforeClass
  public static void setUp() {
    ops = Operations.getInstance();
    seniorProducer = SeniorProducer.getInstance();
  }

  @AfterClass
  public static void tearDown() {
    ops.getRequestsQueue().clear();
  }

  /**
   * Illegal to re-submit submitted status.
   * @throws StatusTransitionException expected here.
   */
  @Test(expected = StatusTransitionException.class)
  public void TestSubmittedStatusCannotResubmitStatus() throws StatusTransitionException {
    ops.getRequestsQueue().clear();
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
      // Step 2) request is instantiated
      Request request = new Dd2536Request(event);
      ops.submitRequest(request);
      ElementProducer producer = ops.delegate();
      Job job = producer.findJobInCalendar("2022-04-25"); // look up by date
      request.setJob(job);
      SubmittedStatus submitted = new SubmittedStatus(request);
      submitted.submit();
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
  }

  /**
   * Legal to go from submitted to approved.
   * @throws StatusTransitionException not expected here.
   */
  @Test
  public void TestSubmittedStatusTransitionToApprovedStatus() throws StatusTransitionException {
    ops.getRequestsQueue().clear();
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
      // Step 2) request is instantiated
      Request request = new Dd2536Request(event);
      ops.submitRequest(request);
      ElementProducer producer = ops.delegate();
      Job job = producer.findJobInCalendar("2022-04-25"); // look up by date
      request.setJob(job);
      SubmittedStatus submitted = new SubmittedStatus(request);
      submitted.approve();
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
  }

  /**
   * Legal Submitted -> Denied.
   * @throws StatusTransitionException - not expected.
   */
  @Test
  public void TestSubmittedStatusTransitionToDeniedStatus() throws StatusTransitionException {
    ops.getRequestsQueue().clear();
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
      // Step 2) request is instantiated
      Request request = new Dd2536Request(event);
      ops.submitRequest(request);
      ElementProducer producer = ops.delegate();
      Job job = producer.findJobInCalendar("2022-04-25"); // look up by date
      request.setJob(job);
      SubmittedStatus submitted = new SubmittedStatus(request);
      submitted.deny();
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
  }

  /**
   * Illegal Approved -> Submitted, already submitted.
   * @throws StatusTransitionException expected.
   */
  @Test(expected = StatusTransitionException.class)
  public void TestApprovedToSubmittedStatus() throws StatusTransitionException {
    ops.getRequestsQueue().clear();
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
      // Step 2) request is instantiated
      Request request = new Dd2536Request(event);
      ops.submitRequest(request);
      ElementProducer producer = ops.delegate();
      Job job = producer.findJobInCalendar("2022-04-25"); // look up by date
      request.setJob(job);
      ApprovedStatus approved = new ApprovedStatus(request);
      approved.submit();
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
  }

  /**
   * Illegal Approved -> Approved, redundant.
   * @throws StatusTransitionException expected.
   */
  @Test(expected = StatusTransitionException.class)
  public void TestApprovedToApprovedStatus() throws StatusTransitionException {
    ops.getRequestsQueue().clear();
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
      // Step 2) request is instantiated
      Request request = new Dd2536Request(event);
      ops.submitRequest(request);
      ElementProducer producer = ops.delegate();
      Job job = producer.findJobInCalendar("2022-04-25"); // look up by date
      request.setJob(job);
      ApprovedStatus approved = new ApprovedStatus(request);
      approved.approve();
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
  }

  /**
   * Legal Approved -> Denied. Unlikely in the real world but possible.
   * @throws StatusTransitionException expected.
   */
  @Test
  public void TestApprovedToDenied() throws StatusTransitionException {
    ops.getRequestsQueue().clear();
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
      // Step 2) request is instantiated
      Request request = new Dd2536Request(event);
      ops.submitRequest(request);
      ElementProducer producer = ops.delegate();
      Job job = producer.findJobInCalendar("2022-04-25"); // look up by date
      request.setJob(job);
      ApprovedStatus approved = new ApprovedStatus(request);
      approved.deny();
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
  }

  /**
   * Illegal Denied to any other state.
   * @throws StatusTransitionException expected.
   */
  @Test(expected = StatusTransitionException.class)
  public void TestDeniedToSubmittedStatus() throws StatusTransitionException {
    ops.getRequestsQueue().clear();
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
      // Step 2) request is instantiated
      Request request = new Dd2536Request(event);
      ops.submitRequest(request);
      ElementProducer producer = ops.delegate();
      Job job = producer.findJobInCalendar("2022-04-25"); // look up by date
      request.setJob(job);
      DeniedStatus denied = new DeniedStatus(request);
      denied.submit();
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
  }

  /**
   * Illegal Denied to any other state.
   * @throws StatusTransitionException expected.
   */
  @Test(expected = StatusTransitionException.class)
  public void TestDeniedToApprovedStatus() throws StatusTransitionException {
    ops.getRequestsQueue().clear();
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
      // Step 2) request is instantiated
      Request request = new Dd2536Request(event);
      ops.submitRequest(request);
      ElementProducer producer = ops.delegate();
      Job job = producer.findJobInCalendar("2022-04-25"); // look up by date
      request.setJob(job);
      DeniedStatus denied = new DeniedStatus(request);
      denied.approve();
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
  }

  /**
   * Illegal Denied to any other state. Redundant Denied -> Denied.
   * @throws StatusTransitionException expected.
   */
  @Test(expected = StatusTransitionException.class)
  public void TestDeniedToDeniedStatus() throws StatusTransitionException {
    ops.getRequestsQueue().clear();
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
      // Step 2) request is instantiated
      Request request = new Dd2536Request(event);
      ops.submitRequest(request);
      ElementProducer producer = ops.delegate();
      Job job = producer.findJobInCalendar("2022-04-25"); // look up by date
      request.setJob(job);
      DeniedStatus denied = new DeniedStatus(request);
      denied.deny();
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
  }
}