# Inventory Management
## NOTE: Database is no longer live

# Author
### Brandon Brugman for CS 310

## Description

With this project, we create a basic inventory tracking inventory. We can create items to add into the database, purchase items, ship items, and then keep track of your total items. 

## Installation

To use this project, this will be very simple! For the Java side of the project, just download the SRC file (which contains just the Project.java file). If using a IDE, just load the project and compile! If using the command line, this will also be simple. All that needs to be compiled is by these steps:

1) Navigate to folder containing Project.Java
2) In the command line, type javac Project.java

Now that the project is compiled, check below for usage!

## Usage

When first starting the system, you can use "java Project /?" to view usage as well, but I will go over the other other usages as well. 

### Creating Items

To use the database once it is compiled, we will preface each command with: "java Project"

For example, java Project CreateItem LargeSoda 2.00

This will add a new item in the database named "LargeSoda" (note: keep it as one continous string of words as the system will recognize LargeSoda as an item, but Large Soda will not work). This item will also be given a price of $2.00. 

This format can be used as well for CreateShipment and CreatePurchase, with a few minor differences. 

java Project CreateItem <itemCode> <itemDescription> <price>
  a. Creates an Item

java Project CreatePurchase <itemCode> <PurchaseQuantity>
  a. Create a Purchase Record
  
java Project CreateShipment <itemCode> <ShipmentQuantity> <shipmentDate>
  a. Create a Shipment Record
  
### Deleting Items

To remove an item, for example, we would type something along the lines of this: java Project DeleteItem BSoda

If we have an item named BSoda, this will remove this item. 

Removing a Purchase or Shipment record will only remove the most recent one, so if you have four purchase records for a BSoda for example, it will only remove the most recent one. 

java Project DeleteItem <itemCode>
a. Removes only the item exactly matching the parameter (errors are expected if shipments
or purchases are referencing the item.)

java Project DeleteShipment <itemCode>
a. Removes only the most recent shipment for one item code, if a shipment exists.
  
java Project DeletePurchase <itemCode>
a. Removes only the most recent purchase for one item code, if a purchase exists.
  
### Viewing Database

If needing to view your database (or aspects of the database), you can do view it by using the below commands. For example, 
if wanting to see all your items, use "java Project GetItems %". Note the "%" as this means we are viewing ALL the items versus a specific item. 

If you want to see a purchase record for just BSodas for example, we could do "java Project GetPurchases BSoda". This will print all purchase orders of BSoda to the console. 

java Project GetItems <itemCode>
  a. Returns information about that item
  b. Using % returns ALL items

java Project GetShipments <itemCode>
  a. Returns all shipments associated with the itemcode
  b. Using % instead of an ItemCode returns all shipments

6. java Project GetPurchases <itemCode>
  a. Returns all purchases associated with the itemcode
  b. Using % instead of an ItemCode returns all purchases.
  
 ### Miscellaneous
 
 Other ones options you can use involve updating the price with "java Project UpdateItem Price". Using this, you can update the price of your items. 
 
 One of the cooler things you can do is select "ItemsAvailable" and it will give you a breakdown of an Item's information, but also read the total purchases items, total shipped items, and what your total items remaining are (by taking Purchases - Shipped). 
 
 7. java Project ItemsAvailable <itemCode>
  a. Returns a calculation for items. Simply stated, it will return, per item requested: all
shipment quantities minus all purchase quantities.
  b. Allow % to be used for a request, this will return all Items

8. java Project UpdateItem <itemCode> <price>
  a. Changes the price for the itemItemCode
