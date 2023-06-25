package br.edu.univas.test.brendon.main;
import br.edu.univas.test.brendon.dto.CustomerDTO;
import static org.junit.Assert.assertEquals;

import org.apache.http.HttpStatus;
import org.junit.Test;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CustomerTests {

	
	private final String customerUrl = "http://localhost:8080/customer/";

	

	// 1- post success
	@Test
	public void testCreateCustomerSuccess() {
		Long code = 100L;
		Response resp = createCustomerById(code);
		resp.then().assertThat().statusCode(HttpStatus.SC_CREATED);
	}

	// 2- post failed code 
	@Test
	public void testCreateCustomerCode() {
		Long code = 1L;
		Response resp = RestAssured.get(customerUrl + code);

		if (resp.getStatusCode() == HttpStatus.SC_OK) {
			RestAssured.given().contentType(ContentType.JSON).post(customerUrl).then().assertThat()
					.statusCode(HttpStatus.SC_BAD_REQUEST);
		} else {
			Response write = createCustomerById(code);
			write.then().assertThat().statusCode(HttpStatus.SC_CREATED);
		}
	}

	// 3- failed 
	@Test
	public void testCreateCustomer_failedJson() {
		RestAssured.given().contentType(ContentType.JSON).post(customerUrl).then().assertThat()
				.statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	

	// 4- get success
	@Test
	public void testCustomerById_Success() {
		Long code = 1L;
		createCustomerById(code);
		Response resp = RestAssured.get(customerUrl + code);
		resp.then().assertThat().statusCode(HttpStatus.SC_OK);
	}

	// 5 - get failed with object
	@Test
	public void testGetCustomerById_Failed() {
		Long nonExistingDeliveryNumber = 60L;
		Response resp = RestAssured.get(customerUrl + nonExistingDeliveryNumber);
		resp.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
	}



	// 6 - Put success
	@Test
	public void testUpdateById_Success() {
		Long code = 1L;

		Response action = RestAssured.put(customerUrl + "active/" + code);
		action.then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT);
		assertEquals(HttpStatus.SC_NO_CONTENT, action.getStatusCode());

		Response resp = RestAssured.get(customerUrl + code);
		resp.then().assertThat().statusCode(HttpStatus.SC_OK);
	}

	// 7 - Put with no content
	@Test
	public void testUpdate_withExistCode() {
		Long code = 1L;

		Response resp = RestAssured.get(customerUrl + code);
		if (resp.getStatusCode() == HttpStatus.SC_OK) {
			Response action = RestAssured.put(customerUrl + "active/" + code);
			action.then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT);
			assertEquals(HttpStatus.SC_NO_CONTENT, action.getStatusCode());
		} else {
			resp.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
		}
	}
	
	// 8 test active
	
	@Test
	public void testActiveSuccess() {
		;
        Response resp = RestAssured.put(customerUrl + "/active/" + 1L);
        resp.then().assertThat().statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	public void testActiveFailed() {
		
        Response resp = RestAssured.put(customerUrl + "/active/" + 60L);
        resp.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
	}

	private Response createCustomerById(Long code) {
		CustomerDTO xpto = new CustomerDTO(code,"1234567", "brendon", "01/05/1999", "M", "brendonnogueira@gmail.com", "trust", true );

		Response resp = RestAssured.given().body(xpto).contentType(ContentType.JSON).post(customerUrl);
		return resp;
	}

}