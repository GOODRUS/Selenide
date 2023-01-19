package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class OpenCardTest {
    @Test
    void shouldTestCardForm() {

        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String dateFinal = day + "." + "0" + month + "." + year;

        Configuration.headless = true;
        open("http://localhost:9999");
        $("span[data-test-id='city'] input").setValue("Екатеринбург");
        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        $("span[data-test-id='date'] input").setValue(String.valueOf(dateFinal));
        $("span[data-test-id='name'] input").setValue("Иванов Иван");
        $("span[data-test-id='phone'] input").setValue("+79225788643");
        $$("label[data-test-id='agreement']").find(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $$("button").find(exactText("Забронировать")).click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
    }
}

