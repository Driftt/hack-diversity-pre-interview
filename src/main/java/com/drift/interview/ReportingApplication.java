package com.drift.interview;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.guicey.jdbi.JdbiBundle;

import javax.ws.rs.Path;

/**
 * Dropwizard is a Java framework for developing ops-friendly, high-performance, RESTful web services.
 *
 * This is where we bootstrap the application.
 *
 * Most notably:
 *  - We bind our injected util via the ReportingGuiceModule
 *  - We set up our DAO + JDBI (see config.yaml for more information)
 */
public class ReportingApplication extends Application<ReportingConfig> {

  public static void main(String[] args) throws Exception {
    new ReportingApplication().run(args);
  }
  @Override
  public void initialize(Bootstrap<ReportingConfig> bootstrap) {
    GuiceBundle bundle = GuiceBundle.builder()
        .enableAutoConfig(ReportingConfig.class.getPackage().getName())
        .bundles(JdbiBundle.<ReportingConfig>forDatabase((conf, env) -> conf.getDatabase()))
        .modules(new ReportingGuiceModule())
        .build();

    bootstrap.addBundle(bundle);
  }

  @Override
  public void run(ReportingConfig config, Environment environment) {
    environment.healthChecks().register("APIHealthCheck", new ReportingManaged.AppHealthCheck());
    System.out.println("RUNNING");

  }
}
