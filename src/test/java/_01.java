import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class _01 {

        @BeforeClass
        public void setup() {

            RestAssured.baseURI = "https://gorest.co.in";
        }
        @Test
        public void allUserInfo() {

            given()
                    .when()
                    .get("/public/v2/users")
                    .then()
                    .statusCode(200)
                    .log().body();
        }
        @Test
        public void specificUserInfo() {

            String id ="2772";

            given()
                    .param("id", id)
                    .when()
                    .get("/public/v2/users")
                    .then()
                    .statusCode(200)
                    .log().body();
        }

    }

