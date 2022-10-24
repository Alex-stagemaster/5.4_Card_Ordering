package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;




public class CardOrderingTest {


     @BeforeEach
       public void setUp() {
        open("http://localhost:9999");

    }


    @Test
    void shouldSubmit() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("Иван Иванов");
        form.$("[data-test-id='phone'] input").setValue("+79123456789");
        form.$("[data-test-id='agreement']").click();
        form.$("[type='button']").click();
        $("[data-test-id=order-success]").
                shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitWithHyphen() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("Иван Иванов-Иванов");
        form.$("[data-test-id='phone'] input").setValue("+79123456789");
        form.$("[data-test-id='agreement']").click();
        form.$("[type='button']").click();
        $("[data-test-id=order-success]").
                shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldErrorEmptyName() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("");
        form.$("[data-test-id='phone'] input").setValue("+79123456789");
        form.$("[data-test-id='agreement']").click();
        form.$("[type='button']").click();
        form.$("[data-test-id='name'].input_invalid .input__sub").
                shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorEmptyPhone() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("Иван Иванов");
        form.$("[data-test-id='phone'] input").setValue("");
        form.$("[data-test-id='agreement']").click();
        form.$("[type='button']").click();
        form.$("[data-test-id='phone'].input_invalid .input__sub").
                shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorCheckboxNotChecked() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("Иван Иванов");
        form.$("[data-test-id='phone'] input").setValue("+79123456789");
        form.$(".button").click();
        form.$(".input_invalid").$(".checkbox__text").
                shouldHave(exactText("Я соглашаюсь с условиями обработки" +
                        " и использования моих персональных данных" +
                        " и разрешаю сделать запрос в бюро кредитных историй"));
    }

    @Test
    void shouldErrorWrongName() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("Ivan Ivanov");
        form.$("[data-test-id='phone'] input").setValue("+79123456789");
        form.$("[data-test-id='agreement']").click();
        form.$("[type='button']").click();
        form.$("[data-test-id='name'].input_invalid .input__sub").
                shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldErrorSpecialSymbolName() {
        SelenideElement form = $("form");
        form.$("[data-test-id='name'] input").setValue("!");
        form.$("[data-test-id='phone'] input").setValue("+79123456789");
        form.$("[data-test-id='agreement']").click();
        form.$("[type='button']").click();
        form.$("[data-test-id='name'].input_invalid .input__sub").
                shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldErrorWrongPhone() {
        SelenideElement form = $("form");
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("71234567890199");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$("[data-test-id=phone].input_invalid .input__sub").
                shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}