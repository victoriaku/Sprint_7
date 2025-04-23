package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;

import static constants.EndPoints.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Создать курьера")
    public static Response createCourier(CourierModel courier){
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(CREATE_COURIER_PATH);
    }

    @Step("Авторизовать курьера")
    public static Response loginCourier(CourierModel courier){
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(LOGIN_COURIER_PATH);
    }

    @Step("Получить Id курьера после авторизации")
    public static Integer getCourierId(CourierModel courier){
        return loginCourier(courier)
                .then()
                .extract()
                .path("id");
    }

    @Step("Удалить курьера")
    public static Response deleteCourier(String id){
        return given()
                .when()
                .delete(DELETE_COURIER_PATH, id);
    }
}
