package ajbc.learn.mongodb.crud;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
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

import ajbc.learn.mongodb.models.Student;
import ajbc.utils.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class AggregationExample {
	
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
					MongoDatabase myDb = mongoClient.getDatabase("sample_training");
					MongoCollection<Document> zips = myDb.getCollection("zips");
					
					
				}
	}

}
