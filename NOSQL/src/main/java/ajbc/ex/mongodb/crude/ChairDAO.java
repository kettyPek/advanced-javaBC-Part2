package ajbc.ex.mongodb.crude;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.model.UpdateOptions;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import ajbc.ex.mongodb.models.Chair;

public class ChairDAO {

	Gson gson = new Gson();
	
	private MongoCollection<Document> chairsCollection;

	public ChairDAO(MongoCollection<Document> chairsCollection) {
		this.chairsCollection = chairsCollection;
	}

	public void insertChairToDB(Chair chair) {
		String chairJson = gson.toJson(chair);
		chairsCollection.insertOne(Document.parse(chairJson));
		System.out.println("Chair was inserted to DB");
		
	}

	public void insertListOfChairToDB(List<Chair> chairs) {
		List<Document> chairsDocs = new ArrayList<Document>();
		String chairJson;
		for(Chair chair : chairs) {
			chairJson = gson.toJson(chair);
			chairsDocs.add(Document.parse(chairJson));
		}
		chairsCollection.insertMany(chairsDocs);
		System.out.println("The list of chairs was inserted to DB");
	}
	
	public Chair getChairByID(String id) {
		Document chair = chairsCollection.find(eq("_id", new ObjectId(id))).first();
		return gson.fromJson(gson.toJson(chair), Chair.class);
	}
	
	public List<Chair> getAllStools(){
		FindIterable<Document> iterable = chairsCollection.find(eq("isStool",true));
		return turnDocumetToChairsList(iterable);
	}
	
	public List<Chair> getChairByManufacturer(String manufacturer){
		FindIterable<Document> iterable = chairsCollection.find(eq("manufacturer",manufacturer));
		return turnDocumetToChairsList(iterable);
	}
	
	public List<Chair> getChairInPriceRange(int minPrice, int maxPrice){
		FindIterable<Document> iterable = chairsCollection.find(and(gte("price",minPrice),lte("price",maxPrice)));
		return turnDocumetToChairsList(iterable);
	}
	
	public List<Chair> getChairByManufacturersList(List<String> manufacturers){
		FindIterable<Document> iterable = chairsCollection.find(in("manufacturer",manufacturers));
		return turnDocumetToChairsList(iterable);
	}
	
	public List<Chair> getChairUnderHeight(int height){
		FindIterable<Document> iterable = chairsCollection.find(lte("measurment.height", height));
		return turnDocumetToChairsList(iterable);
	}
	
	public Chair updateChair(Chair chair){
        UpdateResult updateResult = chairsCollection.updateOne(eq("_id", chair.getId()),
        		combine(set("manufacturer",chair.getManufacturer()),set()));
	}
	
	private List<Chair> turnDocumetToChairsList(FindIterable<Document> iterable){
		List<Chair> chairs = new ArrayList<Chair>();
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) 
			chairs.add(gson.fromJson(gson.toJson(cursor.next()), Chair.class));
		return chairs;
	}
	
	

}
