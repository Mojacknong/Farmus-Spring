package com.modernfarmer.farmusspring.domain.user.util;

import java.util.Random;

public class RamdomFunction {

    private static final Random RANDOM = new Random();

    public static int getRandomIntInRange(int start, int end) {
        return RANDOM.nextInt(end - start + 1) + start;
    }
}
