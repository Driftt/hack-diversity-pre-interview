package com.drift.interview.client.util;

import com.drift.interview.model.Conversation;
import com.drift.interview.model.Message;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ConversationGenerator {

  public ConversationGenerator() {}

  public static Conversation generateConversation() {
    return Conversation.builder().build();
  }

  public static List<Message> generateMessages(long conversationId, List<Long> teamMemberIds) {
    int arbitraryNumMessages = ThreadLocalRandom.current().nextInt(2, 100);
    int arbitraryEndUserId = ThreadLocalRandom.current().nextInt(2, 10000);

    List<Message> messages = new ArrayList<>();
    LocalDate date = LocalDate.of(2018, 9, 1);
    long messageStartedAt = date.atStartOfDay(ZoneId.of("UTC")).toEpochSecond();

    long teamMemberId = teamMemberIds.get(ThreadLocalRandom.current().nextInt(0, teamMemberIds.size()));

    for (int i = 0; i < arbitraryNumMessages; ++i) {
      messageStartedAt = messageStartedAt + ThreadLocalRandom.current().nextInt(10, 60 * 10);
      Message.Builder msg = Message.builder();

      msg.setCreatedAt(messageStartedAt);
      if (ThreadLocalRandom.current().nextInt(1, 100) > 60) {
        msg.setAuthorId(arbitraryEndUserId);
      } else {
        msg.setAuthorId(teamMemberId).setTeamMember(true);
      }
      messages.add(msg.setConversationId(conversationId).build());
    }
    return messages;
  }
}
