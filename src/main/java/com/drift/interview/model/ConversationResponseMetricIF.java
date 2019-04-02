package com.drift.interview.model;

import com.drift.interview.DriftStyle;
import org.immutables.value.Value.Immutable;

@DriftStyle
@Immutable
public interface ConversationResponseMetricIF {
  long getConversationId();
  double getAverageResponseMs();
}