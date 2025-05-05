package api.order;

import api.BaseApiTest;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import model.OrderModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static api.testdata.CourierTestData.getTestCourierWithoutFirstName;
import static api.testdata.OrderTestData.getTestOrder;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;
import static steps.OrderSteps.*;

public class AcceptOrderTest extends BaseApiTest {
    private final CourierModel courier = getTestCourierWithoutFirstName();
    private Integer courierId;
    private boolean wasCourierDeleted = false;

    private final OrderModel order = getTestOrder();
    private Integer track;
    private String orderId;
    private boolean wasOrderCanceled = false;

    @Before
    public void setupTestData(){
        createCourier(courier);
        courierId = getCourierId(courier);

        track = getOrderTrack(createOrder(order));
        orderId = String.valueOf(getOrderId(track));
    }

    @DisplayName("Принять заказ с валидными данными")
    @Test
    public void acceptOrderSuccess(){
        acceptOrder(courierId, orderId)
                .then()
                .statusCode(HTTP_OK)
                .body("ok", equalTo(true));
    }

    @DisplayName("Принять заказ без указания Id курьера")
    @Test
    public void acceptOrderWithoutCourierIdFails(){
        acceptOrder(null, orderId)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @DisplayName("Принять заказ, используя несуществующий Id курьера")
    @Test
    public void acceptOrderWithNonExistentCourierIdFails(){
        deleteCourier(courierId.toString());
        wasCourierDeleted = true;

        acceptOrder(courierId, orderId)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id не существует"));
    }

    @DisplayName("Принять заказ без указания Id заказа")
    @Test
    public void acceptOrderWithoutOrderIdFails(){
        acceptOrder(courierId, "")
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @DisplayName("Принять заказ с несуществующим Id")
    @Test
    public void acceptOrderWithNonExistentOrderIdFails(){
        cancelOrder(track);
        wasOrderCanceled = true;

        acceptOrder(courierId, orderId)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Заказа с таким id не существует"));
    }

    @After
    public void cleanUp(){
        if (!wasCourierDeleted) {
            deleteCourier(courierId.toString());
        }

        if (!wasOrderCanceled) {
            boolean isOrderInDelivery = getOrder(track)
                .then()
                .extract()
                .path("order.inDelivery");
            if (!isOrderInDelivery){
                cancelOrder(track);
            } else {
                finishOrder(orderId);
            }
        }
    }
}
