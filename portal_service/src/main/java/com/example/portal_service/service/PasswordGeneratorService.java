package com.example.portal_service.service;


import java.security.SecureRandom;
import java.util.Random;

public class PasswordGeneratorService {
    public static String generatePassword() {

        Random random = new SecureRandom();

        // Строка для хранения пароля
        StringBuilder password = new StringBuilder();

        // Добавляем заглавную букву
        password.append(Character.toUpperCase(random.nextInt(26) + 'A'));
        // Добавляем строчную букву
        password.append(Character.toLowerCase(random.nextInt(26) + 'a'));
        // Добавляем цифру
        password.append(random.nextInt(10));
        // Добавляем три случайных символа (буквы или цифры)
        for (int i = 0; i < 3; i++) {
            password.append((char) (random.nextInt(36) + '0'));
        }

        return password.toString();
    }

    // Закрытый статический член для хранения длины пароля
    private static final int PASSWORD_LENGTH = 6;
}
