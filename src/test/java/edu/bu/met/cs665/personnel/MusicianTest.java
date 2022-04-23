package edu.bu.met.cs665.personnel;

import com.google.api.services.calendar.model.Event;
import edu.bu.met.cs665.jobs.Job;
import edu.bu.met.cs665.ops.Operations;
import edu.bu.met.cs665.producers.ElementProducer;
import edu.bu.met.cs665.producers.SeniorProducer;
import edu.bu.met.cs665.request.*;
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
public class MusicianTest {

  public void MusicianTest() {}

  private Job job;
  private String[] roster = new String[]{
      "one@one.com",
      "two@two.com",
      "three@three.com"
  };
  private ArrayList<Musician> musicians = new ArrayList<Musician>();

  @Test
  public void TestMusicianSubscribers() {
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
        musicians.add(new Musician(
          roster[i],
          job.getJobSheet(),
          job.getProgram()
        ));
        // Get Job Status
        Status status = job.getRequest().getStatus();
        Status musicianStatus = musicians.get(0).getJobSheet().getStatus();
        // Check Job Status is same as the Musicians job sheet
        Assert.assertTrue(status.equals(musicianStatus));
        // Check this is Submitted
        Assert.assertTrue(musicianStatus instanceof SubmittedStatus);

        // update status
        ops.updateRequestStatus(request, new ApprovedStatus(request));

        // status should be updated
        status = job.getRequest().getStatus();
        // musician status should be updated by Observer Pattern
        musicianStatus = musicians.get(0).getJobSheet().getStatus();
        Assert.assertTrue(status.equals(musicianStatus));
        // Check this is Approved
        Assert.assertTrue(musicianStatus instanceof ApprovedStatus);
      }
    }
  }
}