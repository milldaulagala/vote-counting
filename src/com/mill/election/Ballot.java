package com.mill.election;

import java.math.BigDecimal;
import java.util.Queue;

import static com.mill.calculation.VoteAnalyzer.mc;

/**
 * This Class has candidates Queue with ranking.
 * We can get the top ranking candidate using poll() method.
 * The poll() method remove the top ranking candidate from Queue.
 */

public class Ballot {

    private Queue<Candidate> ranking;
    private BigDecimal value;

    public Ballot(Queue<Candidate> ranking) {
        this.ranking = ranking;
        value = new BigDecimal(1, mc);
    }

    public Candidate getNextPreferred() {
        return ranking.poll();
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

}
