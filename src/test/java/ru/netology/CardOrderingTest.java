package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderingTest {

    SelenideElement form;

    @BeforeEach
    void setUp() {
        form = $(".form");
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmit() {
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("+79123456789");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitWithHyphen() {
        form.$("[data-test-id=name] input").setValue("Иван Иванов-Иванов");
        form.$("[data-test-id=phone] input").setValue("+79123456789");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        $("[data-test-id='order-success']").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldErrorEmptyForm() {
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorEmptyName() {
        form.$("[data-test-id=phone] input").setValue("+79123456789");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorEmptyPhone() {
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldErrorCheckboxNotChecked(){
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("+79123456789");
        form.$(".button").click();
        form.$(".input_invalid[data-test-id=agreement]").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }

    @Test
    void shouldErrorWrongName() {
        form.$("[data-test-id=name] input").setValue("Ivan Ivanov");
        form.$("[data-test-id=phone] input").setValue("+79123456789");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldErrorSpecialSymbolName() {
        form.$("[data-test-id=name] input").setValue("!");
        form.$("[data-test-id=phone] input").setValue("+79123456789");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldErrorWrongPhone() {
        form.$("[data-test-id=name] input").setValue("Иван Иванов");
        form.$("[data-test-id=phone] input").setValue("+79123456789");
        form.$("[data-test-id=agreement]").click();
        form.$("[type='button']").click();
        form.$(".input_theme_alfa-on-white.input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, ++79123456789."));
    }
}