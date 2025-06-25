package de.hems.backend;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Bcrypttest {
    public static void main(String[] args) {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        long start = System.currentTimeMillis();
        for (int i = 4; i < 33; i++) {
            System.out.println(System.currentTimeMillis());
            System.out.println(new String(hasher.hash(i, "12345678".toCharArray())));
            System.out.println(System.currentTimeMillis() - start);
            System.out.println("Cost: " + i + "time:" + (System.currentTimeMillis() - start) / 1000 + "Sekunden");
        }
    }
}
