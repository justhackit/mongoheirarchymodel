package com.aj.mongotreemodel;

public class Node<K,V> {
	private K id;
	private K kinKey;
	private V value;
	
	public Node(K id,K key,V value){
		this.id = id;
		this.kinKey=key;
		this.value = value;
	}
	
	public K getId() {
		return id;
	}
	public void setId(K id) {
		this.id = id;
	}
	public K getKinKey() {
		return kinKey;
	}
	public void setKinKey(K kinKey) {
		this.kinKey = kinKey;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Node [id=" + id + ", kinKey=" + kinKey + ", value=" + value
				+ "]";
	}
}
