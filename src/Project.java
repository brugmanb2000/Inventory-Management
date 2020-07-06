import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class Project {

	// Instance Variables
	static Connection connection = null;
	static String MYSQL_DATABASE = "sql3352814";
	static int MYSQL_PORT = 3306;
	static String MYSQL_HOST = "jdbc:mysql://sql3.freemysqlhosting.net:" + MYSQL_PORT + "/" + MYSQL_DATABASE; 
	static String MYSQL_USERNAME = "sql3352814";
	static String MYSQL_PASSWORD = "n8GBTA7tis";


	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		// Get connection to SQL
		try {

			connection = DriverManager.getConnection(MYSQL_HOST, MYSQL_USERNAME, MYSQL_PASSWORD); 
			Class.forName("com.mysql.jdbc.Driver");
			if (args.length == 0 || args.length > 4) { // If too few/many args or Usage si called, display Usage text 
				System.out.println("Invalid amount of args. Type '/?' for usage.");

			} else if ( args[0].equals("/?")) {
				Usage();



				/* Completed */

				// Go through Create Item Statement
			} else if (args[0].equals("CreateItem")) {
				String itemCode = null;
				String itemDescription;
				Double price;
				String sql = null;

				// Parse Arguments into more readable code
				if (args.length == 4) {
					itemCode = args[1];
					itemDescription = args[2];
					price = Double.parseDouble(args[3]);
					sql = "Insert into Item (itemCode, ItemDescription, Price) Values ('" + itemCode + "', '" + itemDescription + "', " + price + ");";
				} 

				else if (args.length == 3)
				{ 
					itemCode = args[1];
					price = Double.parseDouble(args[2]);
					sql = "Insert into Item (itemCode, Price) Values ('" + itemCode + "', '" + price + "');";
				} 

				else if (args.length == 2) {
					itemCode = args[1];
					sql = "Insert into Item (itemCode) Values ('" + itemCode + "');";
				}

				PreparedStatement ps = connection.prepareStatement(sql);
				int result = ps.executeUpdate();

				// Check if command passed/failed
				if (result != 0) {
					System.out.println(itemCode + " has been successfully inserted.");
				} else {
					System.out.println("Error occured. " + itemCode + " was not inserted.");
				}




				/* Completed */


				// Go through Create Purchase Statement
			} else if (args[0].equals("CreatePurchase")) {

				// Parse Arguments into more readable code
				String itemCode = args[1];
				int purchaseQuantity = Integer.parseInt(args[2]);
				

				// Parse Items as SQL command
				PreparedStatement ps = connection.prepareStatement("INSERT into Purchase(ItemID, Quantity) Values ((SELECT ID from Item where ItemCode = '" + itemCode + "'), " + purchaseQuantity + ");");

				int result = ps.executeUpdate();

				// Check if command passed/failed
				if (result != 0) {
					System.out.println("Purchase has been inserted.");
				} else {
					System.out.println("Error occured. Purchase was not inserted.");
				}



				/* Completed */	


				// Create Shipment
			} else if (args[0].equals("CreateShipment")) {

				// Parse Arguments into more readable code
				String itemCode;
				int shipmentQuantity;
				String shipmentDate;
				PreparedStatement ps = null;
				
				
				if (args.length == 4) {
				itemCode = args[1];
				shipmentQuantity = Integer.parseInt(args[2]);
				shipmentDate = args[3];
				ps = connection.prepareStatement("INSERT into Shipment(ItemID, Quantity, ShipmentDate) Values ((SELECT ID from Item where ItemCode = '" + itemCode + "'), " + shipmentQuantity + ", '" + shipmentDate + "');");

				}
				
				else if (args.length == 3) {
				itemCode = args[1];
				shipmentQuantity = Integer.parseInt(args[2]);
				ps = connection.prepareStatement("INSERT into Shipment(ItemID, Quantity) Values ((SELECT ID from Item where ItemCode = '" + itemCode + "'), " + shipmentQuantity + ");");

				}

				// Parse Items as SQL command
				
				int result = ps.executeUpdate();

				// Check if command passed/failed
				if (result != 0) {
					System.out.println("Shipment has been inserted.");
				} else {
					System.out.println("Error occured. Shipment was not inserted.");
				}





				/* Completed */ 


				// Go through Get Items Statement
			} else if (args[0].equals("GetItems")) {
				String itemCode = args[1];

				// Check if Item Code was %
				if (itemCode.equals("%")) {
					itemCode = "*";
				}

				// Parse Items as SQL Command
				PreparedStatement ps = connection.prepareStatement("SELECT " + itemCode + " from Item;", Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = ps.executeQuery();

				System.out.println("Query Results:");
				System.out.println("--------------");


				// Print all
				if (itemCode.equals("*")) {
					while (rs.next()) {
						int id = rs.getInt("ID");
						itemCode = rs.getString("ItemCode");
						String itemDescription = rs.getString("ItemDescription");
						Double price = rs.getDouble("Price");
						System.out.format("%s, %s, %s, %s\n", id, itemCode, itemDescription, price);
					}
				}

				// Print just prices
				else if (itemCode.equals("Price")) {
					while (rs.next()) {
						Double price = rs.getDouble("Price");
						System.out.println(price);
					}
				}

				// Print just item descriptions
				else if (itemCode.equals("ItemDescription")) {
					while (rs.next()) {
						String itemDescription = rs.getString("ItemDescription");
						System.out.println(itemDescription);
					}
				}

				// Print just the IDs
				else if (itemCode.equals("ID")) {
					while (rs.next()) {
						int id = rs.getInt("ID");
						System.out.println(id);
					}
				}
				// Print just the IDs
				else if (itemCode.equals("ItemCode")) {
					while (rs.next()) {
						itemCode = rs.getString("ItemCode");
						System.out.println(itemCode);
					}
				} else {
					System.out.println("Check spelling (and capitol letters as well) if nothing displays from query results.");
				}














				/* Complete */


				// Go through Get Shipments Statement
			} else if (args[0].equals("GetShipments")) {
				String itemCode = args[1];
				String sql = ("Select ID, ItemID, Quantity, ShipmentDate from Shipment where ItemID = (SELECT ID from Item where ItemCode = '" + itemCode + "');");
						
				// Check if Item Code was %
				if (itemCode.equals("%")) {
					sql = ("Select * from Shipment;");		
					}
				

				// Parse Items as SQL Command
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				ResultSet rs = ps.executeQuery();

				System.out.println("Query Results:");
				System.out.println("--------------");
				
				while (rs.next()) {
					int id = rs.getInt("ID");
					int itemID = rs.getInt("ItemID");
					int quantity = rs.getInt("Quantity");
					Date shipmentDate = rs.getDate("ShipmentDate");
					Time shipmentTime = rs.getTime("ShipmentDate");
					System.out.format("%s, %s, %s, %s\n", id, itemID, quantity, shipmentDate + " " + shipmentTime);
				}





				
				
				/* Complete */
				

				// Go through GetPurchases 
			} else if (args[0].equals("GetPurchases")) {
				String itemCode = args[1];
				String sql = ("Select ID, ItemID, Quantity, PurchaseDate from Purchase where ItemID = (SELECT ID from Item where ItemCode = '" + itemCode + "');");
						
				// Check if Item Code was %
				if (itemCode.equals("%")) {
					sql = ("Select * from Purchase;");		
					}
				

				// Parse Items as SQL Command
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				ResultSet rs = ps.executeQuery();

				System.out.println("Query Results:");
				System.out.println("--------------");
				
				while (rs.next()) {
					int id = rs.getInt("ID");
					int itemID = rs.getInt("ItemID");
					int quantity = rs.getInt("Quantity");
					Date purchaseDate = rs.getDate("PurchaseDate");
					Time purchaseTime = rs.getTime("PurchaseDate");
					System.out.format("%s, %s, %s, %s\n", id, itemID, quantity, purchaseDate + " " + purchaseTime);
				}


				// Go through ItemsAvailable
			} else if (args[0].equals("ItemsAvailable")) {
				String itemCode = args[1];
				String sql = ("Select ID, ItemCode, ItemDescription, (Select IFNULL(sum(Quantity),0) from Purchase where ItemID = (Select ID from Item Where ItemCode = '" + itemCode + "')) as PurchaseQuantity, (Select IFNULL(sum(Quantity),0) from Shipment where ItemID = (Select ID from Item where ItemCode = '" + itemCode + "')) as ShipmentQuantity, sum((Select IFNULL(sum(Quantity),0) from Purchase where ItemID = (Select ID from Item where ItemCode = '" + itemCode + "'))  - (Select IFNULL(sum(Quantity),0) from Shipment where ItemID = (Select ID from Item where ItemCode = '" + itemCode + "'))) as TotalAvailableItems from Item where ItemCode = '" + itemCode + "';"); 

				// Check if Item Code was %
				if (itemCode.equals("%")) {
					itemCode = "*";
					sql = ("Select Item.ID, ItemCode, ItemDescription, (Select IFNULL(sum(Quantity), 0) from Purchase where Item.ID = Purchase.ItemID) as PurchaseQuantity, (Select IFNULL(sum(Quantity), 0) from Shipment where Item.ID = Shipment.ItemID) as ShipmentQuantity,sum((Select IFNULL (sum(Quantity), 0) from Purchase where Item.ID = Purchase.ItemID) - (Select IFNULL(sum(Quantity), 0) from Shipment where Item.ID = Shipment.ItemID)) as TotalAvailableItems from Item Group By ID");
				}

				System.out.println("Query Results:");
				System.out.println("ID, ItemCode, Purchase Quantity, Shipment Quantity, Total Available Items");
				System.out.println("--------------");
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();

				while(rs.next()){
					int id = rs.getInt("ID");
					itemCode = rs.getString("ItemCode");
					int purchaseQuantity = rs.getInt("PurchaseQuantity");
					int shipmentQuantity = rs.getInt("ShipmentQuantity");
					int totalAvailableItems = rs.getInt("TotalAvailableItems");
					System.out.format("%s, %s, %s, %s, %s\n", id, itemCode, purchaseQuantity, shipmentQuantity, totalAvailableItems);
				}



				/* Completed*/

			} else if (args[0].equals("UpdateItem")) {
				String itemCode = args[1];
				Double price = Double.parseDouble(args[2]);
				String sql = "UPDATE Item Set Price = " + price + " Where ItemCode = '" + itemCode + "';";
				// Parse Items as SQL Command
				
				PreparedStatement ps = connection.prepareStatement(sql);
				int result = ps.executeUpdate();


				// Check if command passed/failed
				if (result != 0) {
					System.out.println(itemCode + "'s price updated to " + price);
				} else {
					System.out.println("Error occured. Could not update item code.");
				}



				/* Complete */

				// Delete Item
			} else if (args[0].equals("DeleteItem")) {
				String itemCode = args[1];
				String sql = "Delete from Item where ItemCode = '" + itemCode + "';";

				PreparedStatement ps = connection.prepareStatement(sql);
				int result = ps.executeUpdate();


				// Check if command passed/failed
				if (result != 0) {
					System.out.println(itemCode + " has been deleted.");
				} else {
					System.out.println("Error occured. Could not update item code.");
				}



				/* Completed*/

				// Delete Shipment
			} else if (args[0].equals("DeleteShipment")) {
				String itemCode = args[1];

				String sql = ("DELETE FROM Shipment WHERE itemID = (Select ID from Item where ItemCode = '" + itemCode + "') order by ShipmentDate DESC LIMIT 1;");

				// Parse Items as SQL Command
				
				PreparedStatement ps = connection.prepareStatement(sql);

				int result = ps.executeUpdate();

				// Check if command passed/failed
				if (result != 0) {
					System.out.println("Deleted most recent shipment for " + itemCode);
				} else {
					System.out.println(ps.executeUpdate()); // If result fails, print out the error
				}

				
				
				
				
				
				/* Completed*/

				// Delete Purchase	
			} else if (args[0].equals("DeletePurchase")) {
				String itemCode = args[1];
				String sql = ("DELETE FROM Purchase WHERE itemID = (Select ID from Item where ItemCode = '" + itemCode + "') order by PurchaseDate DESC LIMIT 1;");

				// Parse Items as SQL Command
				
				PreparedStatement ps = connection.prepareStatement(sql);

				int result = ps.executeUpdate();


				// Check if command passed/failed
				if (result != 0) {
					System.out.println("Deleted most recent purchase for " + itemCode);
				} else {
					System.out.println(ps.executeUpdate()); // If result fails, print out the error
				}



				// If format wasn't correct
			} else {
				System.out.println("Incorrect arguments. Check usage and try again.");
			}

		} catch (SQLException sql) {
			System.out.println(sql.getLocalizedMessage());
			System.out.println("Error within MySQL. Please try again.");
			//sql.printStackTrace();

			// Catch non-SQL error	
		} catch (Exception e) {
			System.out.println("Error occured. Please try again.");
			e.printStackTrace();
		}


	}



	static void Usage() {
		System.out.println("Usage of Program: ");
		System.out.println("- /?: Displays Usage");
		System.out.println("- CreateItem <Item Code> <Item Description> <Purchase Quantity>: Creates Item");
		System.out.println("- CreatePurchase <Item Code> <Purchase Quantity>: Creates Purchase Record");
		System.out.println("- CreateShipment <Item Code> <Shipment Quantity> <Shipment Date: Creates a Shipment Record");
		System.out.println("- GetItems <Item Code>: Get Item Code (Note: Use '%' to view all items)");
		System.out.println("- GetShipments <Item Code>: Returns all shipments associated with item code (Note: Use '%' to view all items)");
		System.out.println("- GetPurchases <Item Code>: Returns all purchases associated with item code (Note: Use '%' to view all items)");
		System.out.println("- ItemsAvailable <Item Code>: Returns calculation for items (Shipments - Purchases)");
		System.out.println("- UpdateItem <Item Code> <Price>: Updates price of item");
		System.out.println("- DeleteItem <Item Code>: Deletes item.");
		System.out.println("- DeleteShipment <Item Code>: Removes most recent shipment for item code if it exists");
		System.out.println("- DeletePurchase <Item Code>: Removes most recent purchase for item code if it exists");
	}
}
