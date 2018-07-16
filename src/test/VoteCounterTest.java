package test;

import com.mill.election.Ballot;
import com.mill.election.Candidate;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static com.mill.calculation.VoteCounter.countFirstChoiceVotes;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteCounterTest {

    @Test
    public void countFirstChoiceVotesTest() {

        List<Ballot> ballotList = new ArrayList<>();

        Candidate gets100 = new Candidate("winery tour100");
        Candidate gets50 = new Candidate("ten pin bowling50");
        Candidate gets25 = new Candidate("movie night25");

        ballotList.addAll(makeBallotsForCandidate(gets100, 100));
        ballotList.addAll(makeBallotsForCandidate(gets50, 50));
        ballotList.addAll(makeBallotsForCandidate(gets25, 25));

        assertThat(countFirstChoiceVotes(ballotList)).isEqualTo(100 + 50 + 25);
        assertThat(gets100.getVoteTotal().intValue()).isEqualTo(100);
        assertThat(gets50.getVoteTotal().intValue()).isEqualTo(50);
        assertThat(gets25.getVoteTotal().intValue()).isEqualTo(25);
    }

    private List<Ballot> makeBallotsForCandidate(Candidate c, int numBallots) {
        List<Ballot> ballotList = new ArrayList<>();
        for (int i = 0; i < numBallots; i++) {
            Queue<Candidate> ranking = new ArrayDeque<>(1);
            ranking.add(c);
            ballotList.add(new Ballot(ranking));
        }
        return ballotList;
    }
}
