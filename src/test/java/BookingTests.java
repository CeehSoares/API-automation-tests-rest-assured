import Entities.Booking;
import Entities.BookingDates;
import Entities.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

public class BookingTests {
    public static Faker faker;
    private static User user;
    private static Booking booking;
    private static RequestSpecification request;
    private static BookingDates bookingDates;

    //Método Setup - Ocorre antes de tudo

    @BeforeAll
    public static void Setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        faker = new Faker();
        user = new User(
                faker.name().username(),
                faker.internet().password(8,10),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.internet().safeEmailAddress(),
                faker.phoneNumber().toString());

        bookingDates = new BookingDates("2022-01-02", "2022-01-03");

        booking = new Booking(user.getFirstName(), user.getLastName(),
                (float)faker.number().randomDouble(2, 50, 100000),
                true,bookingDates,
                "");

        RestAssured.filters(new RequestLoggingFilter(),new ResponseLoggingFilter(), new ErrorLoggingFilter());
    }

    // ".auth()" ou "API-KEY" - CREDENCIAL
    @BeforeEach
    void setRequest(){
        request = given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .auth().basic("admin", "password123");
    }

    @Test
    public void getAllBookingsById_returnOk(){
        Response response = request
                .when()
                .get("/booking")
                .then()
                .extract()
                .response();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.statusCode());
    }

    // auth - POST - Create Token
    @Test
    public void createToken_returnOK() {
        Response response = request
                .when()
                .post("/auth")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .time(lessThan(2000L))
                .extract().response();

        Assertions.assertNotNull(response);
    }

    @Test
    public void  getAllBookingsByUserFirstName_returnOk(){
        request
                .when()
                .queryParam("firstName", "Ceeh")
                .get("/booking")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .and()
                .body("results", hasSize(greaterThan(0)));
    }

    @Test
    public void  CreateBooking_ValidData_returnOk(){

        Booking test = booking;
        given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .when()
                .body(booking)
                .post("/booking")
                .then()
                .body(matchesJsonSchemaInClasspath("createBookingRequestSchema.json"))
                .and()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON).and().time(lessThan(2006L));
    }

    // Test de Bad Request
    @Test
    public void BookingsById_returnOk(){
        Response response = request
                .when()
                .get("/bookin")
                .then()
                .extract()
                .response();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    public void updateBooking_ValidData_returnOK() {
        given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .body(booking)
                .put("/booking/580")
                .then()
                .assertThat().statusCode(200)
                .and()
                .time(lessThan(2089L));
    }

    @Test
    public void deleteBooking_returnOK() {
        given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .delete("/booking/1240")
                .then()
                .assertThat().statusCode(201).and()
                .statusLine(containsString("Created"))
                .time(lessThan(2178L));
    }
}
