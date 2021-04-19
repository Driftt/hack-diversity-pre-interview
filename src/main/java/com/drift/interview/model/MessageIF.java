package com.drift.interview.model;

import com.drift.interview.DriftStyle;
import com.drift.interview.client.util.CheapSentenceGenerator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@DriftStyle
@JsonDeserialize(as = Message.class)
public interface MessageIF {

  long getConversationId();

  @Default
  default long getId() {
    return System.nanoTime();
  }

  @Default
  default long getCreatedAt() { return 0; }

  long getAuthorId();

  @Default
  default boolean isTeamMember() {
    return false;
  }

  @Default
  default String getMessage() {
    return CheapSentenceGenerator.generate();
  }

  //@Default
  //default int getNumberofMessages() { return 0; }
}
