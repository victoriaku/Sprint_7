package api.testdata;

import com.github.javafaker.Faker;
import model.OrderModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class OrderTestData {

    public static OrderModel getTestOrder(){
        Faker faker = new Faker(new Locale("ru"));
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String phone = getRandomPhone();
        int rentTime = faker.random().nextInt(1, 8);
        String deliveryDate = LocalDate.now().plusDays(1).toString();
        return new OrderModel(firstName, lastName, address,
                4, phone, rentTime, deliveryDate, address, List.of());
    }

    public static String getRandomPhone(){
        StringBuilder phone = new StringBuilder("+79");
        Random random = new Random();
        for (int i = 0; i < 9; i++){
            phone.append(random.nextInt(10));
        }
        return phone.toString();
    }
}
