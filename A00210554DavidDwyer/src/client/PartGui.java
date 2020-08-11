package client;


import org.apache.http.HttpEntity; 
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import myApp.Part;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


public class PartGui {

	private JFrame frame;
	private JTextField numberTF;
	private JTextField nameTF;
	private JTextField descriptionTF;
	private JTextField stockTF;
	private JTextField searchTF;
	private JTextField deleteTF;
	private JTextField updateStockTF;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PartGui window = new PartGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public PartGui() {
		initialize();
	}

	
	private static String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
				
		InputStream in = entity.getContent();
				
		StringBuffer out = new StringBuffer();
				
		int n = 1;
				
		while (n > 0) {
			
			byte[] b = new byte[4096];
			n = in.read(b);
			if (n > 0) {
				out.append(new String(b, 0, n));
			}
		}
		
		return out.toString();
		
		}
	
	
	private void initialize() 
	{
		
		frame = new JFrame();
		frame.setBounds(100, 100, 519, 651);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPartDatabase = new JLabel("Part Database");
		lblPartDatabase.setBounds(190, 13, 99, 14);
		frame.getContentPane().add(lblPartDatabase);
		
		JLabel lblPartName = new JLabel("Part Name");
		lblPartName.setBounds(130, 79, 77, 14);
		frame.getContentPane().add(lblPartName);
		
		JLabel lblPartNumber = new JLabel("Part Number");
		lblPartNumber.setBounds(130, 43, 77, 14);
		frame.getContentPane().add(lblPartNumber);
		
		JLabel lblStock = new JLabel("Stock");
		lblStock.setBounds(130, 152, 46, 14);
		frame.getContentPane().add(lblStock);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(130, 115, 67, 14);
		frame.getContentPane().add(lblDescription);
		
		numberTF = new JTextField();
		numberTF.setBounds(242, 38, 99, 25);
		frame.getContentPane().add(numberTF);
		numberTF.setColumns(10);
		
		nameTF = new JTextField();
		nameTF.setBounds(242, 74, 99, 25);
		frame.getContentPane().add(nameTF);
		nameTF.setColumns(10);
		
		descriptionTF = new JTextField();
		descriptionTF.setBounds(242, 110, 99, 25);
		frame.getContentPane().add(descriptionTF);
		descriptionTF.setColumns(10);
		
		stockTF = new JTextField();
		stockTF.setBounds(242, 147, 99, 25);
		frame.getContentPane().add(stockTF);
		stockTF.setColumns(10);
		
		searchTF = new JTextField();
		searchTF.setBounds(242, 337, 99, 25);
		frame.getContentPane().add(searchTF);
		searchTF.setColumns(10);
		
		deleteTF = new JTextField();
		deleteTF.setBounds(242, 302, 99, 22);
		frame.getContentPane().add(deleteTF);
		deleteTF.setColumns(10);
		
		updateStockTF = new JTextField();
		updateStockTF.setBounds(242, 262, 99, 22);
		frame.getContentPane().add(updateStockTF);
		updateStockTF.setColumns(10);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(102, 400, 275, 171);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(scroll);
		
		JTextArea textArea = new JTextArea();
		scroll.setViewportView(textArea);
		
		JButton btnInsertPart = new JButton("Insert Part");
		btnInsertPart.setBounds(178, 185, 128, 25);
		btnInsertPart.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				CloseableHttpResponse response = null;	
				
				CloseableHttpClient httpClient=null;
				
				try
				{
					URI uri = new URIBuilder().setScheme("http")
							.setHost("localhost").setPort(8080)
							.setPath("/A00210554DavidDwyer/rest/parts").build(); 
					
					System.out.println(uri.toString());
					HttpPost httpPost = new HttpPost(uri);
					httpPost.setHeader("Accept", "text/plain");				
					httpClient = HttpClients.createDefault();
					
					String number = numberTF.getText();
					
					String name = nameTF.getText();
					
					String description = descriptionTF.getText();
					
					String stockString = stockTF.getText();
					
					int stock = Integer.parseInt(stockString);
					
					Part part = new Part (number, name, description, stock);
					
					//httpPost.setEntity(new UrlEncodedFormEntity(part));
					
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					nameValuePairs.add(new BasicNameValuePair("number", number));
					nameValuePairs.add(new BasicNameValuePair("name", name));
					nameValuePairs.add(new BasicNameValuePair("description", description));
					nameValuePairs.add(new BasicNameValuePair("stock", stockString));
					System.out.print(nameValuePairs);
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					System.out.println("Sending request");
					response = httpClient.execute(httpPost);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}	
		});
		frame.getContentPane().add(btnInsertPart);
		
		JButton btnSearchPart = new JButton("Search Part");
		btnSearchPart.setBounds(102, 337, 128, 25);
		btnSearchPart.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				textArea.setText("");
				
				CloseableHttpResponse response = null;	
				
				CloseableHttpClient httpClient=null;
				
				try
				{
					
					URI uri = new URIBuilder().setScheme("http")
							.setHost("localhost").setPort(8080)
							.setPath("/A00210554DavidDwyer/rest/parts/"+searchTF.getText()).build(); 
					
					System.out.println(uri.toString());
					HttpGet httpGet = new HttpGet(uri);
					httpGet.setHeader("Accept", "application/xml");
									
					httpClient = HttpClients.createDefault();
					response = httpClient.execute(httpGet);
					
					HttpEntity entity = response.getEntity();

					String text = getASCIIContentFromEntity(entity);
					
					System.out.println(text);
					
					Part part =  new ParsePart().doParsePart(text); 
						
					textArea.append("Part ");
					textArea.append("\n Number: " + part.getNumber() + "\n");
					textArea.append("Name : " + part.getName() + "\n");
					textArea.append("Description : " + part.getDescription() + "\n");
					textArea.append("Stock : " + part.getStock() + "\n");
						
				}catch (Exception e) {
					e.printStackTrace();
				}	
			}
		});
		frame.getContentPane().add(btnSearchPart);
		
		JButton btnListParts = new JButton("List Parts");
		btnListParts.setBounds(178, 223, 128, 25);
		btnListParts.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				textArea.setText("");
				
				CloseableHttpResponse response = null;	
				
				CloseableHttpClient httpClient=null;
				
				try
				{
					
					URI uri = new URIBuilder().setScheme("http")
							.setHost("localhost").setPort(8080)
							.setPath("/A00210554DavidDwyer/rest/parts").build(); //add /1
					
					System.out.println(uri.toString());
					HttpGet httpGet = new HttpGet(uri);
					httpGet.setHeader("Accept", "application/xml");
									
					httpClient = HttpClients.createDefault();
					response = httpClient.execute(httpGet);
					
					HttpEntity entity = response.getEntity();
					
					String text = getASCIIContentFromEntity(entity);
						
					System.out.println(text);
						
					List<Part> partList = new ParseParts().doParseParts(text); //change to book
						
					for (Part part : partList){
							
						textArea.append("Part ");
						textArea.append("\n Number:" + part.getNumber() + "\n");
						textArea.append("Name : " + part.getName()+ "\n");
						textArea.append("Description : " + part.getDescription()+ "\n");
						textArea.append("Stock : " + part.getStock()+ "\n");
						textArea.append("  \n");
							
						System.out.println("Number: " + part.getNumber());
						System.out.println("Name : " + part.getName());
						System.out.println("Description : " + part.getDescription());
						System.out.println("Stock : " + part.getStock());
						
					}
				
				}catch (Exception e) {
					e.printStackTrace();
				}	
			}
		});
		frame.getContentPane().add(btnListParts);
		
		JButton btnUpdateStock = new JButton("Update Stock");
		btnUpdateStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CloseableHttpResponse response = null;	
				CloseableHttpClient httpClient=null;
				
				
				try
				{
					URI uri = new URIBuilder().setScheme("http")
							.setHost("localhost").setPort(8080)
							.setPath("/A00210554DavidDwyer/rest/parts/" + numberTF.getText()).build(); 
					
					System.out.println(uri.toString());
					HttpPut httpPut = new HttpPut(uri);
					httpPut.setHeader("Accept", "text/plain");
									
					httpClient = HttpClients.createDefault();
					
					String number = numberTF.getText();

					String stockString = updateStockTF.getText();
					int stock = Integer.parseInt(stockString);
	
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
					nameValuePairs.add(new BasicNameValuePair("number", number));
					nameValuePairs.add(new BasicNameValuePair("stock", stockString));
					System.out.print(nameValuePairs);
					httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					System.out.println("Sending request");
					response = httpClient.execute(httpPut);
					
				}
				
				catch (Exception e1) {
					e1.printStackTrace();
				}		
			}
		});
		btnUpdateStock.setBounds(102, 261, 128, 25);
		frame.getContentPane().add(btnUpdateStock);
		
		
		JButton btnDeletePart = new JButton("Delete Part");
		btnDeletePart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textArea.append("");
				CloseableHttpResponse response = null;	
				CloseableHttpClient httpClient=null;
				
				try
				{
					
					URI uri = new URIBuilder().setScheme("http")
							.setHost("localhost").setPort(8080)
							.setPath("/A00210554DavidDwyer/rest/parts/" + deleteTF.getText()).build(); 
					
					System.out.println(uri.toString());
					HttpDelete httpDelete = new HttpDelete(uri);
					httpDelete.setHeader("Accept", "text/plain");
									
					httpClient = HttpClients.createDefault();
					
					System.out.println("Sending delete request");
					response = httpClient.execute(httpDelete);
					
					textArea.append("Part Number " + deleteTF.getText() + " Deleted");
					
				}
				
				catch (Exception e1) {
					e1.printStackTrace();
				}

				
			}
		});
		btnDeletePart.setBounds(102, 299, 128, 25);
		frame.getContentPane().add(btnDeletePart);
		
		
	}
}
	

