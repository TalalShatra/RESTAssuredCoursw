
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @BeforeClass
    public void setup() {

        RestAssured.baseURI = "https://api.zippopotam.us";
    }

    @Test
    public void test() {

        given()
                .when()
                .then();
    }

    @Test
    public void  checkingStatusCode() {

        given()
                .when()
                .get("/us/07506")
                .then()
                .statusCode(200);
    }

    @Test
    public void loggingRequestDetails() {

        given()
                .log().all()
                .when()
                .get("/us/07506")
                .then()
                .statusCode(200);
    }

    @Test
    public void loggingResponseDetails() {

        given()
                .when()
                .get("h/us/07506")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void checkContentType() {

        given()
                .when()
                .get("/us/07506")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200);
    }

    @Test
    public void validateCountryTest() {

        given()
                .when()
                .get("/us/07506")
                .then()
                .body("country", equalTo("United States"))
                .statusCode(200);
    }

    @Test
    public void validateCountryAbv() {

        given()
                .when()
                .get("/us/07506")
                .then()
                .body("'country abbreviation'", equalTo("US"))
                .statusCode(200);
    }

    @Test
    public void validateState() {

        given()
                .when()
                .get("/us/07506")
                .then()
                .body("places[0].state", equalTo("New Jersey"))
                .statusCode(200);
    }

    @Test
    public void pathParameters() {

        String country ="us";
        String zipcode = "07506";

        given()
                .pathParam("country", country)
                .pathParam("zipcode", zipcode)
                .when()
                .get("/{country}/{zipcode}")
                .then()
                .body("places[0].state", equalTo("New Jersey"))
                .statusCode(200);
    }

    @Test
    public void queryParameters() {

        String gender ="male";
        String status ="active";

        given()
                .param("gender", gender)
                .param("status", status)
                .when()
                .get("https://gorest.co.in/public/v2/users")
                .then()
                .statusCode(200)
                .log().body();
    }
}
