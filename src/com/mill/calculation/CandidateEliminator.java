package com.mill.calculation;

import com.mill.election.Ballot;
import com.mill.election.Candidate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.mill.calculation.VoteAnalyzer.mc;
import static com.mill.calculation.VoteTransferer.transferVotes;

/**
 * This class - Eliminate the candidate who has least number of votes.
 */

public class CandidateEliminator {

    public static void eliminateLastPlace(List<Candidate> candidates, int rounds) {
        int roundsAgo = 0;
        List<Candidate> lastPlaceList = getLastPlace(candidates, roundsAgo);
        while (lastPlaceList.size() > 1) {


            /**
             * If there is a tie between two or more leading candidates and there are no other candidates that
             * can be eliminated, one candidate should be chosen at random for elimination.
             */
            if (roundsAgo >= rounds) {
                eliminateRandomCandidate(lastPlaceList);
                return;
            }
            roundsAgo++;
            lastPlaceList = getLastPlace(lastPlaceList, roundsAgo);
        }
        eliminateCandidate(lastPlaceList.get(0));
    }

    private static List<Candidate> getLastPlace(List<Candidate> candidates, int roundsAgo) {
        BigDecimal fewestVotes = new BigDecimal(Integer.MAX_VALUE, mc);
        List<Candidate> lastPlaceList = new ArrayList<>();
        for (Candidate c : candidates) {
            if (c.isRunning()) {
                BigDecimal candidateVoteTotal = c.getVoteTotal(roundsAgo);
                if (candidateVoteTotal.compareTo(fewestVotes) == 0) {
                    //Tie for fewest votes
                    lastPlaceList.add(c);
                } else if (candidateVoteTotal.compareTo(fewestVotes) < 0) {
                    //New fewest votes
                    fewestVotes = candidateVoteTotal;
                    lastPlaceList.clear();
                    lastPlaceList.add(c);
                }
            }
        }
        return lastPlaceList;
    }

    private static void eliminateRandomCandidate(List<Candidate> lastPlaceList) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, lastPlaceList.size());
        eliminateCandidate(lastPlaceList.get(randomIndex));
    }

    private static void eliminateCandidate(Candidate toEliminate) {
        toEliminate.notRunning();
        for (Ballot b : toEliminate.getVotes()) {
            transferVotes(toEliminate, b);
        }
    }
}

