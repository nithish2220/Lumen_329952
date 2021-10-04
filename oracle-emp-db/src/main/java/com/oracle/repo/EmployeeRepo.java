package com.oracle.repo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.oracle.entity.Employee;
import com.oracle.exception.NameNotFoundException;

public class EmployeeRepo {
	static Logger log = Logger.getRootLogger();
	private Connection con;
	public EmployeeRepo(Connection con) {
		this.con = con;
	}
	
	public void createDefaultTable() {
		log.info("*****************************************************");
		log.info("Creating Employee Table : Default");
		String sql = "Create table OEmployee(employeeid int NOT NULL PRIMARY KEY, firstname varchar(20), lastname varchar(20), address varchar(20), emailaddress varchar(25), phonenumber varchar(10), birthday date, weddinganniversary date)";
		PreparedStatement smt;
		try {
			smt = con.prepareStatement(sql);
			smt.execute(sql);
			log.info("Table Created Successfully!");
		} catch (SQLException e) {
			log.info("Exception Occured while creating table");
			e.printStackTrace();
		}
		log.info("*****************************************************");
	}
	
	public void addValuesTOTable() {
		log.info("*****************************************************");
		log.info("Adding some default records to the Database");
		addEmployee(new Employee(101,"Nithish","Kumar","Kothagudem","sasanala@gmail.com","9254862471",LocalDate.of(2000,02,2),LocalDate.of(2021,02,25)));
		addEmployee(new Employee(121,"Kiran","Kumar","Kadapa","kiran@gmail.com","9254862474",LocalDate.of(2000,04,1),LocalDate.of(2021,02,25)));
		addEmployee(new Employee(131,"Flenn","Twister","Pune","flenn@gmail.com","9254862478",LocalDate.of(2000,04,1),LocalDate.of(2021,04,25)));
		addEmployee(new Employee(141,"Ravi","Shastri","Chennai","ravi@gmail.com","9254862475",LocalDate.of(2000,02,2),LocalDate.of(2021,03,25)));
		addEmployee(new Employee(151,"Ram","Nath","Hyderabad","ram@gmail.com","9254862412",LocalDate.of(2000,02,2),LocalDate.of(2021,03,25)));
		log.info("*****************************************************");
		
	}
	
	public Employee findById(int employeeId) {
		String sql = "select *from OEmployee where employeeid=?";
		Employee employee = null;
		try(PreparedStatement psmt = con.prepareStatement(sql)){
			employee = new Employee();
			psmt.setInt(1, employeeId);
			ResultSet rs = psmt.executeQuery();	
			while(rs.next()) {
				employee.setEmployeeId(rs.getInt(1));
				employee.setFirstName(rs.getString(2));
				employee.setLastName(rs.getString(3));
				employee.setAddress(rs.getString(4));
				employee.setEmailAddress(rs.getString(5));
				employee.setPhoneNumber(rs.getString(6));
				employee.setBirthday(rs.getDate(7).toLocalDate());
				employee.setWeddingAnniversary(rs.getDate(8).toLocalDate());
			}
		}catch(SQLException e) {
			System.out.println("No data Found for ID:"+employeeId);
		}
		return employee;
	}
	
	public List<Employee> findAll(){
		log.info("*****************************************************");
		log.info("findAll() method is called");
		List<Employee> allEmployee = new ArrayList<>();
		String sql = "select *from OEmployee";
		ResultSet rs = null;
		try(PreparedStatement psmt = con.prepareStatement(sql)) {
			rs = psmt.executeQuery();
			while(rs.next()) {
				Employee emp = new Employee();
				emp.setEmployeeId(rs.getInt(1));
				emp.setFirstName(rs.getString(2));
				emp.setLastName(rs.getString(3));
				emp.setAddress(rs.getString(4));
				emp.setEmailAddress(rs.getString(5));
				emp.setPhoneNumber(rs.getString(6));
				emp.setBirthday(rs.getDate(7).toLocalDate());
				emp.setWeddingAnniversary(rs.getDate(8).toLocalDate());
				allEmployee.add(emp);
				log.info(emp);
			}	
		}
		catch(SQLException e) {
			log.info("Some Finding exception is raised");
			e.printStackTrace();
		}
		log.info("All Employee Details are successfully sent!");
		log.info("*****************************************************");
		return allEmployee;
	}

	public void addEmployee(Employee employee) {
		log.info("*****************************************************");
		log.info("Adding Employee Details");
		String sql = "insert into OEmployee values(?,?,?,?,?,?,?,?)";
		try(PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setInt(1, employee.getEmployeeId());
			psmt.setString(2, employee.getFirstName());
			psmt.setString(3, employee.getLastName());
			psmt.setString(4, employee.getAddress());
			psmt.setString(5, employee.getEmailAddress());
			psmt.setString(6, employee.getPhoneNumber());
			psmt.setDate(7, Date.valueOf(employee.getBirthday()));
			psmt.setDate(8, Date.valueOf(employee.getWeddingAnniversary()));
			psmt.executeUpdate();
			log.info("Successfully Added "+employee);
		}
		catch(SQLException e) {
			log.info("Adding Exception is Raised");
			e.printStackTrace();
		}	
		log.info("*****************************************************");
	}
	
	public List<Employee> findByName(String str){
		log.info("*****************************************************");
		log.info("FindBy Employee Menthod called by the User");
		List<Employee> result = null;
		ResultSet rs = null;
		String sql = "select *from OEmployee where UPPER(firstname)=?";
			try {
				
				PreparedStatement psmt = con.prepareStatement(sql);
				psmt.setString(1,str.toUpperCase());
				rs = psmt.executeQuery();
				result = new ArrayList<>();
					while(rs.next()) {
						Employee emp = new Employee();
						emp.setEmployeeId(rs.getInt(1));
						emp.setFirstName(rs.getString(2));
						emp.setLastName(rs.getString(3));
						emp.setAddress(rs.getString(4));
						emp.setEmailAddress(rs.getString(5));
						emp.setPhoneNumber(rs.getString(6));
						emp.setBirthday(rs.getDate(7).toLocalDate());
						emp.setWeddingAnniversary(rs.getDate(8).toLocalDate());
						result.add(emp);
						
						log.info(emp);
					}
			} catch (SQLException e) {
				e.printStackTrace();	
			}
			log.info("*****************************************************");
			return result;
		}
	
	public void editEmployee(Employee e) {
		log.info("*****************************************************");
		log.info("editing the Employee details method is called!");
		String sql = "update OEmployee SET firstname=?, lastname=?, address=?, emailaddress=?, phonenumber=?, birthday=?, weddinganniversary=? where employeeid=?";
		try(PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setString(1, e.getFirstName());
			psmt.setString(2, e.getLastName());
			psmt.setString(3, e.getAddress());
			psmt.setString(4, e.getEmailAddress());
			psmt.setString(5, e.getPhoneNumber());
			psmt.setDate(6, Date.valueOf(e.getBirthday()));
			psmt.setDate(7, Date.valueOf(e.getWeddingAnniversary()));
			psmt.setInt(8, e.getEmployeeId());
			
			psmt.executeUpdate();
			
			log.info("Employee Data Updated Successfully");
			log.info("Updated Data:"+ e);
		}catch(SQLException exception) {
			exception.printStackTrace();
		}
		log.info("*****************************************************");
	}

	public List<Employee> findByBirthday(LocalDate birthday) {
		log.info("Find by Birthday is called with value:="+birthday);
		List<Employee> result = new ArrayList<Employee>();
		ResultSet rs = null;
		String sql = "select *from OEmployee where birthday=?";
		
		try(PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setDate(1, Date.valueOf(birthday));
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				Employee emp = new Employee();
				emp.setEmployeeId(rs.getInt(1));
				emp.setFirstName(rs.getString(2));
				emp.setLastName(rs.getString(3));
				emp.setAddress(rs.getString(4));
				emp.setEmailAddress(rs.getString(5));
				emp.setPhoneNumber(rs.getString(6));
				emp.setBirthday(rs.getDate(7).toLocalDate());
				emp.setWeddingAnniversary(rs.getDate(8).toLocalDate());
				result.add(emp);
				log.info(emp);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("*****************************************************");
		return result;
	}

	public List<Employee> findByWedding(LocalDate wedding) {
		log.info("*****************************************************");
		log.info("Find by Birthday is called with value:="+wedding);
		List<Employee> result = new ArrayList<Employee>();
		ResultSet rs = null;
		String sql = "select *from OEmployee where weddinganniversary=?";
		
		try(PreparedStatement psmt = con.prepareStatement(sql)){
			psmt.setDate(1, Date.valueOf(wedding));
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				Employee emp = new Employee();
				emp.setEmployeeId(rs.getInt(1));
				emp.setFirstName(rs.getString(2));
				emp.setLastName(rs.getString(3));
				emp.setAddress(rs.getString(4));
				emp.setEmailAddress(rs.getString(5));
				emp.setPhoneNumber(rs.getString(6));
				emp.setBirthday(rs.getDate(7).toLocalDate());
				emp.setWeddingAnniversary(rs.getDate(8).toLocalDate());
				result.add(emp);
				log.info(emp);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("*****************************************************");
		return result;
	}
}
