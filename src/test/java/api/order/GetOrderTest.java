package api.order;

import api.BaseApiTest;
import io.qameta.allure.junit4.DisplayName;
import model.OrderModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.testdata.OrderTestData.getTestOrder;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static steps.OrderSteps.*;

public class GetOrderTest extends BaseApiTest {
    private Integer track;
    private boolean wasCanceled = false;

    @Before
    public void setupOrder() {
        OrderModel order = getTestOrder();
        track = getOrderTrack(createOrder(order));
    }

    @DisplayName("Получение заказа с использованием валидного трекингового номера")
    @Test
    public void getOrderSuccess(){
        getOrder(track)
                .then()
                .statusCode(HTTP_OK)
                .body("order", notNullValue());
    }

    @DisplayName("Получение заказа с использованием несуществующего трекингового номера")
    @Test
    public void getOrderWithNonExistentTrackFails(){
        cancelOrder(track);
        wasCanceled = true;

        getOrder(track)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Заказ не найден"));
    }

    @DisplayName("Получение заказа без указания трекингового номера")
    @Test
    public void getOrderWithoutTrackFails(){
        getOrder(null)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @After
    public void cleanUp(){
        if (!wasCanceled) {
            cancelOrder(track);
        }
    }
}
