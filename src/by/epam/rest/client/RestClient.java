package by.epam.rest.client;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import by.epam.rest.bean.Customer;

public class RestClient {

	public static void main(String[] args) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		
		// Get list Customers and print it
		List<Customer> customers = getCustomers(service);
		printListCustomers(customers);
		
		// Get Customer by ID and print it
		int id = 1;
		Customer customer = getCustomerById(id, service);
		
		System.out.println("\nCustomer with ID = " + id);
		printCustomer(customer);
		
		// Add new Customer and print result
		customer = new Customer("10", "fName", "lName", "email", "phone");
		addCustomer(customer, service);
		
		System.out.println("\nList Customers after adding new one");
		customers = getCustomers(service);
		printListCustomers(customers);
		
		// Update added Customer and print it
		customer.setFirstName("Ivan");
		customer.setLastName("Urgant");
		updateCustomer(customer, service);
		
		id = Integer.parseInt(customer.getId());
		customer = getCustomerById(id, service);
		System.out.println("\nUpdated Customer with ID = " + id);
		printCustomer(customer);
		
		// Delete Customer and print result
		deleteCustomer(id, service);
		
		System.out.println("\nList Customers after delete operation");
		customers = getCustomers(service);
		printListCustomers(customers);
	}
	
	/*
	 * Returns list Customers
	 */
	private static List<Customer> getCustomers(WebResource service) {
		GenericType<List<Customer>> genericType = new GenericType<List<Customer>>() {
		};
		List<Customer> customers = service.path("rest").path("customer")
				.accept(MediaType.APPLICATION_XML).get(genericType);
		return customers;
	}
	
	/*
	 * Returns Customer by ID
	 */
	private static Customer getCustomerById(int id, WebResource service) {
		Customer customer = service.path("rest").path("customer").path(String.valueOf(id))
				.accept(MediaType.APPLICATION_XML).get(Customer.class);
		return customer;
	}
	
	/*
	 * Adds new Customer
	 */
	private static void addCustomer(Customer customer, WebResource service) {
		service.path("rest").path("customer")
				.accept(MediaType.APPLICATION_XML).put(customer);
	}
	
	/*
	 * Updates Customer
	 */
	private static void updateCustomer(Customer customer, WebResource service) {
		service.path("rest").path("customer")
				.accept(MediaType.APPLICATION_XML).post(customer);
	}
	
	/*
	 * Delete Customer
	 */
	private static void deleteCustomer(int id, WebResource service) {
		service.path("rest").path("customer").path(String.valueOf(id))
				.accept(MediaType.APPLICATION_XML).delete();
	}
	
	/*
	 * Prints list Customers
	 */
	private static void printListCustomers(List<Customer> customers) {
		for (Customer customer : customers) {
			System.out.println(customer);
		}
	}
	
	/*
	 * Prints Customer
	 */
	private static void printCustomer(Customer customer) {
		System.out.println(customer);
	}
	
	private static URI getBaseURI() {
		URI baseUri = UriBuilder.fromUri("http://localhost:8080/REST").build();
		return baseUri;
	}

}
