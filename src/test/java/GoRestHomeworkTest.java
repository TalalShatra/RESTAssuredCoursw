import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class GoRestHomeworkTest {

    private RequestSpecification reqSpec;
    private HashMap<String, String> requestBody;
    private  Object userId;


    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://gorest.co.in";

        reqSpec = given()
                .log().uri()
                .header("Authorization", "Bearer 53d95bec442a8f9343db9cd387b27ecb9adbadf050d36e60ca77da2430b9d4c2")
                .contentType(ContentType.JSON);

        requestBody = new HashMap<>();
        requestBody.put("name", "John Monroe");
        requestBody.put("email", "johnmonr@gmail.com");
        requestBody.put("gender", "male");
        requestBody.put("status", "active");
    }
    @Test
    public void createUserPositiveTest(){
        userId = given()
                    .spec(reqSpec)
                    .body(requestBody)
                .when()
                    .post("/public/v2/users")
                .then()
                    .statusCode(201)
                    .body("name", equalTo(requestBody.get("name")))
                    .extract().path("id");

    }
    @Test(dependsOnMethods = "createUserPositiveTest")
    public void createUserNegativeTest(){
        given()
                    .spec(reqSpec)
                    .body(requestBody)
                .when()
                    .post("/public/v2/users")
                .then()
                    .statusCode(422);
    }
    @Test(dependsOnMethods = "createUserNegativeTest")
    public void getUserInformationTest(){
        given()
                    .spec(reqSpec)
                .when()
                    .get("/public/v2/users/" + userId)
                .then()
                    .statusCode(200)
                    .body("name", equalTo(requestBody.get("name")));
    }
    @Test(dependsOnMethods = "getUserInformationTest")
    public void editUserEmailTest(){

        HashMap<String, String> editRequestBody= new HashMap<>();
        editRequestBody.put("email", "johnmon@gmail.com");

        given()
                    .spec(reqSpec)
                    .body(editRequestBody)
                .when()
                    .put("/public/v2/users/" + userId)
                .then()
                    .statusCode(200);
    }
    @Test(dependsOnMethods = "editUserEmailTest")
    public void deleteUserPositiveTest(){

        given()
                    .spec(reqSpec)
                .when()
                    .delete("/public/v2/users/" + userId)
                .then()
                    .statusCode(204);
    }
    @Test(dependsOnMethods = "deleteUserPositiveTest")
    public void deleteUserNegativeTest(){

        given()
                    .spec(reqSpec)
                .when()
                    .delete("/public/v2/users/" + userId)
                .then()
                    .statusCode(404);
    }

}
