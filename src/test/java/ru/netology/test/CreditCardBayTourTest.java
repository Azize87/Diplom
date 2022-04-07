package ru.netology.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.Database;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;

public class CreditCardBayTourTest {

    //Database database = new Database(System.getProperty("dbUrl"), System.getProperty("dbUser"), System.getProperty("dbPass"));
    Database database = new Database("jdbc:mysql://localhost:3306/db", System.getProperty("dbUser"), System.getProperty("dbPass"));

    @BeforeEach
    void setup() {
        open("http://localhost:8080/");
        Configuration.holdBrowserOpen = true;

        database.clearDB();
    }

    @Test
    void shouldByWithCreditCardSuccess() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), validCardInfo.getCVV());

        creditCardPage.successMessageForm();

        assertEquals("APPROVED", database.getCreditCardTransactionStatus());
    }

    @Test
    void shouldByWithCreditCardError() {

        val validCardInfo = DataHelper.getInvalidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), validCardInfo.getCVV());
        creditCardPage.errorMessageForm();

        assertEquals("DECLINED", database.getCreditCardTransactionStatus());
    }



    // sad path  Поле "Владелец"

    // Баг. Неверное сообщение об успешности операции, при заполнении Владелец одной буквой. Запрос уходит на сервер при неверном заполнении поля Владелец.
    @Test
    void shouldBuyWithOneLetterOwnerError() {

        val validCardInfo = DataHelper.getInvalidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), validCardInfo.getYear(), "J", validCardInfo.getCVV());
        creditCardPage.validationMessageInvalidOwnerField();


        assertNull(database.getCreditCardTransactionStatus());
    }

// баг. неверное сообщение об успешности операции , при заполнении Владелец на кириллице.
//Неверный формат

    @Test
    void shouldBuyWithCyrillicLetterOwnerError() {

        val validCardInfo = DataHelper.getInvalidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), validCardInfo.getYear(), "ИВАН ИВАНОВ", validCardInfo.getCVV());
        creditCardPage.validationMessageInvalidOwnerField();


        assertNull(database.getCreditCardTransactionStatus());
    }

    // баг. неверное сообщение об успешности операции, при заполнении Владелец символами
    //Неверный формат

    @Test
    void shouldBuyWithSymbolsOwnerError() {

        val validCardInfo = DataHelper.getInvalidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), validCardInfo.getYear(), "!#&*}/", validCardInfo.getCVV());
        creditCardPage.validationMessageInvalidOwnerField();


        assertNull(database.getCreditCardTransactionStatus());
    }

    // поле Владелец не заполнено
    @Test
    void shouldErrorWithEmptyOwnerField() {

        val validCardInfo = DataHelper.getInvalidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), validCardInfo.getYear(), "", validCardInfo.getCVV());
        creditCardPage.validationMessageOwnerFieldEmpty();
        assertNull(database.getCreditCardTransactionStatus());

    }

    // поле Номер карты неверный формат(меньше необходимого)
    @Test
    void shouldErrorWithInvalidLengthFormatCardField() {
        val validCardInfo = DataHelper.getInvalidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields("5558 8877 7889", validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), validCardInfo.getCVV());
        creditCardPage.validationMessageInvalidCarField();
        assertNull(database.getCreditCardTransactionStatus());
    }


    // Поле "Номер карты" из БД спецсимволами

    @Test
    void shouldErrorWithSymbolsInCardField() {

        val validCardInfo = DataHelper.getInvalidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields("%$", validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), validCardInfo.getCVV());


        assertEquals("", creditCardPage.getCardValue());
    }

    // поле Номер карты на латыни
    @Test
    void shouldErrorWithLatinLettersInCardField() {

        val validCardInfo = DataHelper.getInvalidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields("RM", validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), validCardInfo.getCVV());


        assertEquals("", creditCardPage.getCardValue());
    }

    // поле Номер карты кириллицей
    @Test
    void shouldErrorWithCyrillicLettersInCardField() {

        val validCardInfo = DataHelper.getInvalidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields("П", validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), validCardInfo.getCVV());

        assertEquals("", creditCardPage.getCardValue());
    }

    // поле Номер карты не заполнено
    // Сообщение должно быть: Поле обязательно для заполнения
    @Test
    void shouldErrorWithEmptyCardField() {
        val validCardInfo = DataHelper.getInvalidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields("", validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), validCardInfo.getCVV());
        creditCardPage.validationMessageCardFieldEmpty();

        assertNull(database.getCreditCardTransactionStatus());

    }


    // Поле "Срок действия карты" год -  Ввод неверного формата Х= 1 цифра

    @Test
    void shouldErrorInvalidFormatOfTheYearField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), "5", validCardInfo.getOwner(), validCardInfo.getCVV());
        creditCardPage.validationMessageInvalidYearField();
        assertNull(database.getCreditCardTransactionStatus());

    }

    // Поле Год с истекшим сроком

    @Test
    void shouldErrorWhenCardIsExpired() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), "20", validCardInfo.getOwner(), validCardInfo.getCVV());
        creditCardPage.validationMessageAboutOutOfDateYear();
        assertNull(database.getCreditCardTransactionStatus());

    }

    // поле Год - использование спецсимволов

    @Test
    void shouldErrorWithSymbolsInYearField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), "!$", validCardInfo.getOwner(), validCardInfo.getCVV());

        assertEquals("", creditCardPage.getYearValue());
    }


    // поле Год - алфавит на латыни
    @Test
    void shouldErrorWithLatinLettersInYearField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), "RM", validCardInfo.getOwner(), validCardInfo.getCVV());


        assertEquals("", creditCardPage.getYearValue());
    }

    // поле Год - алфавит на кириллице
    @Test
    void shouldErrorWithCyrillicLettersInYearField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), "КД", validCardInfo.getOwner(), validCardInfo.getCVV());


        assertEquals("", creditCardPage.getYearValue());

    }

    // поле Год - пустое
    // Сообщение должно быть: Поле обязательно для заполнения
    @Test
    void shouldErrorWithEmptyYearField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), "", validCardInfo.getOwner(), validCardInfo.getCVV());
        creditCardPage.validationMessageYearFieldEmpty();
        assertNull(database.getCreditCardTransactionStatus());
    }

    //поле Месяц неверный формат 1 цифра
    @Test
    void shouldErrorInvalidFormatOfTheMonthField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), "1", validCardInfo.getYear(), validCardInfo.getOwner(), validCardInfo.getCVV());
        creditCardPage.validationMessageInvalidMonthField();

        assertNull(database.getCreditCardTransactionStatus());

    }

    // поле месяц не заполнено
    // Сообщение должно быть: Поле обязательно для заполнения
    @Test
    void shouldErrorWithEmptyMonthField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), "", validCardInfo.getYear(), validCardInfo.getOwner(), validCardInfo.getCVV());
        creditCardPage.validationMessageMonthFieldEmpty();

        assertNull(database.getCreditCardTransactionStatus());


    }

    // поле CVC/CVV ввод неверного формата 2 цифры
    @Test
    void shouldErrorInvalidFormatOfTheCodeField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), "22");

        creditCardPage.validationMessageInvalidCodeField();

        assertNull(database.getCreditCardTransactionStatus());
    }

    // поле CVC/CVV  ввод спецсимволов
    // Появляется лишнее сообщение для владельца, и не верное сообщение для CVV. После заполнения поля, ошибки валидации остаются
    @Test
    void shouldErrorWithSymbolsInCodeField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), "!");


        assertEquals("", creditCardPage.getCodeValue());
    }

    // поле CVC/CVV ввод алфавита на латыни
    @Test
    void shouldErrorWithLatinLettersInCodeField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), "RM");


        assertEquals("", creditCardPage.getCodeValue());
    }

    // поле CVC/CVV ввод алфавита на кириллице
    @Test
    void shouldErrorWithCyrillicLettersInCodeField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), "Д");


        assertEquals("", creditCardPage.getCodeValue());

    }

    // поле CVC/CVV не заполнено
    // Сообщение должно быть: Поле обязательно для заполнения
    @Test
    void shouldErrorWithEmptyCodeField() {

        val validCardInfo = DataHelper.getValidCardInfo();

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithCreditCard();
        creditCardPage.setFields(validCardInfo.getCardNumber(), validCardInfo.getMonth(), validCardInfo.getYear(), validCardInfo.getOwner(), "");
        creditCardPage.validationMessageCodeFieldEmpty();


        assertNull(database.getCreditCardTransactionStatus());

    }

    // отправка пустой формы
    @Test
    void shouldErrorWithEmptyFields() {

        val mainPage = new MainPage();
        val creditCardPage = mainPage.byWithDebitCard();
        creditCardPage.setFields("", "", "", "", "");
        creditCardPage.validationMessageCardFieldEmpty();
        creditCardPage.validationMessageMonthFieldEmpty();
        creditCardPage.validationMessageYearFieldEmpty();
        creditCardPage.validationMessageOwnerFieldEmpty();
        creditCardPage.validationMessageCodeFieldEmpty();

        assertNull(database.getCreditCardTransactionStatus());
    }

}
