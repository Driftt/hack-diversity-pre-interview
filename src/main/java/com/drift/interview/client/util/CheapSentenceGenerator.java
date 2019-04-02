package com.drift.interview.client.util;

public class CheapSentenceGenerator {
  public CheapSentenceGenerator() { }

  public static String generate() {
    String[] actors = {"I", "You", "We", "They"};
    String[] verbs = {"could be", "will be", "should be"};
    String[] adjectives = {"delightful", "hilarious", "noble", "courteous", "kind", "cheerful", "thrifty", "brave"};

    int rand1 = (int) (Math.random() * actors.length);
    int rand2 = (int) (Math.random() * verbs.length);
    int rand3 = (int) (Math.random() * adjectives.length);

    StringBuilder sb = new StringBuilder();
    sb.append(actors[rand1]);
    sb.append(" ");
    sb.append(verbs[rand2]);
    sb.append(" ");
    sb.append(adjectives[rand3]);

    String phrase = sb.toString();
    return phrase;
  }
}
