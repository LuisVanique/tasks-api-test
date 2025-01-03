package br.ce.luisvanique.task.apitest;

import java.time.LocalDate;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}

	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveAdicionarTarefasComSucesso() {
		RestAssured.given()
	    .body("{ \"task\": \"Teste via API\", \"dueDate\": \"" + 
	    LocalDate.now().plusDays(1) + "\" }")
	    .contentType(ContentType.JSON)
	    .when()
	        .post("/todo")
	    .then()
	        .log().all() // Loga toda a resposta para análise
	        .statusCode(201);;		
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{ \"task\": \"Teste via API\", \"dueDate\": \"2010-12-07\" }").contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));		
	}
	
}



