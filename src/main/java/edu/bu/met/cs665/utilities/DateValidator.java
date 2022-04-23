package edu.bu.met.cs665.utilities;

import java.text.ParseException;

/**
 * The purpose of this class is to give a flexible interface to inherit from
 * for checking dates.
 *
 * @author Chandra Prakash, https://www.baeldung.com/java-string-valid-date
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public interface DateValidator {

  /**
   * The purpose of this method is to give a flexible check to validate a date
   * format.
   * @param dateFormat flexible formatting.
   * @return boolean indicating match.
   */
  boolean validate(String dateFormat) throws ParseException;
}
