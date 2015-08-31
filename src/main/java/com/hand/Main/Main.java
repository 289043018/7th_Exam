package com.hand.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.hand.POJO.Address;
import com.hand.POJO.Customer;
import com.hand.interceptor.MyInterceptor;

public class Main {
	private static SessionFactory factory;
	 private DataSource dataSource;
	 private JdbcTemplate jdbcTemplateObject;
	 private PlatformTransactionManager transactionManager;
	public static void main(String[] args) {
		ApplicationContext context =  new FileSystemXmlApplicationContext("ApplicationContext.xml");
		try {
			 factory = new AnnotationConfiguration().configure().addPackage("com.hand.POJO")
	    			  .addAnnotatedClass(Customer.class).buildSessionFactory();
		} catch (Throwable ex) {
			 System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
		}
		int store_id = 1;
		String first_name;
		String last_name;
		String email;
		int address_id = 0;
		String creat_date;
		
		Main ME = new Main();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入first_name");
		first_name = sc.nextLine();
		
		System.out.println("请输入last_name");
		last_name = sc.nextLine();
		
		System.out.println("请输入email");
		email = sc.nextLine();
		

		System.out.println("请输入address_id");
		Scanner sc_address_id = new Scanner(System.in);
		address_id = sc_address_id.nextInt();
		if(ME.selectAddress(address_id) == false){
			for (;ME.selectAddress(address_id) == false; ) {
				System.out.println("输入的address_id错误，请再次输入：");
				address_id = sc_address_id.nextInt();
			}
		}
//		address_id = ME.getaddress_id(ME);
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		creat_date=format.format(date);

		ME.addCustomer(first_name, last_name, email, address_id, creat_date, store_id);
		ME.selectCustomer(first_name);
		
		System.out.println("请输入要删除的Customer的ID：");
		Scanner sc_customer_id = new Scanner(System.in);
		int customer_id = sc_customer_id.nextInt();
		ME.deleteEmployee(customer_id);
		
	}
	
	public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	   }
	 public void setTransactionManager(
		      PlatformTransactionManager transactionManager) {
		      this.transactionManager = transactionManager;
		   }
//  实现增加数据
  public Integer addCustomer(String fname, String lname,String email,int address_id,String create_date,int store_id){
     Session session = factory.openSession(new MyInterceptor());
     Transaction tx = null;
     Integer customerID = null;
     try{
        tx = session.beginTransaction();
        Customer customer = new Customer();
        customer.setFirst_name(fname);
        customer.setLast_name(lname);
        customer.setEmail(email);
        customer.setAddress_id(address_id);
        customer.setCreate_date(create_date);
        customer.setStore_id(store_id);
        customerID = (Integer) session.save(customer);
        tx.commit();
     }catch (HibernateException e) {
        if (tx!=null) tx.rollback();
        e.printStackTrace(); 
     }finally {
        session.close(); 
     }
     return customerID;
  }
//  实现读取数据
  public void selectCustomer(String first_name ){
     Session session = factory.openSession(new MyInterceptor());
     Transaction tx = null;
     try{
        tx = session.beginTransaction();
        
        String sql = "SELECT * FROM Customer WHERE first_name = :first_name";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(Customer.class);
        query.setParameter("first_name", first_name);
        List customers = query.list();  
        for (Iterator iterator = 
        		customers.iterator(); iterator.hasNext();){
        	Customer customer = (Customer) iterator.next(); 
        	System.out.println("已经保存如下数据：");
        	System.out.println("ID: " + customer.getCustomer_id()); 
        	System.out.println("First Name: " + customer.getFirst_name()); 
        	System.out.println("Last Name: " + customer.getLast_name()); 
        	System.out.println("Email: " + customer.getEmail());
        	System.out.println("Address: " + customer.getAddress_id());
        }
        tx.commit();
     }catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      }finally {
        session.close(); 
     }
  }
//实现读取数据
public boolean selectAddress(int address_id ){
	boolean flag = false ;
   Session session = factory.openSession(new MyInterceptor());
   Transaction tx = null;
   try{
      tx = session.beginTransaction();
      
      String sql = "SELECT * FROM address WHERE address_id = :address_id";
      SQLQuery query = session.createSQLQuery(sql);
      query.addEntity(Address.class);
      query.setParameter("address_id", address_id);
      List customers = query.list();  
      
      Iterator iterator = customers.iterator();

    	if(iterator.hasNext()){
    		flag = true;
    	}else{
    		flag = false;
    	}
      tx.commit();
   }catch (HibernateException e) {
       if (tx!=null) tx.rollback();
       e.printStackTrace(); 
    }finally {
      session.close(); 
   }
return flag;
}
  
//实现删除数据
public void deleteEmployee(int customer_id){
   Session session = factory.openSession(new MyInterceptor());
   Transaction tx = null;
   try{
      tx = session.beginTransaction();
      Customer customer = 
                (Customer)session.get(Customer.class, customer_id); 
      session.delete(customer);
      System.out.println("你输入的ID为"+customer_id+"的Customer已经 删除");
      tx.commit();
   }catch (HibernateException e) {
      if (tx!=null) tx.rollback();
      e.printStackTrace(); 
   }finally {
      session.close(); 
   }
}
}
