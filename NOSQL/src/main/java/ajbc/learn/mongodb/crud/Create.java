package ajbc.learn.mongodb.crud;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;

import ajbc.learn.mongodb.models.Student;
import ajbc.utils.MyConnectionString;

public class Create {
	
	public static void main(String[] args) {
		
		ConnectionString connectionString = MyConnectionString.uri();
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
		
		try(MongoClient mongoClient = MongoClients.create(settings);){
			
			JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
			
			final String DB_NAME = "my_own_db", COLLECTION = "myCollection";
			// create new DB
			MongoDatabase db = mongoClient.getDatabase(DB_NAME);
			
			// create collection
			MongoCollection<Document> myCollection = db.getCollection(COLLECTION);
			
			
			Document studentDoc = Basics.createStudentDoc(1,2,"ketty","pekarsky");
			
			InsertOneResult inserResult = myCollection.insertOne(studentDoc);
			System.out.println(inserResult.wasAcknowledged());
			
			// create a Student object
			List<Exam> exams = new ArrayList<Exam>();
			exams.add(new Exam("java",100));
			exams.add(new Exam("C",99));
			Student student = new Student(1234,5678,"moshe","choen",exams);
			
			// convert to Gson
			Gson gson = new Gson();
			String studentJson = gson.toJson(student);
//			System.out.println(studentJson);
			
			// parse json to document
			Document studentDoc1 = Document.parse(studentJson);
			InsertOneResult inserResult1 = myCollection.insertOne(studentDoc1);
			System.out.println(inserResult1.wasAcknowledged());
			
			List<Document> stuDocs = new ArrayList<Document>();
			stuDocs.add(studentDoc);
			stuDocs.add(studentDoc1);
			
			InsertManyResult manyInserResult = myCollection.insertMany(stuDocs);
			System.out.println(manyInserResult.wasAcknowledged());
		}
	}

}
