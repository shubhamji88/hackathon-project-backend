package com.incedo.Hackathon.models;

import jakarta.persistence.*;

@Entity
@Table(name = "status")
public class HackathonStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String statusName;

  private String startTimeStamp;

  private String endTimeStamp;

  public HackathonStatus() {

  }

  public Integer getId() {
    return id;
  }

  public String getStatusName() {
    return statusName;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public String getStartTimeStamp() {
    return startTimeStamp;
  }

  public void setStartTimeStamp(String startTimeStamp) {
    this.startTimeStamp = startTimeStamp;
  }

  public String getEndTimeStamp() {
    return endTimeStamp;
  }

  public void setEndTimeStamp(String endTimeStamp) {
    this.endTimeStamp = endTimeStamp;
  }
}