package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Locale;

@UtilityClass
public class DataGenerator {


    public static String generateOwner() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() +" "+ faker.name().lastName();
    }

    public static String generateMonth() {
        Faker faker = new Faker(new Locale("ru"));
        return String.format("%02d", faker.number().numberBetween(1, 12));
    }

    public static String generateYear() {
        Faker faker = new Faker(new Locale("ru"));
        return Integer.toString(faker.number().numberBetween(LocalDate.now().getYear() + 1, LocalDate.now().getYear() + 3) % 100);
    }

    public static String generateCVV() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.number().digits(3);
    }

}
