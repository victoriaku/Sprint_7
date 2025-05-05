package constants;

public final class EndPoints {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    public static final String CREATE_COURIER_PATH = "/api/v1/courier";
    public static final String LOGIN_COURIER_PATH = "/api/v1/courier/login";
    public static final String DELETE_COURIER_PATH = "/api/v1/courier/{id}";

    public static final String CREATE_ORDER_PATH = "/api/v1/orders";
    public static final String CANCEL_ORDER_PATH = "/api/v1/orders/cancel";
    public static final String GET_ORDER_PATH = "/api/v1/orders/track";
    public static final String GET_ORDERS_LIST_PATH = "/api/v1/orders";
    public static final String ACCEPT_ORDER_PATH = "/api/v1/orders/accept/{id}";
    public static final String FINISH_ORDER_PATH = "/api/v1/orders/finish/{id}";
}
