package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderModel;

import static constants.EndPoints.*;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создать заказ")
    public static Response createOrder(OrderModel order){
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH);
    }

    @Step("Получить трекинговый номер после создания заказа")
    public static Integer getOrderTrack(Response createOrderResponse){
        return createOrderResponse
                .then()
                .extract()
                .path("track");
    }

    @Step("Отменить заказ")
    public static Response cancelOrder(Integer track){
        return given()
                .queryParam("track", track)
                .when()
                .put(CANCEL_ORDER_PATH);
    }

    @Step("Получить заказ по его трекинговому номеру")
    public static Response getOrder (Integer track){
        return given()
                .queryParam("t", track)
                .when()
                .get(GET_ORDER_PATH);
    }

    @Step("Получить идентификатор заказа по его трекинговому номеру")
    public static Integer getOrderId (Integer track){
        return getOrder(track)
                .then()
                .extract()
                .path("order.id");
    }

    @Step("Принять заказ курьером")
    public static Response acceptOrder(Integer courierId, String orderId){
        return given()
                .queryParam("courierId", courierId)
                .when()
                .put(ACCEPT_ORDER_PATH, orderId);
    }

    @Step("Завершить заказ")
    public static Response finishOrder(String orderId){
        return given()
                .when()
                .put(FINISH_ORDER_PATH, orderId);
    }
}
