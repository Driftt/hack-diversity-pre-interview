# Hello Hack.Diversity!

This application is a facsimile of a reporting microservice. It's a sandbox that mimics the kind of environment you might see as an engineer.

## Prologue: The Best of the Best

The right reporting, analytics, and information delivery strategy can have a significant impact on an organization. It can fundamentally change the way people perform their jobs and the way decisions are made.

**You are the engineer responsible** for generating our very first report. There is one important question that we want to answer:

1. How long does it take for my team members to RESPOND to end users who send us chats?

Someone else has gotten a head start on this service and have handed it off to you, leaving you to implement some of the calculations that will bring this data to life.

## Your Goals

1. Fork this repo
2. Get this Java app set up (instructions below, feel free to use the internet for help!)
3. Complete the "health check" challenge
4. Complete the "average response time" challenge
5. Submit your work by Adding https://github.com/ptemplin / @ptemplin as a collaborator and emailing ptemplin@drift.com 

## Setup

1. Checkout a new git branch before you begin
2. Open in Intellij
3. If necessary, run `mvn clean install -U`
4. Run in debug mode using `Start Interview Application` runtime config 🐞

If it's running, your console should be free of errors.
 
# Challenge 1: Health check

This is your warm-up. 🔥

⁉️️ Please make a GET request to a healthcheck endpoint in your app to confirm that it's running.

✅ `@GET localhost:7070/metrics/healthcheck` should return a 200 OK.

# Challenge 2: Implement average response time

⁉️️ Implement a method (already stubbed) to calculate and return the average response time in a conversation.

✅ `@GET localhost:7070/metrics/conversations/2` to return the response time for conversation #1.
 
#### Details

Conversation object signatures look like this:
```
Conversation{id=0, messages=[]}
``` 

Message object signatures look like this:
```
Message{id=0, conversationId=0, message="", createdAt="", authorId=0, isTeamMember=true|false}
``` 

The spec for calculating the average response time based on the following:

* Response time is defined as the time between an `End User` message and the next `Teammate` message.
* If there are multiple `End User` messages in a sequence, calculate the response time from the first end user message.

For the conversation:

 ```
 @0.0s  End User:    Hello
 @2.0s  End User:    I'd like to know more about Thneeds
 @6.0s  Team member: I'm here to help. What's your question?
 @6.5s  End User:    What can it do?
 @8.5s  Team member: The real question is what can't it do
 @9.5s  Team member: A Thneed can do anything you need!
 ```

The final response should look like this:
 
 ```
 # GET /metrics/conversations/2
 {
   "conversationId": 2,
   "avgResponseMs": 4000.00
 }
 ```

 Which other cases would be useful to validate with unit tests? Feel free to add these as part of your solution if you feel that they are valuable.
  
# Resources
 
## Tech
 
 There are some tools in use with which you may not be familiar.
 
 #### [Dropwizard](https://www.dropwizard.io/1.3.5/docs/getting-started.html)
 
 > Dropwizard straddles the line between being a library and a framework. Its goal is to provide performant, reliable implementations of everything a production-ready web application needs. Because this functionality is extracted into a reusable library, your application remains lean and focused, reducing both time-to-market and maintenance burdens.  
 
 #### [Guice](https://github.com/google/guice/wiki/Motivation)
 
 We rely on Guice for dependency injection. Here's what they have to say:
 
 > ...dependency injection is just a design pattern. The core principle is to separate behaviour from dependency resolution. In our example, the RealBillingService is not responsible for looking up the TransactionLog and CreditCardProcessor. Instead, they're passed in as constructor parameters.
 
 #### [Immutables](https://immutables.github.io/)
 
 > Java annotation processors to generate simple, safe and consistent value objects. Do not repeat yourself, try Immutables, the most comprehensive tool in this field!
 
 #### [RxJava](https://github.com/ReactiveX/RxJava)
 
 We access [ReactiveX](http://reactivex.io/) via RxJava
 
 > An API for asynchronous programming with observable streams