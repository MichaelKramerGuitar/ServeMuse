package edu.bu.met.cs665.personnel;

import com.google.api.services.calendar.model.Event;
import edu.bu.met.cs665.deliverables.TransRequest;
import edu.bu.met.cs665.jobs.Job;
import edu.bu.met.cs665.ops.Operations;
import edu.bu.met.cs665.producers.ElementProducer;
import edu.bu.met.cs665.producers.SeniorProducer;
import edu.bu.met.cs665.request.Dd2536Request;
import edu.bu.met.cs665.request.InvalidEnsembleException;
import edu.bu.met.cs665.request.Request;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * The purpose of this class is to test the subscriber base of this deliverable
 * that they receive their deliverables and updates to these properly.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class DriverTest {

  public void DriverTest() {}

  private Job job;
  private String[] roster = new String[]{
    "one@one.com",
    "two@two.com",
    "three@three.com"
  };
  private ArrayList<Driver> drivers = new ArrayList<Driver>();

  @Test
  public void TestDriverSubscribers() {
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

    Request request = new Dd2536Request(event); // Step 2) request is instantiated
    Operations ops = Operations.getInstance();

    ElementProducer producer = ops.delegate();
    // with the producer reference we can query jobs by start date
    if (producer != null && job != null) {
      job = producer.findJobInCalendar("2022-04-25");
      for (int i = 0; i < roster.length; i++) {
        drivers.add(new Driver(
          roster[i],
          job.getTransRequest()
        ));
        TransRequest transRequest = job.getTransRequest();
        TransRequest driverTransRequest = drivers.get(0).getTransRequest();
        // check that drivers subscribed properly to trans request
        Assert.assertTrue(transRequest.equals(driverTransRequest));
        transRequest.setLocation(request.getEvent().getLocation());
        String transTime = "0900"; // arbitrary based on job distance
        String etr = "2200"; // again arbitrary
        transRequest.setTransTimes(transTime, etr);
        driverTransRequest = drivers.get(0).getTransRequest();
        // recheck the updates received
        Assert.assertTrue(transRequest.equals(driverTransRequest));

      }
    }
  }
}

