# 1 Project Guidelines
One of the requirements for CS665 course is the final project (20% of the grade). The project must
be done individually. This is an opportunity for you to be creative in solving a problem that is of
interest to you and demonstrate your software design proficiency. The project should be challenging
enough so that you could discuss with professional software developers.
We have covered in our class a list of design patterns. We have learned about _Strategy, Factory,
Abstract Factory, Singleton, Prototype, Command, Observer, State, Template, Facade, Decorator,
Composite, Adapter, Proxy, Iterator_ and _Mediator_ patterns.
However, the list of existing design patterns are longer than what we covered in our class. You
can find a list of design patterns that can be used in Java programming language on the following
website:
http://java-design-patterns.com/patterns/
Your main task for the class project is to learn about further design patterns. You should pick
up one of the design patterns that we have not covered in our class, learn it on your own, create
and describe a use case scenario for it (it should be a new scenario which is not described in any
references like books or Websites), implement it in your favorite programing language and create a
presentation about the design pattern and your scenario.
It is recommended to can use in your project a combination of design patterns because in real
software projects mostly a combination of patterns are used.
Your project should include one design pattern that we have not covered in our class. You
read the above list and find select one of the design patterns that you like. Some examples of
important patterns that we have not covered deeply in our class are: Visitor, Bridge, Mediator,
Builder, Callback, Delegation, Thread Pool.

**Note:** Your project idea should not be copied from existing books or Websites. Your project
should be a unique project based on your own ideas.

# ServeMuse Description
* A Design to Automate Mission Creation and Delegation for The United States Army Band “Pershing’s Own”
* Flexible, Reusable and Sufficient System for mission processing potentially applicable
  * For other military bands
  * For other non-musical military units
  * For civilian music contractors
  * Potentially for any corporate-style job tasking


# ServeMuse Usage Example:

* SeniorProducer class compiles job details into a Google Calendar Event
  * Need dependency to pom to use Google Calendar Event
  * groupID: com.google.apis
  * artifactId: google-api-services-calendar
  * Version: v3-rev260-1.23.0
* Event is passed into a Request Object
* Request is passed into Operations.submitRequest()
* Request now has a Status (i.e. State) of SubmittedStatus
  * Operations.delegate()
    * Returns an ElementProducer but first:
      * Sets the appropriate Delegator Object
      * Creates the appropriate JobFactory
      * Calls the appropriate ElementProducer (object to be returned)
        * Uses the Delegator object to bind the JobFactory and request
        * Adds the delegated job to the ElementProducers calendar vector before returning the object
* ElementProducer keeps track of jobs assigned to its elements 
  * ElementProducer can ```findJobInCalendar()```
    * Update deliverables attached to Job as needed and ```distribute()``` updates to Concrete SubscriberBase classes in ```personnel``` package

# 2 Project Tasks
## 2.1 Task 1 : Design pattern and its Use Case Scenario Description. (4 points)
Provide a description of the application use case scenario that you have selected for your final
project.
Describe what are your main software design concepts regarding this application.
For example describe:
* What are the design goals in your project?
  * Flexibility
    * I will have 10 years of military service in June, 8 of those years has been with TUSAB.
      The one constant over that timeframe has been a continual change in requirements, especially
      regarding processing missions. Generally, these changes are minute, however, the way
      ServeMuse is designed is with generally broad "strokes" that capture concepts that have
      been around since before I was born: job sheets, transportation requests, etc. However,
      some ensembles and come and gone since I've been in the band and with each new 2-Star MDW
      general (they turn over every 3 years or so and are our direct "leadership") comes new requirements
      to mission processing. Most recently, we have to distinguish between the way we process missions
      that fall outside the National Capitol Region to those from within. This was
      not addressed directly by ServeMuse in this implementation but could easily be handled
      through the evaluation of the location field in a Request objects Event attribute.
  * Reusability
    * I feel the design is abstract and broad enough to be easily adapted to
      * Other military bands 
      * Non-musical military units 
      * Civilian music contractors 
      * Potentially any corporate task delegation system 
  * Sufficiency
    * It effectively accomplishes the following requirements:
      * Creating a Request 
      * Submitting a Request 
      * Delegating a Request 
      * Generating mission details from a Request 
      * Distributing mission details 
      * Updating the status of a Request 
      * Distributing the updated status in the deliverables containing mission details informing subscribing parties of the update
  * Understandability
    * See below for more information
  * Efficiency
    * As noted above this system essentially automates a series of processes (listed under *Sufficiency* above) that are currently manual processes
    * Consequently, this system would improve efficiency by many orders of magnitude by automating these processes
  * High Cohesion/Low Coupling 
    * By focusing on the overlapping design patters noted in the UML Class Diagram High Cohesion and Low Coupling have been achieved to the extent it was possible
  * Reliability
    * More testing is needed however, seems to not fail when used properly at an acceptable rate.
  * Information Hiding 
    * By adhering to the design patterns utilized, modularization is achieved and information hiding is achieved
  * Areas to Improve
      * Security
        * Need to build out defense against bad actors 
      * Robustness
        * Although it is designed to be flexible generally, it is additionally designed to be interacted with in a certain way
          * This could be improved by developing out the capacity to handle different types of inputs
            * Essentially the only input to the system is a Google Calendar Event by design. 
            * An easy way to build out Robustness would be to create a system agnostic Calendar API with the ability to interact with any Calendar API 

* How is the flexibility, of your implementation, e.g., how you add or remove in future new
types?
  * The Abstract Factory Pattern implemented is essentially designed to be able to support easy extension of the product and creator abstract classes.
  * The Observer Pattern implemented easily supports adding Subscriber base classes 
    * In our case here we just need to be mindful that these new Subscriber base classes are folded into our Abstract Factory creator and producer classes
  * The Delegation Pattern implemented also easily supports implementation of more concrete delegators as needed.
  * Lastly, here the State Pattern implementation easily supports through implementing the Status Interface as many states as needed if more are needed. 
* How is the simplicity and understandability of your implementation?
  * Understandability
    * It would be quite easy to explain and relate what the system is doing to a non-technical supervisor
        * The system reflects quite well "the way things are" at the office, how this process works in the real world
            * It is also clear how the automation of these processes improves the efficiency by many orders of magnitud
* How you avoided duplicated code?
  * By adhering to the above-mentioned design patterns quite strictly.
    * The producers package is an example where only Singletons are utilized and on the surface there may appear to be code duplicated
    * I have attempted to show that this is not the case in the BluesProducer, ChorusProducer, and DownrangeProducer classes finJObInCalendar methods all have deceivingly similar signatures however these methods are implemented with slightly varying algorithms to justify show why they are needed as distinct classes. 
You should use design patterns in your project. You can use a combinations of
you have used any design patterns, describe which design pattern you have applied and why.
Write your description in a README.md file, use MarkDown format https://spec.
commonmark.org/current/ and add the README.md file to the root folder of your project


# How to compile the project

We use Apache Maven to compile and run this project. 

You need to install Apache Maven (https://maven.apache.org/)  on your system. 

Type on the command line: 

```bash
mvn clean compile
```

# How to create a binary runnable package 


```bash
mvn clean compile assembly:single
```


# How to run

```bash
mvn -q clean compile exec:java -Dexec.executable="edu.bu.met.cs665.Main" -Dlog4j.configuration="file:log4j.properties"
```

# Run all the unit test classes.


```bash
mvn clean compile test checkstyle:check  spotbugs:check
```

# Using Spotbugs to find bugs in your project 

To see bug detail using the Findbugs GUI, use the following command "mvn findbugs:gui"

Or you can create a XML report by using  


```bash
mvn spotbugs:gui 
```

or 


```bash
mvn spotbugs:spotbugs
```


```bash
mvn spotbugs:check 
```

check goal runs analysis like spotbugs goal, and make the build failed if it found any bugs. 


For more info see 
https://spotbugs.readthedocs.io/en/latest/maven.html


SpotBugs https://spotbugs.github.io/ is the spiritual successor of FindBugs.


# Run Checkstyle 

CheckStyle code styling configuration files are in config/ directory. Maven checkstyle plugin is set to use google code style. 
You can change it to other styles like sun checkstyle. 

To analyze this example using CheckStyle run 

```bash
mvn checkstyle:check
```

This will generate a report in XML format


```bash
target/checkstyle-checker.xml
target/checkstyle-result.xml
```

and the following command will generate a report in HTML format that you can open it using a Web browser. 

```bash
mvn checkstyle:checkstyle
```

```bash
target/site/checkstyle.html
```




