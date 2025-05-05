package api.courier;

import api.BaseApiTest;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import org.junit.Test;

import static api.testdata.CourierTestData.getTestCourierWithoutFirstName;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;

public class DeleteCourierTest extends BaseApiTest {

    @DisplayName("Удаление курьера с существующим id")
    @Test
    public void deleteCourierSuccess(){
        CourierModel courier = getTestCourierWithoutFirstName();
        createCourier(courier);

        deleteCourier(getCourierId(courier).toString())
                .then()
                .statusCode(HTTP_OK)
                .body("ok", equalTo(true));
    }

    @DisplayName("Удаление курьера с несуществующим id")
    @Test
    public void deleteCourierWithNonExistentIdFails(){
        CourierModel courier = getTestCourierWithoutFirstName();
        createCourier(courier);

        String courierId = getCourierId(courier).toString();
        deleteCourier(courierId);

        deleteCourier(courierId)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id нет"));
    }

    @DisplayName("Удаление курьера без id")
    @Test
    public void deleteCourierWithoutIdFails(){
        deleteCourier("")
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для удаления курьера"));
    }
}
