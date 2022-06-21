package ajbc.learn.mongodb.crud;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.Arrays;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.slf4j.LoggerFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.result.InsertOneResult;

import ajbc.learn.mongodb.models.Student;
import ajbc.utils.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class POJOMapping {

	public static void main(String[] args) {

		// Set logger to Error only
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// prepare codec registry
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		
		ConnectionString connectionString = MyConnectionString.uri();
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).codecRegistry(codecRegistry).build();
		
		try (MongoClient mongoClient = MongoClients.create(settings);) {
			MongoDatabase myDb = mongoClient.getDatabase("my_own_db");
			MongoCollection<Student> stedentsCollection = myDb.getCollection("myCollection", Student.class);
			
			// Create
			List<Exam> exams = Arrays.asList(new Exam("JAVA",99), new Exam("C",89));
			Student student = new Student(444,555,"nikol","pekarsky",exams);
			
			InsertOneResult result= stedentsCollection.insertOne(student);
			System.out.println(result.wasAcknowledged());
			
			// Read
			Student student1 = stedentsCollection.find(Filters.eq("first_name","nikol")).first();
			System.out.println("after read\n"+student1);
			
			// Update
			// remove all exams of student1
			student1.setExams(null);
			FindOneAndReplaceOptions returnNew = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
			Student student2 = stedentsCollection.findOneAndReplace(Filters.eq("_id",student1.getId()), student1,returnNew);
			System.out.println("after update\n"+student2);
			
			// Delete
			Bson filter = Filters.eq("_id",student1.getId());
			stedentsCollection.deleteOne(filter);
			
			
			
		}

	}

}
