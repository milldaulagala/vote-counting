package com.mill.calculation;

import com.mill.election.Ballot;
import java.util.List;

public class VoteCounter {

    public static int countFirstChoiceVotes(List<Ballot> ballots) {
        int total = 0;
        for (Ballot b : ballots) {
            b.getNextPreferred().addVotes(b);
            total++;
        }
        return total;
    }

}
