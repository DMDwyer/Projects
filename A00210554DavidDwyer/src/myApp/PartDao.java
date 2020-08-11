package myApp;
import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public enum PartDao {

	instance;
	
	public static Connection getConnection() {
		
		Connection connection = null;
	      
		try 
		{
			
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/oneDB", "sa", "");
	      
		}catch (Exception e){
			e.printStackTrace();
		}
			return connection;
		}
	
	
	public void add(Part part){
		
		Connection connection = getConnection();
		
		try
		{
			
			PreparedStatement psmt = connection
					.prepareStatement("INSERT INTO PART (number, name, description, stock) VALUES (?, ?, ?, ?)");
			
			psmt.setString(1, part.getNumber());
			psmt.setString(2, part.getName());
			psmt.setString(3, part.getDescription());
			psmt.setInt(4, part.getStock());
			
			psmt.executeUpdate();
			psmt.close();
			connection.close();
			
		}catch (SQLException e){
			e.printStackTrace();}		
		}


	public List<Part> getParts() {
		 
		Connection connection = getConnection();

		List<Part> parts = new ArrayList<Part>();
     
		try 
		{
			
			PreparedStatement psmt = connection
              .prepareStatement("SELECT * FROM PART");
        
			ResultSet rs = psmt.executeQuery();

			while (rs.next()) {
				
				Part p = new Part(rs.getString("number"), rs.getString("name"), 
				rs.getString("description"), rs.getInt("stock"));
          
				parts.add(p);
				System.out.print(p);
				
			}
     
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return parts;
  
	}
	 
	
	public Part search(String number) {
	      
		Part part = null;

		Connection connection = getConnection();
	      
		try 
		{
	         
			String sql = "SELECT * FROM PART WHERE NUMBER = ?";
			
			PreparedStatement psmt = connection.prepareStatement(sql);
			
			psmt.setString(1, number);

			ResultSet rs = psmt.executeQuery();
			rs.next();

			String name = rs.getString("name");
			String description = rs.getString("description");
			Integer stock = rs.getInt("stock");
	           
	         part = new Part(name, number, description, stock);
	      
		}catch (SQLException e) {
			e.printStackTrace();
		}
			return part;
	}
	
	
	 public void update(String number, int stock) {
		    

		    Connection connection = getConnection();
		  
		    try 
		    {
		  	  
		       String sql = "UPDATE PART SET STOCK=? WHERE NUMBER=?";
		       PreparedStatement psmt = connection.prepareStatement(sql);
		       psmt.setInt(1, stock);
		       psmt.setString(2, number);

		       psmt.executeUpdate();
		       
		    } catch (SQLException e) {
		       e.printStackTrace();
		    } 
		 }

		
	 public void delete(String number) {
		    

		    Connection connection = getConnection();
		  
		    try 
		    {
		  	  
		       String sql = "DELETE FROM PART WHERE NUMBER = ?";
		       PreparedStatement psmt = connection.prepareStatement(sql);
		       psmt.setString(1, number);
		       System.out.println(number);

		       psmt.executeUpdate();
		       
		       System.out.println("Part has been deleted from SQL table");
		     
		       
		    } catch (SQLException e) {
		       e.printStackTrace();
		    } 
		 }

	
}
