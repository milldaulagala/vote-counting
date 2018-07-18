package test;

import com.mill.calculation.VoteAnalyzer;
import com.mill.election.Ballot;
import com.mill.election.Candidate;
import com.mill.election.Clash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

public class VoteAnalyzerTest {

    private Candidate WineryTour;
    private Candidate TenPinBowling;
    private Candidate MovieNight;
    private Candidate DinnerAtRestaurant;
    private Candidate PicnicInThePark;
    private List<Candidate> candidates;
    private List<Ballot> ballots;

    @BeforeEach
    public void setup() {
        WineryTour = new Candidate("WineryTour");
        TenPinBowling = new Candidate("TenPinBowling");
        MovieNight = new Candidate("MovieNight");
        DinnerAtRestaurant = new Candidate("DinnerAtRestaurant");
        PicnicInThePark = new Candidate("PicnicInThePark");
        candidates = new ArrayList<>();
        candidates.add(WineryTour);
        candidates.add(TenPinBowling);
        candidates.add(MovieNight);
        candidates.add(DinnerAtRestaurant);
        candidates.add(PicnicInThePark);
        ballots = new ArrayList<>();
    }

    @Test
    public void calculateWinnerTest() {


        /**    Wine Tenpin  Movie  Dinner Picnic
        *      4      2      12      1      1           <-- MovieNight wins, distribute extras(6). quota= 6 , extra= 12-quota
        *      4      2       6      5      3           <-- TenPinBowling last, eliminate and distribute votes
        *      6      0       6      5      3           <-- PicnicInThePark last, eliminate
        *      6      0       6      5      0           <-- Final vote totals
        */

        Queue<Candidate> ranking1 = new ArrayDeque<>();
        Queue<Candidate> ranking2 = new ArrayDeque<>();
        Queue<Candidate> ranking3 = new ArrayDeque<>();
        Queue<Candidate> ranking4 = new ArrayDeque<>();
        Queue<Candidate> ranking5 = new ArrayDeque<>();
        Queue<Candidate> ranking6 = new ArrayDeque<>();

        ranking1.add(WineryTour);
        ranking2.add(TenPinBowling);
        ranking2.add(WineryTour);
        ranking3.add(MovieNight);
        ranking3.add(DinnerAtRestaurant);
        ranking4.add(MovieNight);
        ranking4.add(PicnicInThePark);
        ranking5.add(DinnerAtRestaurant);
        ranking6.add(PicnicInThePark);

        for (int i = 0; i < 4; i++) {
            ballots.add(new Ballot(new ArrayDeque<>(ranking1)));

        }
        for (int i = 0; i < 2; i++) {
            ballots.add(new Ballot(new ArrayDeque<>(ranking2)));

        }
        for (int i = 0; i < 8; i++) {
            ballots.add(new Ballot(new ArrayDeque<>(ranking3)));

        }
        for (int i = 0; i < 4; i++) {
            ballots.add(new Ballot(new ArrayDeque<>(ranking4)));
        }
        ballots.add(new Ballot(new ArrayDeque<>(ranking5)));
        ballots.add(new Ballot(new ArrayDeque<>(ranking6)));

        Clash cl = new Clash("Celebrate successful software release", ballots, candidates);
        VoteAnalyzer va = new VoteAnalyzer(cl);
        assertThat(va.calculateWinners()).containsExactly(MovieNight);
    }

    @Test
    public void calculateWinnersOneWinnerTest() {

         /**    WineTour  Tenpin   Movie  Dinner  Picnic
         *      9           1       10      0       0     <-- Dinner, Picnic eliminated. Tenpin last place, eliminated, distribute extras.
         *      9           0       11      0       0     <-- Movie wins
         */

        Queue<Candidate> ranking1 = new ArrayDeque<>();
        Queue<Candidate> ranking2 = new ArrayDeque<>();
        Queue<Candidate> ranking3 = new ArrayDeque<>();

        ranking1.add(MovieNight);
        ranking2.add(WineryTour);
        ranking3.add(TenPinBowling);
        ranking3.add(MovieNight);

        for (int i = 0; i < 10; i++) {
            ballots.add(new Ballot(new ArrayDeque<>(ranking1)));
        }
        for (int i = 0; i < 9; i++) {
            ballots.add(new Ballot(new ArrayDeque<>(ranking2)));
        }
        ballots.add(new Ballot(new ArrayDeque<>(ranking3)));

        Clash cl = new Clash("Select one winner", ballots, candidates);
        VoteAnalyzer va = new VoteAnalyzer(cl);
        assertThat(va.calculateWinners()).containsExactly(MovieNight);
    }
}
