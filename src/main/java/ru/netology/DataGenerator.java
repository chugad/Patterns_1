package ru.netology;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String randomCity() {
        String[] city = new String[]{"Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас", "Нальчик", "Элиста", "Черкесск", "Петрозаводск", "Сыктывкар", "Симферополь", "Йошкар-Ола", "Саранск", "Якутск", "Владикавказ", "Казань", "Кызыл", "Ижевск", "Абакан", "Грозный", "Чебоксары", "Барнаул", "Чита", "Петропавловск-Камчатский", "Краснодар", "Красноярск", "Пермь", "Владивосток", "Ставрополь", "Хабаровск", "Благовещенск", "Архангельск", "Астрахань", "Белгород", "Брянск", "Владимир", "Волгоград", "Вологда", "Воронеж", "Иваново", "Иркутск", "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Курган", "Курск", "Санкт-Петербург", "Липецк", "Магадан", "Москва", "Красногорск", "Мурманск", "Нижний Новгород", "Великий Новгород", "Новосибирск", "Омск", "Оренбург", "Орёл", "Пенза", "Псков", "Ростов-на-Дону", "Рязань", "Самара", "Саратов", "Южно-Сахалинск", "Екатеринбург", "Смоленск", "Тамбов", "Тверь", "Томск", "Тула", "Тюмень", "Ульяновск", "Челябинск", "Ярославль", "Москва", "Санкт-Петербург", "Севастополь", "Биробиджан", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь", "Салехард"
        };
        int randomIndex = new Random().nextInt(city.length);
        String randomCity = city[randomIndex];
        return randomCity;
    }

    public static String randomDate() {
        Faker faker = new Faker(new Locale("ru"));
        long randomCount = faker.number().numberBetween(1, 9);
        String randomDate = LocalDate.now().plusDays(3).plusDays(randomCount).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return randomDate;
    }

    public static String dateAfterThreeDays() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String randomName() {
        Faker faker = new Faker(new Locale("ru"));
        String randomName1 = faker.name().lastName();
        String randomName2 = faker.name().firstName();
        String randomName = randomName1 + " " + randomName2;
        return randomName;
    }

    public static String randomInvalidName() {
        Faker faker = new Faker();
        String randomInvalidName = faker.harryPotter().character();
        return randomInvalidName;
    }

    public static String randomPhone() {
        Faker faker = new Faker(new Locale("ru"));
        String randomPhone = faker.phoneNumber().phoneNumber();
        return randomPhone;
    }

    public static String randomInvalidPhone() {
        Faker faker = new Faker(new Locale("ru"));
        String randomInvalidPhone = faker.phoneNumber().subscriberNumber(faker.number().numberBetween(1, 10));
        return randomInvalidPhone;
    }
}
