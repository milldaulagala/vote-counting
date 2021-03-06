package com.mill.calculation;

import com.mill.election.Candidate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class - Find the winner.
 */

public class FindWinner {

    public static List<Candidate> getWinners(List<Candidate> candidates, BigDecimal quota) {
        List<Candidate> winners = new ArrayList<>();
        for (Candidate c : candidates) {
            if (c.isRunning() && c.getVoteTotal().compareTo(quota) >= 0) {
                winners.add(c);
            }
        }
        return winners;
    }

}

