package ru.netology.data;

import lombok.SneakyThrows;

public class WaitHelper {

    @SneakyThrows
    public static void Wait(int ms){
        Thread.sleep(ms);
    }

}
