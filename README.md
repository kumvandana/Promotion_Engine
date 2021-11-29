# Promotion_Engine
#How to run the project :
    i) mvn clean package
    ii) java -jar target\Promotion_Engine2-1.0.0-SNAPSHOT.jar
#Input the item :
    Pls follow the instructions to input items in correct format:
    a) Input the various kind of items needed to be added (like [A,B,C]), then 3
    b) Add the item with quatity : like 5A etc
#Cost of every item : 
    The cost of every item is mentioned in application.properties
#Promotions :
    The promotions are added in a proper json format (developer code specific). The json is parsed and a 
    collection is created (specific to the promotion model) with priority.
    The Promotions abstract class can be extended by upcoming new promotions in future and the overridden methods need 
    to be implemented.
    Only one promotion is applied to each item, on the basis of priority (configurable)
#Attached is a sample_log.txt for an overview of working of the project