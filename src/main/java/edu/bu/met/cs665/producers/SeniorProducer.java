package edu.bu.met.cs665.producers;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import edu.bu.met.cs665.request.InvalidEnsembleException;
import edu.bu.met.cs665.utilities.DateValidatorStringFormatter;
import edu.bu.met.cs665.utilities.TimeValidator24HourClock;
import java.text.ParseException;
import java.util.Arrays;

/**
 * The purpose of this Singleton class is to assemble the details of an event received
 * from a client and produce a request which will be submitted to OPS.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class SeniorProducer {

  private static SeniorProducer seniorProducer;
  private final String[] ensembles = new String[]{
      "BL",
      "DR",
      "STRINGS",
      "CM",
      "CT",
      "SR",
      "LME",
      "CHORUS",
      "COMBO"
  };

  private static final String dateFormat = "yyyy-MM-dd"; // for Google Calendar API
  private static final String defaultTimeZone = "America/Washington_DC";

  /**
   * The purpose of this method is to ensure no other can can instantiate a
   * new instance - private constructor.
   */
  private SeniorProducer() {}

  /**
   * The purpose of this method is to create the Producer instance if not
   * yet created, otherwise return the one that already exists.
   * @return This Instance of Producer
   */
  public static SeniorProducer getInstance() {
    if (seniorProducer == null) {
      seniorProducer = new SeniorProducer();
    }
    return seniorProducer;
  }

  /**
   * Template style method for assembling an event to pass into a Request.
   * Important note: we validate ensemble and all time information but blindly
   * trust the description string (engagement title, i.e. "Army Blues @ Blues Alley")
   * and location string (because it seems futile to regex check against addresses
   * given the wide range of possible acceptable formats. In practice these would
   * need to be human verified with POC (Point of Contact i.e. Requestor).
   */
  public Event collectDetails(String ensemble,
                             String startDate,
                             String startTime,
                             String endDate,
                             String endTime,
                             String description,
                             String location) throws InvalidEnsembleException, ParseException {
    // How to use Google Calendar API
    // https://developers.google.com/calendar/api/v3/reference/events/insert
    Event event = new Event(); // create empty event
    try {
      setEnsemble(event, ensemble);
    } catch (InvalidEnsembleException e) {
      throw e;
    }
    try {
      setStartTime(event, startDate, startTime);
      setEndTime(event, endDate, endTime);
    } catch (ParseException invalidTime) {
      throw invalidTime;
    }
    event
        .setDescription(description) // like title of event "Army Blues at Blues Alley"
        .setLocation(location);

    return event;
  }

  /**
   * Sets the events ensemble by Google Calendar event.setSummary().
   * @param event Google Calendar event.
   * @param ensemble String
   * @throws InvalidEnsembleException if ensemble is not registered with ensembles class attribute.
   */
  public void setEnsemble(Event event, String ensemble) throws InvalidEnsembleException {
    // check if string passed in is registered in the ensembles array
    if (!Arrays.stream(ensembles).anyMatch(ensemble.toUpperCase()::equals)) {
      throw new InvalidEnsembleException("Error: " + ensemble + "is not registered.");
    }
    event.setSummary(ensemble.toUpperCase()); // summary field for ensemble

  }

  /**
   * Sets start time for Google Calendar API event.setStart().
   * @param event Google Calendar Event
   * @param startDate yyyy-MM-dd
   * @param startTime HH:MM 24-hour clock.
   * @throws ParseException if invalid startDate.
   */
  public void setStartTime(Event event,
                           String startDate,
                           String startTime) throws ParseException {
    boolean validDate;
    boolean validTime;
    try {
      validDate = validDate(startDate);
    } catch (ParseException e) {
      throw e;
    }
    validTime = validTime(startTime);
    if (validDate && validTime) {
      DateTime startDateTime = new DateTime(formatRfc3339(startDate, startTime));
      EventDateTime start = new EventDateTime()
          .setDateTime(startDateTime)
          .setTimeZone(defaultTimeZone); // timezone defaulting, update as needed.
      event.setStart(start);
    }
  }

  /**
   * Sets end time for Google Calendar API event.setEnd().
   * @param event Google Calendar Event
   * @param endDate yyyy-MM-dd
   * @param endTime HH:MM 24-hour clock.
   * @throws ParseException if invalid startDate.
   */
  public void setEndTime(Event event,
                           String endDate,
                           String endTime) throws ParseException {
    boolean validDate;
    boolean validTime;
    try {
      validDate = validDate(endDate);
    } catch (ParseException e) {
      throw e;
    }
    validTime = validTime(endTime);
    if (validDate && validTime) {
      DateTime endDateTime = new DateTime(formatRfc3339(endDate, endTime));
      EventDateTime end = new EventDateTime()
          .setDateTime(endDateTime)
          .setTimeZone(defaultTimeZone); // timezone defaulting, update as needed.
      event.setEnd(end);
    }
  }

  /**
   * Validates any format, need yyyy-MM-dd for this class, could theoretically
   * be any formatter string form java.text.SimpleDateFormat.
   * @param date try for yyyy-MM-dd
   * @return true if valid.
   * @throws ParseException if invalid.
   */
  public boolean validDate(String date) throws ParseException {
    boolean valid;
    DateValidatorStringFormatter validator = new DateValidatorStringFormatter(dateFormat);
    try {
      valid = validator.validate(date);
    } catch (ParseException e) {
      throw e;
    }
    return valid;
  }

  /**
   * The purpose of this method is to validate an HH:MM time string with
   * 24 hour clock validator.
   * @param time HH:MM desired format.
   * @return true if good 24-hour clock format, else false.
   */
  public boolean validTime(String time) {
    TimeValidator24HourClock validator = new TimeValidator24HourClock();
    boolean validTime = validator.validate(time);
    return validTime;
  }

  /**
   * The purpose of this method is to format a Rfc3339 string given a
   * specifically formatted date and time. For Google Events API.
   * @param date String YYYY-MM-DD
   * @param time String HH:MM
   * @return a properly formatted string
   */
  public String formatRfc3339(String date, String time) {
    // perform some checks first
    return date + "T" + time + ":00+00:00";
  }

}
