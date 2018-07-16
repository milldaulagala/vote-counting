package com.mill.calculation;

import java.math.BigDecimal;

import static com.mill.calculation.VoteAnalyzer.mc;

/**
 * This class - Calculate the Quota.
 * Quota = (number of non-exhausted ballots /(seats + 1)) + 1
 * This is to be calculated at each round whenever needed.
 */

public class QuotaCalculator {

    public static BigDecimal calculateQuota(int totalVotes, int seats) {

        return new BigDecimal((totalVotes / (seats + 1)) + 1, mc);
    }

}
