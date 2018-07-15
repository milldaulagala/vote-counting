package com.mill.calculation;


import com.mill.election.Candidate;
import com.mill.election.Clash;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static com.mill.calculation.CandidateEliminator.eliminateLastPlace;
import static com.mill.calculation.QuotaCalculator.calculateQuota;
import static com.mill.calculation.VoteCounter.countFirstChoiceVotes;
import static com.mill.calculation.VoteTransferer.distributeSurplus;
import static com.mill.calculation.VoteTransferer.getSurplusWinners;
import static com.mill.calculation.FindWinner.getWinners;

public class VoteAnalyzer {

    private Clash clash;
    private BigDecimal quota;
    private int remainingSeats;
    private int runningCandidates;
    private int rounds;
    private List<Candidate> winners;
    private PriorityQueue<Candidate> haveSurplus;

    public static MathContext mc = new MathContext(3, RoundingMode.DOWN);

    public VoteAnalyzer(Clash clash) {
        this.clash = clash;
        this.remainingSeats = clash.getSeats();
        this.runningCandidates = clash.getCandidates().size();
        rounds = -1;
        winners = new ArrayList<>(clash.getSeats());
        haveSurplus = new PriorityQueue<>();
    }

    public List<Candidate> calculateWinners() {
        int totalVotes = countFirstChoiceVotes(clash.getBallots());
        quota = calculateQuota(totalVotes, clash.getSeats());
        checkForWinners();
        while (remainingSeats > 0) {
            while (!haveSurplus.isEmpty()) {
                distributeSurplus(haveSurplus, quota);
                checkForWinners();
            }
            eliminateLastPlace(clash.getCandidates(), rounds);
            runningCandidates--;
            if (remainingSeats == runningCandidates) {
                declareRunningAsWinners();
                return winners;
            }
            checkForWinners();
        }
        return winners;
    }

    private void checkForWinners() {
        List<Candidate> newWinners = getWinners(clash.getCandidates(), quota);
        remainingSeats -= newWinners.size();
        runningCandidates -= newWinners.size();
        winners.addAll(newWinners);
        for (Candidate c : newWinners) {
            c.notRunning();
        }
        haveSurplus.addAll(getSurplusWinners(newWinners, quota));
        pushRoundVoteTotals();
    }

    private void declareRunningAsWinners() {
        for (Candidate c : clash.getCandidates()) {
            if (c.isRunning()) {
                winners.add(c);
            }
        }
    }

    private void pushRoundVoteTotals() {
        rounds++;
        for (Candidate c : clash.getCandidates()) {
            if (c.isRunning()) {
                c.pushRoundVoteTotal();
            }
        }
    }

}

