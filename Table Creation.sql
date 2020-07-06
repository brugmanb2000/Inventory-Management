use cs310brugman;

create table Item (
ID int auto_increment,
ItemCode varchar(10) unique NOT NULL,
ItemDescription varchar(50),
Price decimal(4,2) default 0,
PRIMARY KEY (ID));

create table Purchase (
ID int auto_increment,
ItemID int,
Quantity int,
PurchaseDate timestamp DEFAULT current_timestamp(),
Foreign Key (ItemID) References Item(ID),
PRIMARY KEY (ID));

create table Shipment (
ID int auto_increment,
ItemID int,
Quantity int NOT NULL,
ShipmentDate datetime unique NOT NULL,
foreign key (ItemID) REFERENCES Item(ID),
PRIMARY KEY (ID));