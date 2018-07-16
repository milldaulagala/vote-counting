package test;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


import com.mill.election.Ballot;
import com.mill.election.Candidate;
import static com.mill.calculation.VoteAnalyzer.mc;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import static com.mill.calculation.VoteTransferer.distributeSurplus;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VoteTransfererTest {

    @Test
    public void distributeSurplusTest() {
        BigDecimal quota = new BigDecimal(50, mc);
        BigDecimal voteTotal = new BigDecimal(75, mc);

        Ballot ballotToTransfer = mock(Ballot.class);
        when(ballotToTransfer.getValue()).thenReturn(new BigDecimal(1, mc));
        List<Ballot> ballots = new ArrayList<>();
        ballots.add(ballotToTransfer);
        Candidate extraVotes = mock(Candidate.class);

        when(extraVotes.getVoteTotal()).thenReturn(voteTotal);
        when(extraVotes.getVotes()).thenReturn(ballots);
        PriorityQueue<Candidate> haveSurplus = new PriorityQueue<>();
        haveSurplus.add(extraVotes);
        distributeSurplus(haveSurplus, quota);

        verify(extraVotes, times(1)).getVoteTotal();
        verify(extraVotes, times(1)).getVotes();
        verify(ballotToTransfer, times(1)).getValue();

        //Extra = vote total - quota = 75 - 50 = 25.  Transfer value = extra / vote total = 25/75 = 0.33333

        verify(ballotToTransfer, times(1)).setValue(new BigDecimal(0.33333, mc));
        verify(extraVotes, times(1)).subtractVotes(ballotToTransfer);
        verify(ballotToTransfer, times(1)).getNextPreferred();

        verifyNoMoreInteractions(ballotToTransfer);
        verifyNoMoreInteractions(extraVotes);
    }
}
