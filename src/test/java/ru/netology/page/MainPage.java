package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private SelenideElement debitButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));

    public DebitCardPage byWithDebitCard() {
         debitButton.click();
         return new DebitCardPage();
    }

    public CreditCardPage byWithCreditCard() {
        creditButton.click();
        return new CreditCardPage();
    }
}
