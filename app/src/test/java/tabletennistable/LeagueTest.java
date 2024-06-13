package tabletennistable;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LeagueTest {

    @Test
    public void testAddPlayer()
    {
        // Given
        League league = new League();

        // When
        league.addPlayer("Bob");

        // Then
        List<LeagueRow> rows = league.getRows();
        Assert.assertEquals(1, rows.size());
        List<String> firstRowPlayers = rows.get(0).getPlayers();
        Assert.assertEquals(1, firstRowPlayers.size());
        Assert.assertThat(firstRowPlayers, IsCollectionContaining.hasItem("Bob"));
    }

    @Test
    public void testGetRows()
    {
        // Given
        League league = new League();
        league.addPlayer("Bob");
        league.addPlayer("Bill");
        league.addPlayer("Ben");

        // When
        List<LeagueRow> rows = league.getRows();

        // Then
        Assert.assertEquals(2, rows.size());
        List<String> firstRowPlayers = rows.get(0).getPlayers();
        List<String> secondRowPlayers = rows.get(1).getPlayers();
        Assert.assertEquals(1, firstRowPlayers.size());
        Assert.assertThat(firstRowPlayers, IsCollectionContaining.hasItem("Bob"));
        Assert.assertThat(secondRowPlayers, IsCollectionContaining.hasItem("Ben"));
        Assert.assertThat(secondRowPlayers, IsCollectionContaining.hasItem("Bill"));
    }

    @Test
    public void testRecordWin()
    {
        // Given
        League league = new League();
        league.addPlayer("Bob");
        league.addPlayer("Bill");
        league.addPlayer("Ben");

        // When
        league.recordWin("Bill", "Bob");

        // Then
        List<LeagueRow> rows = league.getRows();
        Assert.assertEquals(2, rows.size());
        List<String> firstRowPlayers = rows.get(0).getPlayers();
        List<String> secondRowPlayers = rows.get(1).getPlayers();
        Assert.assertEquals(1, firstRowPlayers.size());
        Assert.assertThat(firstRowPlayers, IsCollectionContaining.hasItem("Bill"));
        Assert.assertThat(secondRowPlayers, IsCollectionContaining.hasItem("Bob"));
    }

    @Test
    public void testGetWinner()
    {
        // Given
        League league = new League();
        league.addPlayer("Bob");
        league.addPlayer("Bill");
        league.addPlayer("Ben");
        league.recordWin("Bill", "Bob");

        // When
        String winnerResult = league.getWinner();

        // Then
        Assert.assertEquals("Bill", winnerResult);
    }

    @Test
    public void createGoodChallenge()
    {
        // Given
        League league = new League();
        league.addPlayer("Bob");
        league.addPlayer("Bill");
        league.addPlayer("Ben");

        // When
        Challenge challenge = league.createChallenge("Bob", "Ben");

        // Then
        Assert.assertEquals("Bob", challenge.challenger);
        Assert.assertEquals("Ben", challenge.challengedPlayer);
    }

    @Test
    public void createBadChallenge()
    {
        // Given
        League league = new League();
        league.addPlayer("Bob");
        league.addPlayer("Bill");


        // Then - a bad challenge entry
        Assert.assertThrows(IllegalArgumentException.class, () ->
                league.createChallenge("Bob", "NotBill"));
    }

    @Test
    public void testIsChallengePlayerUnique(){
        // Given
        League league = new League();
        league.addPlayer("Bob");
        league.addPlayer("Bill");


        // Then - a bad challenge with non-unique players
        Assert.assertThrows(IllegalArgumentException.class, () ->
                league.createChallenge("Bob", "Bob"));
    }
}
