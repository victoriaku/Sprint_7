package api.courier;

import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import org.junit.After;
import org.junit.Test;
import api.BaseApiTest;

import static api.testdata.CourierTestData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;

public class CreateCourierTest extends BaseApiTest {

    private final String courierLogin = getTestCourierLogin();
    private final String courierPassword = getTestCourierPassword();
    private final String courierFirstName = getTestCourierFirstName();

    private CourierModel courier;

    @DisplayName("Создание курьера с валидными данными")
    @Test
    public void createCourierSuccess(){
        courier = new CourierModel(courierLogin, courierPassword, courierFirstName);
        createCourier(courier)
                .then()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @DisplayName("Создание курьера с валидными данными без указания имени")
    @Test
    public void createCourierWithoutFirstNameSuccess(){
        courier = new CourierModel(courierLogin, courierPassword, "");
        createCourier(courier)
                .then()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @DisplayName("Создание курьера с дублирующимся логином")
    @Test
    public void createDuplicateCourierFails(){
        courier = new CourierModel(courierLogin, courierPassword, courierFirstName);
        createCourier(courier);
        createCourier(courier)
                .then()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @DisplayName("Создание курьера без логина")
    @Test
    public void createCourierWithoutLoginFails(){
        courier = new CourierModel("", courierPassword, courierFirstName);
        createCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("Создание курьера без пароля")
    @Test
    public void createCourierWithoutPasswordFails(){
        courier = new CourierModel(courierLogin, "", courierFirstName);
        createCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp(){
        CourierModel loginCourier = new CourierModel(courier.getLogin(), courier.getPassword());
        Integer courierId = getCourierId(loginCourier);
        if (courierId != null){
            deleteCourier(courierId.toString());
        }
    }
}
