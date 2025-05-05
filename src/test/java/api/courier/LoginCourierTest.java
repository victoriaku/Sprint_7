package api.courier;

import api.BaseApiTest;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import org.junit.*;

import static api.testdata.CourierTestData.getTestCourierLogin;
import static api.testdata.CourierTestData.getTestCourierPassword;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static steps.CourierSteps.*;

public class LoginCourierTest extends BaseApiTest {

    private static final String COURIER_LOGIN = getTestCourierLogin();
    private static final String COURIER_PASSWORD = getTestCourierPassword();
    private static CourierModel courier;

    @BeforeClass
    public static void createTestCourier(){
        courier = new CourierModel(COURIER_LOGIN, COURIER_PASSWORD);
        createCourier(courier);
    }

    @DisplayName("Авторизация курьера с валидными данными")
    @Test
    public void loginCourierSuccess(){
        loginCourier(courier)
                .then()
                .statusCode(HTTP_OK)
                .body("id", notNullValue());
    }

    @DisplayName("Авторизация курьера без логина")
    @Test
    public void loginCourierWithoutLoginFails(){
        CourierModel testCourier = new CourierModel("", COURIER_PASSWORD);
        loginCourier(testCourier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Авторизация курьера без пароля")
    @Test
    public void loginCourierWithoutPasswordFails(){
        CourierModel testCourier = new CourierModel(COURIER_LOGIN, "");
        loginCourier(testCourier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Авторизация курьера с неправильным паролем")
    @Test
    public void loginCourierWithIncorrectPasswordFails(){
        CourierModel testCourier = new CourierModel(COURIER_LOGIN, getTestCourierPassword());
        loginCourier(testCourier)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @DisplayName("Авторизация курьера с неправильным логином")
    @Test
    public void loginCourierWithIncorrectLoginFails(){
        CourierModel testCourier = new CourierModel(getTestCourierLogin(), COURIER_PASSWORD);
        loginCourier(testCourier)
                .then()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @AfterClass
    public static void cleanUp(){
        Integer courierId = getCourierId(courier);
        if (courierId != null) {
            deleteCourier(courierId.toString());
        }
    }
}
