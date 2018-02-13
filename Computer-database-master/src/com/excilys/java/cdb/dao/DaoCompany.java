package com.excilys.java.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.java.cdb.connectionManager.SingletonConn;
import com.excilys.java.cdb.model.Company;

public class DaoCompany extends Dao<Company>{

	private String defaultquery = " Select id, name from company";
	
	private ResultSet results;
		
	
	@Override
	public Optional<Company>  findById(Long id) {
		// TODO Auto-generated method stub
			
			String queryfindbyId = "SELECT id ,name FROM company WHERE id=?";
		 	PreparedStatement ps;
			SingletonConn con= SingletonConn.INSTANCE;		
			
			try {
				
				ps = con.getConn().prepareStatement(queryfindbyId);
				ps.setLong(1, id);
				Optional<ResultSet> opCompany = Optional.ofNullable(ps.executeQuery());
				con.closeConn();
				
				if(opCompany.isPresent()) {
				
					ResultSet rs = opCompany.get();
					
					while(rs.next()) {
						String name =rs.getString("Name");
						Long idres = rs.getLong("id");
						System.out.println("Name : "+ name  +"   id: "+idres);
						System.out.println();
					}
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				System.err.println(" error requetes FIND : " + e.getMessage());
			}
			
				
			
	
		return null;
	}

	@Override
	public boolean create(Company obj) {
		// TODO Auto-generated method stub
		
		String queryfindbyId = "INSERT INTO company (id, name) VALUES (?, ?)";
	 	PreparedStatement ps;
		SingletonConn con= SingletonConn.INSTANCE;		
		
		try {
			
			ps = con.getConn().prepareStatement(queryfindbyId);
			ps.setLong(1,obj.getId());
			ps.setString(2, obj.getName());
			Optional<ResultSet> opCompany = Optional.ofNullable(ps.executeQuery());
			con.closeConn();
			
			if(opCompany.isPresent()) {
			
				ResultSet rs = opCompany.get();
				
				while(rs.next()) {
				
					String name =rs.getString("Name");
					Long id = rs.getLong("id");
					System.out.println("Name : "+ name  +"   id: "+id);
					System.out.println();
				}
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(" error requete CREATE  : " + e.getMessage());
		}
		
			
		
		return null;
	}

	@Override
	public boolean update(Company obj) {
		// TODO Auto-generated method stub
		String queryfindbyId = "INSERT INTO company (id, name) VALUES (?, ?)";
	 	PreparedStatement ps;
		SingletonConn con= SingletonConn.INSTANCE;		
		
		return false;
	}

	@Override
	public boolean delete(Company obj) {
		// TODO Auto-generated method stub
		return false;
	}


}
