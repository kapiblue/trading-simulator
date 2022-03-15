/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kapiblue.tradingsimulator.utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class that stores functions for generating random strings and date
 *
 * @author kapib
 */
public class Generator {

    /**
     * Parameterless constructor
     */
    public Generator() {
    }

    /**
     * Generates a random String of a given length. Uses capital letters.
     *
     * @param length
     * @return
     */
    public String generateRandomWord(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char letter = alphabet.charAt(index);
            builder.append(letter);
        }
        String randomString = builder.toString();
        return randomString;
    }

    /**
     * Generates a random date from an interval.
     *
     * @return
     */
    public Date generateRandomDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1995, 1, 1);
        Date startDate = calendar.getTime();
        calendar.set(2019, 1, 1);
        Date endDate = calendar.getTime();
        long random = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
        Date date = new Date(random);
        return date;
    }
}
