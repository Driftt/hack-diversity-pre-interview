package com.drift.interview.reporting;

import com.drift.interview.model.Conversation;
import com.drift.interview.model.ConversationResponseMetric;
import com.drift.interview.model.Message;
import java.util.List;

public class ConversationMetricsCalculator {
  public ConversationMetricsCalculator() {}

  /**
   * Returns a ConversationResponseMetric object which can be used to power data visualizations on the front end.
   */
  ConversationResponseMetric calculateAverageResponseTime(Conversation conversation) {
    List<Message> messages = conversation.getMessages();

    // implement me!

    /*
    Okay!
    Notes : Message{id=0, conversationId=0, message="", createdAt="", authorId=0, isTeamMember=true|false}
    Goal: to find the average response time per message
    Note to self, endUser is equal to self
    */

    //create = the time message is created at, we will initiate it at 0 because that would represent the start time
    double createTime = 0;

    //response time of message initiated
    double responseTime;

    //The total amount of response initiated
    int ResponseCounter = 0;

    //difference between created time and response time
    double difference;

    //difference between created time and response time
    double totalSum = 0;

    //avgResponseMs = responseTime / totalResponse
    double avgResponseMs =0;

    //keeps track of endUser
    int selfCounter = 0;

    //keeps track of teamMember
    int teamCounter = 0;

    for (Message message : messages) {
      if (message.isTeamMember() && teamCounter < 1){
        responseTime = message.getCreatedAt();
        difference = responseTime - createTime;
        totalSum += difference;
        ResponseCounter += 1;
        selfCounter = 0;
        teamCounter +=1;
      }

      else{
        if (selfCounter < 1) {
          createTime = message.getCreatedAt();
          teamCounter = 0;
          selfCounter += 1;
        }
        else{
          selfCounter += 1;
          teamCounter = 0;
          }
      }
    }



    if (ResponseCounter>0){
      avgResponseMs = totalSum / ResponseCounter;
    }

    /*
    Which other cases would be useful to validate with unit tests?
    Feel free to add these as part of your solution if you feel that they are valuable.


    I tried to add adding the total number of messages, because that seemed like a valuable part of the chat between user an end user.
    As a way to tell if the chat ended with this amount of messages.
     */


    return ConversationResponseMetric.builder()
        .setConversationId(conversation.getId())
        .setAverageResponseMs(avgResponseMs)
        //.setNumberofMessages(5)
        .build();
  }
}
