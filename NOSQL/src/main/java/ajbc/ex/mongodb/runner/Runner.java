package ajbc.ex.mongodb.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ajbc.ex.mongodb.crude.ChairDAO;
import ajbc.ex.mongodb.models.Chair;
import ajbc.ex.mongodb.models.Measurment;
import ajbc.utils.MyConnectionString;

public class Runner {

	public static void main(String[] args) {

		ConnectionString connectionString = MyConnectionString.uri();
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();

		try (MongoClient mongoClient = MongoClients.create(settings);) {

			// create new DB
			final String DB_NAME = "Furniture"; String COLLECTION = "chairs";
			MongoDatabase db = mongoClient.getDatabase(DB_NAME);

			// create collection
			MongoCollection<Document> chairsCollection = db.getCollection(COLLECTION);
			
			Chair chair = new Chair("IKEA","A-100",true,100,new Measurment(10,10,10));
			List<Chair> chairsList = createChairsList();
			
			ChairDAO chairDao = new ChairDAO(chairsCollection);
			
			// check ChairDAO methods
			// Insert
//			chairDao.insertChairToDB(chair);
//			chairDao.insertListOfChairToDB(chairsList);
			
			// Read 
//			Chair chairByID = chairDao.getChairByID("62aee08e231310338fb5abbd");
//			System.out.println("returned chair: " + chairByID);
			
//			List<Chair> stools = chairDao.getAllStools();
//			System.out.println("All stools:");
//			stools.forEach(System.out::println);
			
//			List<Chair> sameManufacturer = chairDao.getChairByManufacturer("IKEA");
//			System.out.println("All chairs from IKEA:");
//			sameManufacturer.forEach(System.out::println);
			
//			List<Chair> chairsIPriceRange = chairDao.getChairInPriceRange(20, 100);
//			System.out.println("All chairs in the price range ");
//			chairsIPriceRange.forEach(System.out::println);
			
//			List<String> manufacturers = Arrays.asList("IKEA","ACE");
//			List<Chair> chairsByManufacturers = chairDao.getChairByManufacturersList(manufacturers);
//			System.out.println("All chairs by manufacturers list: ");
//			chairsByManufacturers.forEach(System.out::println);
			
			List<Chair> chairsUnderHeight = chairDao.getChairUnderHeight(30);
			System.out.println("All chairs under height : ");
			chairsUnderHeight.forEach(System.out::println);

		}
	}
	
	public static List<Chair> createChairsList(){
		List<Chair> chairsList = new ArrayList<Chair>();
		chairsList.add( new Chair("HOME-CENTER","HM",false,60,new Measurment(4,5,6)));
		chairsList.add( new Chair("ACE","AC34",true,90,new Measurment(9,12,10)));
		chairsList.add( new Chair("BITILI","BT",false,400,new Measurment(30,10,23)));
		
		return chairsList;
	}

}
