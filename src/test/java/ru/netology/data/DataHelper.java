package ru.netology.data;


import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }


    public static CardInfo getValidCardInfo() {
        return new CardInfo("4444 4444 4444 4441", DataGenerator.generateMonth(), DataGenerator.generateYear(), DataGenerator.generateOwner(), DataGenerator.generateCVV());
    }

    public static CardInfo getInvalidCardInfo() {
        return new CardInfo("4444 4444 4444 4442", DataGenerator.generateMonth(), DataGenerator.generateYear(), DataGenerator.generateOwner(), DataGenerator.generateCVV());
    }


    @Value
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String owner;
        private String CVV;

    }


}
