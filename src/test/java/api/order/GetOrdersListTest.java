package api.order;

import api.BaseApiTest;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static constants.EndPoints.GET_ORDERS_LIST_PATH;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersListTest extends BaseApiTest {
    @DisplayName("Получение списка заказов")
    @Test
    public void getOrdersListSuccess(){
        given()
                .when()
                .get(GET_ORDERS_LIST_PATH)
                .then()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());
    }
}
