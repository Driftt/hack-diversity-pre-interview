package com.drift.interview;

import com.drift.interview.client.util.ConversationGenerator;
import com.drift.interview.dao.ConversationDAO;
import com.drift.interview.dao.MessageDAO;
import com.drift.interview.dao.TeamMemberDAO;
import com.drift.interview.model.Conversation;
import com.drift.interview.model.Message;
import com.drift.interview.model.TeamMember;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.dropwizard.lifecycle.Managed;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * this managed class's `start` method is automatically run by Dropwizard
 * during application bootstrapping.
 *
 * This class ensures we have required db structures and data in place for the interview
 */
public class ReportingManaged implements Managed {

  private static final String USER_FILE = "users.json";
  private static final String CONVERSATIONS_FILE = "conversations.json";

  private final ConversationDAO conversationDAO;
  private final MessageDAO messageDAO;
  private final TeamMemberDAO teamMemberDAO;
  private final ObjectMapper objectMapper;

  @Inject
  public ReportingManaged(ConversationDAO conversationDAO,
                          MessageDAO messageDAO,
                          TeamMemberDAO teamMemberDAO,
                          ObjectMapper objectMapper) {
    this.conversationDAO = conversationDAO;
    this.messageDAO = messageDAO;
    this.teamMemberDAO = teamMemberDAO;
    this.objectMapper = objectMapper;
  }

  @Override
  public void start() {
    System.out.println("-- STARTING ReportingManaged");
    conversationDAO.setup();
    messageDAO.setup();
    teamMemberDAO.setup();

    conversationDAO.reset();
    messageDAO.reset();
    teamMemberDAO.reset();

    initializeTeamMembers();
    initializeConversations();
  }

  private void generateConversations(List<TeamMember> teamMembers) {
    int howMany = 1000;
    List<Long> teamMemberIds = teamMembers.stream().map((tm) -> tm.getId()).collect(Collectors.toList());

    long start = System.nanoTime();
    for (int i = 0; i < howMany; i++) {
      Conversation c = ConversationGenerator.generateConversation();
      this.conversationDAO.create(c);

      List<Message> messages = ConversationGenerator.generateMessages(c.getId(), teamMemberIds);
      messages.forEach(messageDAO::create);
      System.out.println("--- Conversation " + String.format("%,d", (i + 1)) + " created with " + messages.size() + " messages");
    }

    String elapsed = String.format("%.3f", (double) Math.round((System.nanoTime() - start) / 100000) / 10000);
    System.out.println("-- " + howMany + " conversations generated and saved in " + elapsed + "s");
  }

  private void initializeTeamMembers() {
    readSampleDataFromFile(USER_FILE, TeamMember.class).forEach(teamMemberDAO::create);
  }

  private void initializeConversations() {
    readSampleDataFromFile(CONVERSATIONS_FILE, Conversation.class).forEach(c -> {
      c.getMessages().forEach(messageDAO::create);
      conversationDAO.create(c);
      System.out.println("Saved conversation " + c.getId());
    });
  }

  private <T> List<T> readSampleDataFromFile(String fileName, Class<T> clz) {
    try {
      JsonParser parser = new JsonFactory().createParser(new File(fileName));
      return objectMapper.readValue(parser, objectMapper.getTypeFactory().constructCollectionType(List.class, clz));
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void stop() {
    System.out.println("-- STOPPING ReportingManaged");
  }
}
