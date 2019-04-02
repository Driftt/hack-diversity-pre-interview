package com.drift.interview;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.immutables.value.Value.Style;
import org.immutables.value.Value.Style.ImplementationVisibility;

/**
 * The DriftStyle annotation lets us write shorthand interfaces which will
 * be compiled into immutable builders.
 *
 * Look at /model/MessageIF to see the shorthand.
 *
 * (after compilation) you may import com.drift.interview.model.Message
 * and generate objects like so:
 *
 * Message msg = Message.builder().setId(8675309).build();
 */
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@JsonSerialize
@Style(
    get = {"is*", "get*"},
    init = "set*",
    typeAbstract = {"Abstract*", "*IF"},
    typeImmutable = "*",
    jdkOnly=true,
    buildOrThrow = "buildOrThrow",
    throwForInvalidImmutableState = DriftStyle.InvalidBuilderException.class,
    optionalAcceptNullable = true,
    visibility = ImplementationVisibility.SAME
)
public @interface DriftStyle {
  class InvalidBuilderException extends RuntimeException {
    public InvalidBuilderException(String message) {
      super(message);
    }
  }
}
