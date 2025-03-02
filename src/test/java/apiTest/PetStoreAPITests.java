package apiTest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PetStoreAPITests {

    String exampleId = "123";
    String exampleName = "Buddy";

    @BeforeClass
    public static void setup() {
        // Set the base URI for the Petstore API
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testCreatePetPositive() {
// Create the main JSON object
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("id", exampleId);

        JsonObject category = new JsonObject();
        category.addProperty("id", 0);
        category.addProperty("name", "string");
        jsonBody.add("category", category);

        jsonBody.addProperty("name", exampleName);

        JsonArray photoUrls = new JsonArray();
        photoUrls.add("path1");
        jsonBody.add("photoUrls", photoUrls);

        JsonArray tags = new JsonArray();
        JsonObject tag = new JsonObject();
        tag.addProperty("id", 0);
        tag.addProperty("name", "string");
        tags.add(tag);
        jsonBody.add("tags", tags);

        jsonBody.addProperty("status", "available");

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody.toString())
        .when()
                .post("/pet")
        .then()
                .statusCode(200)
                .body("name", equalTo("Buddy"))
                .body("status", equalTo("available"));
    }

    // Negative Scenario: Create pet with invalid payload (e.g. missing required field "name")
    @Test
    public void testCreatePetNegative() {
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("id", 1234);
        jsonBody.addProperty("status", "");

        given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(anyOf(equalTo(405), equalTo(400)));
    }

    // ----- GET PET -----

    // Positive Scenario: Retrieve an existing pet
    @Test
    public void testGetPetPositive() {
        // Then, retrieve the pet
        given()
                .pathParam("petId", exampleId)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("name", equalTo(exampleName));
    }

    // Negative Scenario: Try to retrieve a pet that does not exist
    @Test
    public void testGetPetNegative() {
        int nonExistentPetId = 99999999;

        given()
                .pathParam("petId", nonExistentPetId)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(404);
    }

    // ----- UPDATE PET -----

    // Positive Scenario: Update an existing pet successfully
    @Test
    public void testUpdatePetPositive() {
        String updatedName = "BuddyUpdated";
        String updatedStatus = "sold";
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("id", exampleId);

        JsonObject category = new JsonObject();
        category.addProperty("id", 0);
        category.addProperty("name", "string");
        jsonBody.add("category", category);

        jsonBody.addProperty("name", updatedName);

        JsonArray photoUrls = new JsonArray();
        photoUrls.add("path1");
        jsonBody.add("photoUrls", photoUrls);

        JsonArray tags = new JsonArray();
        JsonObject tag = new JsonObject();
        tag.addProperty("id", 0);
        tag.addProperty("name", "string");
        tags.add(tag);
        jsonBody.add("tags", tags);

        jsonBody.addProperty("status", updatedStatus);

        // Update the pet
        given()
                .contentType(ContentType.JSON)
                .body(jsonBody.toString())
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo(updatedName))
                .body("status", equalTo(updatedStatus));
    }

    // Negative Scenario: Update a pet that does not exist
    @Test
    public void testUpdatePetNegative() {
        int nonExistentPetId = 888888;
        String updateBody = "{ \"id\": " + nonExistentPetId + ", \"name\": \"Ghost\", \"status\": \"sold\" }";

        given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(404);
    }

    // ----- DELETE PET -----

    // Positive Scenario: Delete an existing pet successfully
    @Test
    public void testDeletePetPositive() {
        int petId = 555666;

        // Delete the pet
        given()
                .pathParam("petId", exampleId)
                .when()
                .delete("/pet/{petId}")
                .then()
                .statusCode(200);

        // Verify the pet is deleted by attempting to retrieve it
        given()
                .pathParam("petId", exampleId)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(404);
    }

    // Negative Scenario: Delete a pet that does not exist
    @Test
    public void testDeletePetNegative() {
        int nonExistentPetId = 123456789;

        given()
                .pathParam("petId", nonExistentPetId)
                .when()
                .delete("/pet/{petId}")
                .then()
                .statusCode(404);
    }
}
