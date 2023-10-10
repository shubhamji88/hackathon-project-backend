package com.incedo.Hackathon.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "status")
public class HackathonStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  private String statusName;

  private String startTimeStamp;

  private String endTimeStamp;

  public HackathonStatus() {

  }

  public Long getId() {
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