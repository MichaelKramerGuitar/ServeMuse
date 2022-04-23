package edu.bu.met.cs665.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeValidator24HourClock implements TimeValidator {

  /**
   * The purpose of this method is to validate time in 24 hour (military) format.
   * @author https://www.geeksforgeeks.org/how-to-validate-time-in-24-hour-format-using-regular-expression/
   * @param timeFormat HH:MM 24 hour clock
   * @return true if valid, else false.
   */
  @Override
  public boolean validate(String timeFormat) {
    // Regex to check valid time in 24-hour format.
    String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    // Compile the ReGex
    Pattern p = Pattern.compile(regex);

    // If the time is empty
    // return false
    if (timeFormat == null) {
      return false;
    }

    // Pattern class contains matcher() method
    // to find matching between given time
    // and regular expression.
    Matcher m = p.matcher(timeFormat);

    // Return if the time
    // matched the ReGex
    return m.matches();
  }
}
