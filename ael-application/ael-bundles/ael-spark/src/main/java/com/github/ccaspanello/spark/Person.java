package com.github.ccaspanello.spark;

import java.io.Serializable;

/**
 * Created by ccaspanello on 4/23/18.
 */
public class Person implements Serializable {

  private String firstName;
  private String lastName;

  public Person(String firstName, String lastName){

  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName( String firstName ) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName( String lastName ) {
    this.lastName = lastName;
  }
}
