package com.drift.interview;

import com.drift.interview.dao.ConversationDAO;
import com.drift.interview.dao.MessageDAO;
import com.drift.interview.dao.TeamMemberDAO;
import com.google.inject.Provides;
import org.skife.jdbi.v2.DBI;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

/**
 *
 */
public class ReportingGuiceModule extends DropwizardAwareModule<ReportingConfig> {

  @Provides
  ConversationDAO providesConversationDAO(DBI dbi) {
    return dbi.onDemand(ConversationDAO.class);
  }

  @Provides
  MessageDAO providesMessageDAO(DBI dbi) {
    return dbi.onDemand(MessageDAO.class);
  }

  @Provides
  TeamMemberDAO providesTeamMemberDAO(DBI dbi) {
    return dbi.onDemand(TeamMemberDAO.class);
  }

  @Override
  protected void configure() {
    bind(ReportingManaged.class);
  }
}