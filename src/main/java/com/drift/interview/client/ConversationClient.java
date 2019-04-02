package com.drift.interview.client;

import com.drift.interview.dao.ConversationDAO;
import com.drift.interview.dao.MessageDAO;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import java.util.List;
import com.drift.interview.model.Conversation;
import java.util.stream.Collectors;

public class ConversationClient {

  private final ConversationDAO conversationDAO;
  private final MessageDAO messageDAO;
  @Inject
  public ConversationClient(ConversationDAO conversationDAO,
                            MessageDAO messageDAO) {
    this.conversationDAO = conversationDAO;
    this.messageDAO = messageDAO;
  }

  /**
   * Return a conversation by id from db
   *
   * @return  returns the given conversation from the db or absent if not found
   * @see Conversation
   */
  public Optional<Conversation> getConversation(long id) {
    return conversationDAO.find(id).transform(this::populateMessages);
  }

  private Conversation populateMessages(Conversation conversation) {
    return conversation.withMessages(messageDAO.findByConversationId(conversation.getId()));
  }
}
