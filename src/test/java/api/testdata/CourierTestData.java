package api.testdata;

import com.github.javafaker.Faker;
import model.CourierModel;

public class CourierTestData {

    private static Faker faker = new Faker();

    public static CourierModel getTestCourierWithoutFirstName(){
        return new CourierModel(getTestCourierLogin(), getTestCourierPassword());
    }

    public static String getTestCourierLogin(){
        return "ninja_" + System.currentTimeMillis();
    }

    public static String getTestCourierPassword(){
        return faker.number().digits(4);
    }

    public static String getTestCourierFirstName(){
        return faker.name().firstName();
    }

}
