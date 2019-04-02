package com.drift.interview.model;

import com.drift.interview.DriftStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.annotation.Nullable;
import org.immutables.value.Value.Immutable;

@Immutable
@DriftStyle
@JsonDeserialize(as = TeamMember.class)
public interface TeamMemberIF {

  long getId();

  String getName();

  @Nullable
  String getAvatar();

  String getEmail();
}
