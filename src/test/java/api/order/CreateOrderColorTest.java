package api.order;

import api.BaseApiTest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static api.testdata.OrderTestData.getTestOrder;
import static constants.ScooterColor.BLACK;
import static constants.ScooterColor.GREY;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static steps.OrderSteps.*;

@RunWith(Parameterized.class)
public class CreateOrderColorTest extends BaseApiTest {

    private final List<String> colors;
    private Response response;

    public CreateOrderColorTest(List<String> colors){
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Тестовые данные: цвет = {0}")
    public static Object[] colors(){
        return new Object[]{
                List.of(BLACK),
                List.of(GREY),
                List.of(BLACK, GREY),
                List.of()
        };
    }

    @DisplayName("Создание заказа с валидными вариантами цвета самоката")
    @Test
    public void createOrderSuccess(){
        OrderModel order = getTestOrder();
        order.setColors(colors);
        response = createOrder(order);
        response
                .then()
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue());
    }

    @After
    public void cleanUp(){
        Integer track = getOrderTrack(response);
        if (track != null) {
            cancelOrder(track);
        }
    }
}
