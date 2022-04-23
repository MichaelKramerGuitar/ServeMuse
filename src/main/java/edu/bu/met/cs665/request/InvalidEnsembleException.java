package edu.bu.met.cs665.request;

/**
 * The purpose of this class is to throw an exception if the ensemble is not
 * registered in the ensembles attribute of the Producer Singleton.
 *
 * @author Michael Kramer
 *
 * <p>CS665 Spring 2, 2022 Software Design Patterns</p>
 */
public class InvalidEnsembleException extends Exception {

  public InvalidEnsembleException(String message) {
    super(message);
  }

}
