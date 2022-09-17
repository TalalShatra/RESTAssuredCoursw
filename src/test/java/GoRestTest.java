import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.*;



public class GoRestTest {
    private RequestSpecification reqSpec;
    private HashMap<String, String> requestBody;
    private  Object userId;


    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://gorest.co.in";

        reqSpec = given()
                .log().uri()
                .header("Authorization", "Bearer 53d95bec442a8f9343db9cd387b27ecb9adbadf050d36e60ca77da2430b9d4c2")
                .contentType(ContentType.JSON); // to be sure it is in jason format

        requestBody = new HashMap<>();
        requestBody.put("name", "John Leon");
        requestBody.put("email", "johnln@gmail.com");
        requestBody.put("gender", "male");
        requestBody.put("status", "active");
    }

    @Test
    public void createUserTest() {

            userId = given()
                    .spec(reqSpec)
                    .body(requestBody)
                .when()
                    .post("/public/v2/users")
                .then()
                    .statusCode(201)
                    .log().body()
                    .extract().path("id");

  //JSON= Java Script Object Notation. In Javascript objects have the similar structure with Hashmaps in Java. This is why we use Hashmap
    }

    @Test(dependsOnMethods = "createUserTest")
    public void editUserTest() {

        HashMap<String, String> editRequestBody= new HashMap<>();
        editRequestBody.put("email", "johnl@gmail.com");

        given()
                .spec(reqSpec)
                .body(editRequestBody)
            .when()
                .put("/public/v2/users/" + userId)
            .then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "editUserTest")
    public void deleteUserTest() {

        given()
                    .spec(reqSpec)
                .when()
                    .delete("/public/v2/users/" + userId)
                .then()
                    .statusCode(204);
    }

}
