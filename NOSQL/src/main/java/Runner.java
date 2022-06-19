import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Runner {

	public static void main(String[] args) {

		ConnectionString connectionString = new ConnectionString(
				"mongodb+srv://ketty:050296@myfirstcluster.t75t8.mongodb.net/?retryWrites=true&w=majority");
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
		try(MongoClient mongoClient = MongoClients.create(settings);){
			MongoDatabase database = mongoClient.getDatabase("sample_mflix");
			long numOfDocuments = database.getCollection("movies").countDocuments();
			System.out.println("num of docs in movies = " + numOfDocuments);
		}

	}

}
