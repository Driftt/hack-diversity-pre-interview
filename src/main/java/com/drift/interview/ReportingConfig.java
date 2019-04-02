package com.drift.interview;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * This configures environment-specific parameters mapped from a config.yaml
 * https://www.dropwizard.io/1.3.5/docs/getting-started.html#creating-a-configuration-class
 */
public class ReportingConfig extends Configuration {
  @Valid
  @NotNull
  @JsonProperty
  private DataSourceFactory database = new DataSourceFactory();

  public DataSourceFactory getDatabase() {
    return database;
  }
}
