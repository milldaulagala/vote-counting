package com.mill.calculation;

import com.mill.election.Ballot;
import com.mill.election.Candidate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static com.mill.calculation.VoteAnalyzer.mc;

public class VoteTransferer {

    public static void distributeSurplus(PriorityQueue<Candidate> extraVotes, BigDecimal quota) {
        Candidate hasLargestExtra = extraVotes.poll();
        BigDecimal totalVotes = hasLargestExtra.getVoteTotal();
        BigDecimal surplus = totalVotes.subtract(quota, mc);
        BigDecimal transferValue = surplus.divide(totalVotes, mc);
        for (Ballot b : hasLargestExtra.getVotes()) {
            b.setValue(transferValue.multiply(b.getValue(), mc));
            transferVotes(hasLargestExtra, b);
        }
    }

    public static void transferVotes(Candidate from, Ballot ballot) {
        from.subtractVotes(ballot);
        Candidate nextPreferred = getNextEligiblePreferred(ballot);
        if (nextPreferred != null) {
            nextPreferred.addVotes(ballot);
        }
    }

    public static List<Candidate> getSurplusWinners(List<Candidate> candidates, BigDecimal quota) {
        List<Candidate> extraVotes = new ArrayList<>();
        for (Candidate c : candidates) {
            if (c.getVoteTotal().compareTo(quota) > 0) {
                extraVotes.add(c);
            }
        }
        return extraVotes;
    }

    private static Candidate getNextEligiblePreferred(Ballot ballot) {
        Candidate nextPreferred = ballot.getNextPreferred();
        while (nextPreferred != null && !nextPreferred.isRunning()) {
            nextPreferred = ballot.getNextPreferred();
        }
        return nextPreferred;
    }


}