package com.hand.interceptor;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.hand.POJO.Customer;

public class MyInterceptor extends EmptyInterceptor {
	private int updates;
	private int creates;
	private int loads;
	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		// TODO Auto-generated method stub
		System.out.println("deleted operation");
	}
	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		  if ( entity instanceof Customer ) {
	          System.out.println("Update Operation");
	          return true; 
	       }
	       return false;
	}
	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		return true;
	}
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		System.out.println("Before Save");
		return super.onSave(entity, id, state, propertyNames, types);
	}
	@Override
	public void postFlush(Iterator entities) {
		
	}
	@Override
	public void preFlush(Iterator entities) {
		 
	}
	
	
}
