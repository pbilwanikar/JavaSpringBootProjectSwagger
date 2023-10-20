package com.example.joblisting.repository;

import com.example.joblisting.model.Post;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import com.mongodb.client.AggregateIterable;
import java.util.Arrays;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchRepositoryImpl implements SearchRepository{

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoConverter mongoConverter;

    @Override
    public List<Post> findByText(String text){
        final List<Post> posts = new ArrayList<>();
        MongoDatabase database = mongoClient.getDatabase("joblistingPranav");
        MongoCollection<Document> collection = database.getCollection("JobPost");
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search",
                        new Document("index", "default").append("text", new Document("query", text).append("path", Arrays.asList("desc", "profile", "techs")))),
                new Document("$sort", new Document("exp", 1L)), new Document("$limit", 5L)));

        result.forEach(doc -> posts.add(mongoConverter.read(Post.class, doc)));


        return posts;
    }
}
