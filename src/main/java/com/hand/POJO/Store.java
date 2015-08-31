package com.hand.POJO;



public class Store {

	private int store_id;

	private int manager_stadd_id;

	private int address_id;

	 public Store() {  
	    }  
	    public Store(int store_id,int manager_stadd_id,int address_id) {  
	        this.store_id = store_id; 
	        this.manager_stadd_id = manager_stadd_id;  
	        this.address_id = address_id;  
	    }  
	
	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public int getManager_stadd_id() {
		return manager_stadd_id;
	}

	public void setManager_stadd_id(int manager_stadd_id) {
		this.manager_stadd_id = manager_stadd_id;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}


	
}
