package com.aj.mongotreemodel.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.aj.mongotreemodel.Node;
import com.aj.mongotreemodel.excpn.MongoTreeModelException;
import com.aj.mongotreemodel.impl.ParentReferenceImpl;
import com.aj.mongotreemodel.utils.Constants;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.util.JSON;

public class TheDatabase<T> {

	private MongoClient mongoClient;
	private DBCollection collection;
	private Object implemenation;
	Gson gson = new Gson();
	public TheDatabase(String dbHostPort, String dbName,String collection, String userName,
			String password,T impl) throws NumberFormatException, UnknownHostException {
		MongoCredential credential = MongoCredential.createMongoCRCredential(
				userName, dbName, password.toCharArray());
		List<ServerAddress> serverList = new ArrayList<ServerAddress>();
		String serverArray[] = dbHostPort.split(",");
		String serverDtls[] = null;
		for (String serverField : serverArray) {
			serverDtls = serverField.split(":");
			serverList.add(new ServerAddress(serverDtls[0], Integer
					.parseInt(serverDtls[1])));
		}
		this.mongoClient = new MongoClient(serverList, Arrays.asList(credential));
		this.mongoClient = new MongoClient("localhost",27017);
		implemenation = impl;
		this.collection = mongoClient.getDB(dbName).getCollection(collection);
	}
	

	public boolean createRoot(Node rootNode) throws MongoTreeModelException{
		if(rootNode.getKinKey() == null && !collection.find().hasNext()){
			BasicDBObject doc = new BasicDBObject();
			if(rootNode.getValue()!=null){
				doc = (BasicDBObject)JSON.parse(gson.toJson(rootNode.getValue()));
			}
			doc = doc.append(Constants.ID, rootNode.getId());
			collection.insert(doc);
		} else {
			throw new MongoTreeModelException("Root already exists");
		}
		return true;
	}
	
	public boolean attachChild(Node child){
		if(child.getId() == null || child.getKinKey() == null){
			return false;
		}
		BasicDBObject match = new BasicDBObject(Constants.ID, new BasicDBObject("$ne", child.getId())).append(Constants.ID, child.getKinKey());
		if(!collection.find(match).hasNext()){
			return false;
		}
		BasicDBObject doc = new BasicDBObject();
		if(child.getValue()!=null){
			doc = (BasicDBObject)JSON.parse(gson.toJson(child.getValue()));
		}
		if(implemenation instanceof ParentReferenceImpl){
			doc = doc.append(Constants.ID, child.getId()).append(Constants.PARENT_KEY, child.getKinKey());
		}
		collection.insert(doc);
		return true;
	}
	
}
