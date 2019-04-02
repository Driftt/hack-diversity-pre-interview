
package com.drift.interview.model;

import com.drift.interview.DriftStyle;
import org.immutables.value.Value.Immutable;

@DriftStyle
@Immutable
public interface TeamMemberResponseMetricIF {

  long getId();

  double getMedian();

  double getVariance();
}