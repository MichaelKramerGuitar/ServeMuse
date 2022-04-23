package edu.bu.met.cs665.producers;

import com.google.api.services.calendar.model.Event;
import edu.bu.met.cs665.deliverables.PaoRequest;
import edu.bu.met.cs665.deliverables.Program;
import edu.bu.met.cs665.deliverables.TransRequest;
import edu.bu.met.cs665.jobs.Job;
import edu.bu.met.cs665.ops.Operations;
import edu.bu.met.cs665.request.ApprovedStatus;
import edu.bu.met.cs665.request.Dd2536Request;
import edu.bu.met.cs665.request.InvalidEnsembleException;
import edu.bu.met.cs665.request.Request;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;

/**
 * The purpose of this class is to test functionality associated with an element
 * producer, the object responsible for updating a jobs deliverables once a
 * request has been submitted and delegated.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class ChorusProducerTest {

  private static Operations ops;
  private static SeniorProducer seniorProducer;

  public ChorusProducerTest() {}

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
  public void TestJobPopulatedWithDeliverables() {
    ops.getRequestsQueue().clear();
    Event event = new Event();
    try {
      event = seniorProducer.collectDetails(
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
    Request request = new Dd2536Request(event);
    ops.submitRequest(request);
    ElementProducer producer = ops.delegate();
    if (producer != null) {
      Job job = producer.findJobInCalendar("2022-12-18");
      if (job != null) {
        producer.distribute(job);
        // make sure that the job now has all the deliverables it needs for updating
        Assert.assertTrue(job.getJobSheet() != null);
        Assert.assertTrue(job.getPaoRequest() != null);
        Assert.assertTrue(job.getPaoRequest() != null);
        Assert.assertTrue(job.getTransRequest() != null);
      }
    }
  }

  @Test
  public void TestUpdateProgram() {
    ops.getRequestsQueue().clear();
    Event event = new Event();
    try {
      event = seniorProducer.collectDetails(
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
    Request request = new Dd2536Request(event);
    ops.submitRequest(request);
    ElementProducer producer = ops.delegate();
    if (producer != null) {
      Job job = producer.findJobInCalendar("2022-12-18");
      if (job != null) {
        producer.distribute(job);
        Program defaultProgram = job.getProgram(); // get default program
        // new program ideas
        String newSongs = "newchorussong1, newchorussong2, newchorussong3";
        // create a new program
        Program updatedProgram = new Program();
        // bind new song ideas to the new program
        updatedProgram.setProgram(newSongs);
        // bind new program to job
        job.setProgram(updatedProgram);
        // make sure old program does not match updated program
        Assert.assertFalse(defaultProgram.equals(job.getProgram()));
      }
    }
  }

  @Test
  public void TestUpdateTransRequest() {
    ops.getRequestsQueue().clear();
    Event event = new Event();
    try {
      event = seniorProducer.collectDetails(
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
    Request request = new Dd2536Request(event);
    ops.submitRequest(request);
    ElementProducer producer = ops.delegate();
    if (producer != null) {
      Job job = producer.findJobInCalendar("2022-12-18");
      if (job != null) {
        producer.distribute(job);
        TransRequest transRequest = job.getTransRequest();// get default trans request
        // nothing is set yet.
        Assert.assertTrue(transRequest.getTransTime() == null);
        Assert.assertTrue(transRequest.getEtr() == null);
        Assert.assertTrue(transRequest.getLocation() == null);
        transRequest.setTransTimes("1615", "2330");
        transRequest.setLocation(job.getRequest().getEvent().getLocation());
        job.setTransRequest(transRequest);
        transRequest.updateTransRequest(); // notify drivers
        Assert.assertTrue(transRequest.getTransTime() == job.getTransRequest().getTransTime());
        Assert.assertTrue(transRequest.getEtr() == job.getTransRequest().getEtr());
        Assert.assertTrue(transRequest.getLocation() == job.getTransRequest().getLocation());
      }
    }
  }

  @Test
  public void TestUpdatePaoRequest() {
    ops.getRequestsQueue().clear();
    Event event = new Event();
    try {
      event = seniorProducer.collectDetails(
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
    Request request = new Dd2536Request(event);
    ops.submitRequest(request);
    ElementProducer producer = ops.delegate();
    if (producer != null) {
      Job job = producer.findJobInCalendar("2022-12-18");
      if (job != null) {
        producer.distribute(job);
        PaoRequest paoRequest = job.getPaoRequest();
        // pao should not yet have an event in for this job yet
        Assert.assertTrue(paoRequest.getEvent() == null);
        job.getPaoRequest().setEvent(job.getRequest().getEvent());
        // now event should be synced up
        Assert.assertTrue(paoRequest.getEvent().equals(job.getRequest().getEvent()));
        // update event description
        job.getRequest().getEvent().setDescription("U.S. Army Chorus Handel Massiah @ Midwest");
        // notify pao of updated description
        paoRequest.notifySubscribers();
        // make sure the request has been updated the same way
        Assert.assertTrue(
            paoRequest.getEvent().getDescription() == job.getRequest().getEvent().getDescription());
      }
    }
  }

  @Test
  public void TestUpdateJobSheet() {
    ops.getRequestsQueue().clear();
    Event event = new Event();
    try {
      event = seniorProducer.collectDetails(
        "CHORUS",
        "2022-12-21",
        "13:00",
        "2022-12-24",
        "22:00",
        "Chorus @ Midwest Band Clinic Chicago",
        "2233 S Martin Luther King Dr, Chicago, IL 60616"
      );
    } catch (InvalidEnsembleException | ParseException e) {
      System.out.println(e);;
    }
    Request request = new Dd2536Request(event);
    ops.submitRequest(request);
    ElementProducer producer = ops.delegate();
    if (producer != null) {
      Job job = producer.findJobInCalendar("2022-12-24");
      if (job != null) {
        producer.distribute(job);
        // this calls request.setStatus() which calls job.getJobSheet().updateStatus()
        ops.updateRequestStatus(request, new ApprovedStatus(request));
        // check status updated in request
        Assert.assertTrue(request.getStatus() instanceof ApprovedStatus);
        // check update made it to the job
        Assert.assertTrue(job.getRequest().getStatus() instanceof ApprovedStatus);
      }
    }
  }
}