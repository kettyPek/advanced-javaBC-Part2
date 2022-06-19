package ajbc.learn.mongodb.crud;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import ajbc.utils.MyConnectionString;

public class Basics {

	public static void main(String[] args) {

		ConnectionString connectionString = MyConnectionString.uri();
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
		
		try(MongoClient mongoClient = MongoClients.create(settings);){
			
			// print databases
			mongoClient.listDatabaseNames().forEach(System.out::println);
			
			// create new DB
			final String dbName = "my_own_db";
			MongoDatabase db = mongoClient.getDatabase(dbName);
			
			System.out.println("------After DB creation:------");
			mongoClient.listDatabaseNames().forEach(System.out::println);
			
			// create collection
			final String collection = "myCollection";
			MongoCollection<Document> myCollection = db.getCollection(collection);
			
			System.out.println("------After creating collection:------");
			mongoClient.listDatabaseNames().forEach(System.out::println);
			
			Document studentDoc = createStudentDoc(1,2,"ketty","pekarsky");
			
			InsertOneResult inserResult = myCollection.insertOne(studentDoc);
			System.out.println(inserResult.wasAcknowledged());
			
			System.out.println("------After creating Document:------");
			mongoClient.listDatabaseNames().forEach(System.out::println);
		}
		

	}

	public static Document createStudentDoc(int studentID, int classID, String firstName, String lastName) {
		
		Document studentDoc = new Document("student_id",studentID).append("calss_id", classID).append("firsu_name", firstName).append("last_name", lastName);
		List<Document> grades = new ArrayList<Document>();
		grades.add(new Document("topic","java").append("grade", 99));
		grades.add(new Document("topic","SQL").append("grade", 100));
		grades.add(new Document("topic","NOSQL").append("grade", 98));
		
		studentDoc.append("exams", grades);
		
		return studentDoc;
	}
}
