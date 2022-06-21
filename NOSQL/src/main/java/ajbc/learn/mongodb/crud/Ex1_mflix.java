package ajbc.learn.mongodb.crud;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.jsr310.LocalDateCodec;
import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.model.Projections;


import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

import ajbc.learn.mongodb.models.Comment;
import ajbc.learn.mongodb.models.Movie;
import ajbc.learn.mongodb.models.Student;
import ajbc.utils.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Ex1_mflix {

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
			MongoDatabase myDb = mongoClient.getDatabase("sample_mflix");
			MongoCollection<Movie> movies = myDb.getCollection("movies", Movie.class);
			MongoCollection<Comment> comments = myDb.getCollection("comments", Comment.class);
			

			// Read
//			Movie movie = movies.find(Filters.eq("_id",new ObjectId("573a1391f29313caabcd8979"))).first();
//			System.out.println("after read\n" + movie);
//
//			List<Comment> commentsList = comments.find(Filters.eq("movie_id",movie.getId())).into(new ArrayList<>());
//			commentsList.forEach(System.out::println);
			
			// aggregation ex
//			MongoCollection<Document> moviesDoc = myDb.getCollection("movies");
//			Bson match = match(eq("type","movie"));
//			Bson group = group("$year",Accumulators.sum("count", 1));
//			Bson sort  = sort(Sorts.descending("count"));
//			Bson limit = limit(10);
//			List<Document> results = moviesDoc.aggregate(Arrays.asList(match,group,sort,limit)).into(new ArrayList<>());
//			results.forEach(doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build())));
			
			// join ex
			MongoCollection<Document> commentsDoc = myDb.getCollection("comments");
			Bson match1 = match(gte("date",LocalDateTime.of(2017, 1, 1, 0, 0)));
			Bson pipeline = lookup("movie", "movie_id", "_id", "movies");
			Bson project = project(Projections.fields(Projections.include("title","text"), Projections.computed("movie_title","$movie.title")));
			List<Document> results2 = commentsDoc.aggregate(Arrays.asList(match1,pipeline,project)).into(new ArrayList<>());
			results2.forEach(doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build())));
			
			
			
		}

	}

}
