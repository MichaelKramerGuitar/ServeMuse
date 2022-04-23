package edu.bu.met.cs665.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The purpose of this class is to give a flexible interface to inherit from
 * for checking dates.
 *
 * @author Chandra Prakash, https://www.baeldung.com/java-string-valid-date
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class DateValidatorStringFormatter implements DateValidator {

  private String format; // yyyy-MM-dd for Google API but class is flexible here.

  public DateValidatorStringFormatter(String format) {
    this.format = format;
  }

  /**
   * Validate input.
   * @param dateFormat flexible formatting.
   * @return true valid.
   * @throws ParseException if invalid date.
   */
  @Override
  public boolean validate(String dateFormat) throws ParseException {
    DateFormat sdf = new SimpleDateFormat(this.format);
    sdf.setLenient(false);
    try {
      sdf.parse(dateFormat);
    } catch (ParseException e) {
      throw e;
    }
    return true;
  }
}

