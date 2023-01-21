package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class OpenCardTest {

    public String generateDate(long addDays, String pattern) {

        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));

    }

    @Test
    void shouldTestCardForm() {

        long daysToAdd = 3;

        LocalDate date = LocalDate.now();
        date = date.plusDays(daysToAdd);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LL.yyyy");
        String dataFormatter = date.format(formatter);

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id='city'] input").setValue("Екатеринбург");
        $("span[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a" + Keys.BACK_SPACE);
        $("span[data-test-id='date'] input").setValue(dataFormatter);
        $("span[data-test-id='name'] input").setValue("Иванов Иван");
        $("span[data-test-id='phone'] input").setValue("+79225788643");
        $$("label[data-test-id='agreement']").find(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $$("button").find(exactText("Забронировать")).click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
        $(".notification__content")

                .shouldHave(Condition.text("Встреча успешно забронирована на " + dataFormatter), Duration.ofSeconds(15))

                .shouldBe(Condition.visible);
    }

    @Test
    void shouldTestCardFormPopup() {

        int daysToAdd = 7;

        LocalDate date = LocalDate.now();
        date = date.plusDays(daysToAdd);

        int day = date.getDayOfMonth();

        String dataFormatter = generateDate(daysToAdd, "dd.LL.yyyy");

        LocalDate currentDate = LocalDate.now();

        int currentDay = currentDate.getDayOfMonth();

        int monthMaxDays = date.lengthOfMonth();

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("span[data-test-id='city'] input").setValue("Ек");
        $x("//span[contains(text(), 'Екатеринбург')]").hover().click();
        $("span[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

        if (currentDay + daysToAdd > monthMaxDays) {
            $x("//div[@class='calendar__arrow calendar__arrow_direction_right']").should(appear, Duration.ofSeconds(7)).hover().click();
        }

        $x("//td[contains(text(), '" + day + "')]").should(appear, Duration.ofSeconds(7)).hover().click();
        $("span[data-test-id='name'] input").setValue("Иванов Иван");
        $("span[data-test-id='phone'] input").setValue("+79225788643");
        $$("label[data-test-id='agreement']").find(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).click();
        $$("button").find(exactText("Забронировать")).click();
        $x("//div[contains(text(), 'Успешно!')]").should(appear, Duration.ofSeconds(15));
        $(".notification__content")

                .shouldHave(Condition.text("Встреча успешно забронирована на " + dataFormatter), Duration.ofSeconds(15))

                .shouldBe(Condition.visible);
    }
}

