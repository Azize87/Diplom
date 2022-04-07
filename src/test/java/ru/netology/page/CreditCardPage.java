package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditCardPage {

    private SelenideElement cardForm = $("[class='form form_size_m form_theme_alfa-on-white']");
    private SelenideElement cardNumberField =$(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement ownerField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement codeField = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement buttonContinue = $(byText("Продолжить"));
    private SelenideElement successSendMessage = $$(".notification").first();
    private SelenideElement errorSendMessage = $$(".notification").last();

    private SelenideElement errorMessageCardNumberField = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement errorMessageMonthField = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement errorMessageYearField = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement errorMessageOwnerField = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement errorMessageCodeField = $(byText("CVC/CVV")).parent().$(".input__sub");



    public CreditCardPage(){
        cardForm.shouldBe(visible);
    }

    public void setFields(String cardNumber, String month, String year, String owner, String code) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        codeField.setValue(code);
        buttonContinue.click();
    }

    public String getCardValue(){
        return cardNumberField.getValue();
    }

    public String getYearValue(){
        return  yearField.getValue();
    }

    public String getCodeValue(){
        return codeField.getValue();
    }


    public void successMessageForm() {
        successSendMessage.shouldBe(visible, Duration.ofSeconds(10)).shouldHave(text("Операция одобрена Банком."));
    }


    public void errorMessageForm() {
        errorSendMessage.shouldBe(visible, Duration.ofSeconds(10)).shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }

    //CardNumber Field

    public void validationMessageInvalidCarField() {
        errorMessageCardNumberField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Неверный формат"));
    }

    public void validationMessageCardFieldEmpty() {
        errorMessageCardNumberField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Поле обязательно для заполнения"));
    }

    //Month Field

    public void validationMessageInvalidMonthField() {
        errorMessageMonthField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Неверный формат"));
    }

    public void validationMessageMonthFieldEmpty() {
        errorMessageMonthField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Поле обязательно для заполнения"));
    }

    //Year Field

    public void validationMessageInvalidYearField() {
        errorMessageYearField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Неверный формат"));
    }

    public void validationMessageAboutOutOfDateYear() {
        errorMessageYearField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Истёк срок действия карты"));
    }

    public void validationMessageYearFieldEmpty() {
        errorMessageYearField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Поле обязательно для заполнения"));
    }

    //Owner Field

    public void validationMessageInvalidOwnerField() {
        errorMessageOwnerField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Неверный формат"));
    }

    public void validationMessageOwnerFieldEmpty() {
        errorMessageOwnerField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Поле обязательно для заполнения"));
    }

    //Code Field

    public void validationMessageInvalidCodeField() {
        errorMessageCodeField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Неверный формат"));
    }

    public void validationMessageCodeFieldEmpty() {
        errorMessageCodeField.shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Поле обязательно для заполнения"));
    }
}
