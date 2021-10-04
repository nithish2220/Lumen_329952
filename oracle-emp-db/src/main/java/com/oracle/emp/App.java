package com.oracle.emp;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.oracle.dbconnections.DBConnection;
import com.oracle.entity.Employee;
import com.oracle.exception.NameNotFoundException;
import com.oracle.repo.EmployeeRepo;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
    	Logger log = Logger.getRootLogger();
    	log.info("Main Application is Started");
    	Scanner sc = new Scanner(System.in);
    	Connection con = DBConnection.getConnection();
    	EmployeeRepo repo = new EmployeeRepo(con);
//    	repo.createDefaultTable();
//    	repo.addValuesTOTable();
    	System.out.println("************************************");
    	System.out.println("Welcome to Oracle Employee Database");
    	System.out.println("***********************************");
    	while(true) {
    		System.out.println("***********************************");
    		System.out.println("1. All Employees' Data");
    		System.out.println("2. Add Employee");
    		System.out.println("3. Find Employee By Name");
    		System.out.println("4. Edit Employee Details");
    		System.out.println("5. Get List of Employees' based on Birthday");
    		System.out.println("6. Get List of Employees' based on Wedding Anniversary");
    		System.out.println("7. Get All Employees' FirstName and Phone Number");
    		System.out.println("8. Exit");
        	System.out.println("***********************************");
        	
        	int choice = sc.nextInt();
        	log.info("User selected option :="+choice);
        	
        	if(choice==1) {
        		System.out.println("Getting All Employee Details");
        		List<Employee> empList = repo.findAll();
        		System.out.println("ID, FirstName, LastName, Address, EmailAddress, PhoneNumber, Birthday, WeddingAnniversary");
        		empList.forEach(System.out::println);
        		
        	}
        	if(choice==2) {
        		
        		System.out.println("Enter Employee ID:");
        		int employeeId = sc.nextInt();
        		System.out.println("Enter Employee First Name:");
        		String firstName = sc.next();
        		System.out.println("Enter Employee Last Name:");
        		String lastName = sc.next();
        		System.out.println("Enter Employee Address:");
        		String address = sc.next();
        		System.out.println("Enter Employee Email Address:");
        		String email = sc.next();
        		System.out.println("Enter Employee Phone Number:");
        		String phone = sc.next();
        		System.out.println("Enter Employee Birthday: {YYYY-MM-DD}");
        		LocalDate birthday = LocalDate.parse(sc.next());
        		System.out.println("Enter Employee Wedding Anniversary: {YYYY-MM-DD}");
        		LocalDate wedding = LocalDate.parse(sc.next());
        		Employee emp = new Employee(employeeId,firstName,lastName,address,email,phone,birthday,wedding);
        		repo.addEmployee(emp);
        		System.out.println("Employee Added Successully!");
        	}
        	if(choice==3) {
        		System.out.println("Enter the Employee Name");
        		String name = sc.next();
        		System.out.println("Retriving the Data...");
        		List<Employee> findByName;
				try {
					findByName = repo.findByName(name);
					if(findByName==null || findByName.isEmpty())
						throw new NameNotFoundException("No Name Found in Database with Name "+name);
					else {
						System.out.println("ID, FirstName, LastName, Address, EmailAddress, PhoneNumber, Birthday, WeddingAnniversary");
						findByName.forEach(System.out::println);
					}
					
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
				Thread t1 = new Thread();
        			try {
						t1.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        	}
        	if(choice==4) {
        		System.out.println("Please enter the Employee ID to Update:");
        		int employeeId = sc.nextInt();
        		Employee emp  = repo.findById(employeeId);
        		System.out.println("If you want to update any details, type the update value respectively");
        		System.out.println("If Yes, enter Details, If No Just press 'N' for every row");
        		System.out.println("Enter FirstName to Update:");
        		String fname = sc.next();
        		if(fname.equalsIgnoreCase("N") || fname.equalsIgnoreCase("No"))
        			fname=emp.getFirstName();
        		else
        		emp.setFirstName(fname);
        		
        		System.out.println("Enter LastName to Update:");
        		String lastname = sc.next();
        		if(lastname.equalsIgnoreCase("N") || lastname.equalsIgnoreCase("No"))
        			lastname=emp.getLastName();
        		else
        		emp.setLastName(lastname);
        		
        		System.out.println("Enter Address to Update:");
        		String address = sc.next();
        		if(address.equalsIgnoreCase("N") || address.equalsIgnoreCase("No"))
        			address=emp.getAddress();
        		else
        			emp.setAddress(address);
        		
        		System.out.println("Enter Email Address to Update:");
        		String email = sc.next();
        		if(email.equalsIgnoreCase("N") || email.equalsIgnoreCase("No"))
        			email=emp.getEmailAddress();
        		else
        			emp.setEmailAddress(email);
        		
        		System.out.println("Enter Phone Number to Update:");
        		String phn = sc.next();
        		if(phn.equalsIgnoreCase("N") || phn.equalsIgnoreCase("No"))
        			phn=emp.getPhoneNumber();
        		else
        			emp.setPhoneNumber(phn);
        		
        		System.out.println("Press 'Y' to Update Birthday or 'N' to Not Update :");
        		String choice1 = sc.next();
        		LocalDate birth;
        		if(choice1.equalsIgnoreCase("Y") || choice1.equalsIgnoreCase("Yes")) {
        			System.out.println("Enter Birthday to Update: {YYYY-MM-DD}");
        			 birth = LocalDate.parse(sc.next());
        			emp.setBirthday(birth);
        		}
        		if(choice1.equalsIgnoreCase("N") || choice1.equalsIgnoreCase("No")) {
        			birth  = emp.getBirthday();
        			emp.setBirthday(birth);
        		}
        			
        		System.out.println("Press 'Y' to Update Wedding Date or 'N' to Not Update :");
        		String choice2 = sc.next();
        		LocalDate wed;
        		if(choice2.equalsIgnoreCase("Y") || choice2.equalsIgnoreCase("Yes")) {
        			System.out.println("Enter Wedding Date to Update: {YYYY-MM-DD}");
        			 wed = LocalDate.parse(sc.next());
        			emp.setWeddingAnniversary(wed);
        		}
        		if(choice2.equalsIgnoreCase("N") || choice2.equalsIgnoreCase("No")) {
        			wed = emp.getWeddingAnniversary();
        			emp.setWeddingAnniversary(wed);
        		}
        		System.out.println("Updated Employee Details Successfully!");
        		System.out.println("From :"+ repo.findById(employeeId));
        		repo.editEmployee(emp);
        		System.out.println("To :"+repo.findById(employeeId));
        		
        		
        		
        	}
        	if(choice==5) {
        		System.out.println("Enter the Date: {YYYY-MM-DD}");
        		LocalDate birthday = LocalDate.parse(sc.next());
        		List<Employee> findByBirthday = repo.findByBirthday(birthday);
        		if(findByBirthday==null||findByBirthday.isEmpty())
        			System.out.println("No Records Found");
        		else {
        			System.out.println("Name          Email_Address");
        			findByBirthday.forEach(emp->System.out.println(emp.getFirstName()+"          "+emp.getEmailAddress()));
        		}
        		
        	}
        	if(choice==6) {
        		System.out.println("Enter the Date: {YYYY-MM-DD}");
        		LocalDate wedding = LocalDate.parse(sc.next());
        		List<Employee> findByWed = repo.findByWedding(wedding);
        		if(findByWed==null||findByWed.isEmpty())
        			System.out.println("No Records Found");
        		else {
        			System.out.println("Name          PhoneNumber");
        			findByWed.forEach(emp->System.out.println(emp.getFirstName()+"          "+emp.getPhoneNumber()));
        		}
        	}
        	if(choice==7) {
        		List<Employee> findAll = repo.findAll();
        		System.out.println("Name           PhoneNumber");
        		findAll.forEach(emp->System.out.println(emp.getFirstName()+"          "+emp.getPhoneNumber()));
        	}
        	if(choice==8) {
        		System.out.println("Thankyou for Using the Application");
        		break;
        	}
        		
        	
        	
    	}
    	
//    	Connection connection = DBConnection.getConnection();
    	
    	
//        System.out.println( "Hello World!" );
    }
}
