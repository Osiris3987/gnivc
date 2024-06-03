package com.example.portal_service.service;


import java.security.SecureRandom;
import java.util.Random;

public class PasswordGeneratorService {

    //TODO refactor password generator algorithm
    public static String generatePassword() {

        Random random = new Random();

        StringBuilder password = new StringBuilder();

        password.append((char)(random.nextInt(26) + 'A'));
        password.append((char)(random.nextInt(26) + 'a'));
        password.append(random.nextInt(10));
        password.append((char)(random.nextInt(26) + 'a'));
        password.append((char)(random.nextInt(26) + 'A'));
        password.append(random.nextInt(10));

        return password.toString();
    }

    // Закрытый статический член для хранения длины пароля
    private static final int PASSWORD_LENGTH = 6;
}
