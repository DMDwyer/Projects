package myApp;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "part")

@XmlType(propOrder = {"number", "name", "description", "stock"})


public class Part {

	private String number;
	
	private String name;
	
	private String description;	   
	
	private int stock;
	 
	public Part(){
	 		
	}
	 
	
	public Part(String number, String name, String description, int stock) {	 
		this.number = number;
		this.name = name;
		this.description = description;
		this.stock = stock;
	 }
	
	 
	 public String getNumber() {
		return number;
	 }
	
	 public void setNumber(String number) {
		 this.number = number;
	 }
	   
	   
	 public String getName() {
		 return name;
	 }
	  
	 public void setName(String name) {
		 this.name = name;
	 }
	
	
	 public String getDescription() {
		 return description;
	 }
	
	 public void setDescription(String description) {
		 this.description = description;
	 }
		  
		   
	 public int getStock() {
		 return stock;
	 }
	
	 public void setStock(int stock) {
		 this.stock = stock;
	 }
				   	
}
