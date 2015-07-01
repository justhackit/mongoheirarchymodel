package com.aj.mongotreemodel;

public interface IMongoTreeModel<K,V> 
{
	
    public boolean createRoot(Node<K,V> parent);
    public boolean attachChild(Node<K,V> child);
    public boolean detachChild(K child);
    public boolean chopBranch(K nodeKey);
    
}