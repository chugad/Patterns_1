package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {
    String city = AppCardDelivery.randomCity();
    String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    String date2 = AppCardDelivery.randomDate();
    String name = AppCardDelivery.randomName();
    String phone = AppCardDelivery.randomPhone();

    @BeforeEach
    void shouldStartBeforeEachTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestHappyPath() {
        $("[data-test-id=city] .input__control").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__title").waitUntil(text("Успешно!"), 15000).shouldBe(visible);
        $("[data-test-id='success-notification'] .notification__content").waitUntil(text("Встреча успешно запланирована на " + date), 15000).shouldBe(visible);
    }

    @Test
    void happyPathError() {// ссылка на ишью
        $("[data-test-id=city] .input__control").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Фёдор Фёдорович Свёклов");
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__title").waitUntil(text("Успешно!"), 15000).shouldBe(visible);
        $("[data-test-id='success-notification'] .notification__content").waitUntil(text("Встреча успешно запланирована на " + date), 15000).shouldBe(visible);
    }

    @Test
    void shouldTestHappyPath2() { //проверка переназначения встречи на другое число
        $("[data-test-id=city] .input__control").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id='name'] input").setValue(AppCardDelivery.randomName());
        $("[data-test-id=phone] input").setValue(AppCardDelivery.randomPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__title").waitUntil(text("Успешно!"), 15000).shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content").waitUntil(exist, 15000).shouldHave(text("Встреча успешно запланирована на " + date));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date2);
        $(byText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content").waitUntil(exist, 15000).shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id=replan-notification] .button__text").click();
        $("[data-test-id='success-notification'] .notification__title").waitUntil(text("Успешно!"), 15000).shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content").waitUntil(exist, 15000).shouldHave(text("Встреча успешно запланирована на " + date2));
    }

    @Test
    void shouldTestPathIfEmptyCity() {
        $("[data-test-id=city] .input__control").setValue("");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestPathIfCityIsNotCapital() {
        $("[data-test-id=city] .input__control").setValue("Бийск");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestPathDateEmpty() {

        $("[data-test-id=city] .input__control").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue("");
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldTestPathDateTwoDays() {
        String dateTwoDays = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] .input__control").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(dateTwoDays);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestPathDateNow() {
        String dateNow = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] .input__control").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(dateNow);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestPathIfEmptyName() {
        $("[data-test-id=city] .input__control").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='name'] .input__sub").shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestPathIfInvalidName() {
        $("[data-test-id=city] .input__control").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Lelby Jktu");
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldBe(visible).shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestPathIfInvalidPhoneUnderLimitDown() { // тест должен упасть, ссылка на ишью:
        $("[data-test-id=city] .input__control").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id=phone] input").setValue("7923");
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestPathIfEmptyPhoneNumber() { // тест должен упасть, ссылка на ишью:
        $("[data-test-id=city] .input__control").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestPathWithOutCheckbox() {
        $("[data-test-id=city] .input__control").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id='name'] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $(byText("Запланировать")).click();
        $("[data-test-id='agreement'].input_invalid [role]").shouldBe(visible).shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
