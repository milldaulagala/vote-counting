package com.mill.election;

import java.util.List;

public class Clash {

    private int seats;
    private String title;
    private List<Candidate> candidates;
    private List<Ballot> ballots;

    public Clash(String title, List<Ballot> ballots, List<Candidate> candidates) {
        this.title = title;
        this.seats = 1;
        this.ballots = ballots;
        this.candidates = candidates;
    }

    public int getSeats() {
        return seats;
    }

    public List<Ballot> getBallots() {
        return ballots;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

}

