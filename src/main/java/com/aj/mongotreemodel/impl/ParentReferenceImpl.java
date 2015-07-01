package com.aj.mongotreemodel.impl;

import java.net.UnknownHostException;

import com.aj.mongotreemodel.IMongoTreeModel;
import com.aj.mongotreemodel.Node;
import com.aj.mongotreemodel.dao.TheDatabase;
import com.aj.mongotreemodel.excpn.MongoTreeModelException;

public class ParentReferenceImpl<K,V> implements IMongoTreeModel<K,V>{
	
	private TheDatabase db ;
	
	public ParentReferenceImpl() throws MongoTreeModelException{
		try {
			db = new TheDatabase("localhost:27017", "treetest","mytree", "", "",this);
		} catch (NumberFormatException e) {
			throw new MongoTreeModelException("Invalid port number");
		} catch (UnknownHostException e) {
			throw new MongoTreeModelException("Unable to connect to database");
		}
	}
	
	public boolean createRoot(Node<K,V> parent) {
		boolean status=false;
		try {
			status = db.createRoot(parent);
		} catch (MongoTreeModelException e) {
			status = false;
		}
		return status;
	}

	public boolean attachChild(Node child) {
		return db.attachChild(child);
	}

	public boolean detachChild(Object child) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean chopBranch(Object nodeKey) {
		// TODO Auto-generated method stub
		return false;
	}

}