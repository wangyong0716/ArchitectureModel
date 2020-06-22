package com.archi.architecture.lottery.plan;

import java.util.Random;

public class RandomSSPlan extends BaseSSPlan {
    private static final String TAG = "RandomSSPlan";

    @Override
    protected int[] getBuyReds() {
        return randomReds();
    }

    @Override
    protected int getBuyBlue() {
        Random random = new Random();
        return random.nextInt(16) + 1;
    }

    @Override
    protected long getBuyCount() {
        return 1;
    }

    @Override
    public void activate() {
        do {
            if (!buy()) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }
}
