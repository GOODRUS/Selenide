package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

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

        String months;

        if (month < 10) {
            months = "0" + String.valueOf(month);

        } else {
            months = String.valueOf(month);
        }

        int day = date.getDayOfMonth();

        String days;

        if (day < 10) {
            days = "0" + String.valueOf(day);
        } else {
            days = String.valueOf(day);
        }

        String dataFinal = days + "." + months + "." + year;

        Configuration.headless = true;
        open("http://localhost:9999");
        $("span[data-test-id='city'] input").setValue("Екатеринбург");
        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        $("span[data-test-id='date'] input").setValue(String.valueOf(dataFinal));
        $("span[data-test-id='name'] input").setValue("Иванов Иван");
        $("span[data-test-id='phone'] input").setValue("+79225788643");
        $$("label[data-test-id='agreement']").find(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $$("button").find(exactText("Забронировать")).click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
    }
    
    @Test
    void shouldTestCardFormPopup() {

        int daysToAdd = 7;

        LocalDate date = LocalDate.now();
        date = date.plusDays(daysToAdd);

        int day = date.getDayOfMonth();

        Date currentDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);

        int monthMaxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        Configuration.headless = true;
        open("http://localhost:9999");
        $("span[data-test-id='city'] input").setValue("Ек");
        $x("//span[contains(text(), 'Екатеринбург')]").hover().click();
        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);

        if (currentDay + daysToAdd > monthMaxDays) {
            $x("//div[@class='calendar__arrow calendar__arrow_direction_right']").should(appear, Duration.ofSeconds(7)).hover().click();
        }

        $x("//td[contains(text(), '" + day + "')]").should(appear, Duration.ofSeconds(7)).hover().click();
        $("span[data-test-id='name'] input").setValue("Иванов Иван");
        $("span[data-test-id='phone'] input").setValue("+79225788643");
        $$("label[data-test-id='agreement']").find(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $$("button").find(exactText("Забронировать")).click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
    }
}

