package com.mill.calculation;

import java.math.BigDecimal;

import static com.mill.calculation.VoteAnalyzer.mc;

public class QuotaCalculator {

    public static BigDecimal calculateQuota(int totalVotes, int seats) {
        //Integer division is intentional and used in Droop quota formula
        return new BigDecimal((totalVotes / (seats + 1)) + 1, mc);
    }

}
