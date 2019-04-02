package com.drift.interview.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
  private static final String OK_RESULT = "OK";

  @JsonIgnore
  public static final Result OK = new Result();
  public static final Result NOT_OK = new Result("error");
  public static final Result NOT_FOUND = new Result("not_found");

  @JsonProperty
  private final String result;

  public Result() {
    this("OK");
  }

  public Result(String result) {
    this.result = result;
  }
}

