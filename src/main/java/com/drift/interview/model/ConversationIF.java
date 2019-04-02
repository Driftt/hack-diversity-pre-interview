package com.drift.interview.model;

import com.drift.interview.DriftStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@DriftStyle
@JsonDeserialize(as = Conversation.class)
public interface ConversationIF {

  long getId();

  List<Message> getMessages();
}
