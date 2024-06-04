package tabletennistable;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.contains;

public class AppTest {
    @Mock
    LeagueRenderer renderer;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    public void addTestPlayers(App app){
        app.sendCommand("add player Jack");
        app.sendCommand("add player Jill");
        app.sendCommand("add player Josh");
        app.sendCommand("add player Joey");
        app.sendCommand("add player Jojo");
        app.sendCommand("add player Jeff");
    }
    @Test
    public void testPrintsCurrentState()
    {
        initMocks();
        League league = new League();
        Mockito.when(renderer.render(league)).thenReturn("Rendered League");

        App app = new App(league, renderer, null, null);

        Assert.assertEquals("Rendered League", app.sendCommand("print"));
    }

    @Test
    public void testGetRows()
    {
        initMocks();
        League league = new League();
        App app = new App(league, renderer, null, null);
        addTestPlayers(app);

        Assert.assertEquals(3, league.getRows().stream().count());
    }

    @Test
    public void testAddPlayer()
    {
        initMocks();
        League league = new League();
        App app = new App(league, renderer, null, null);

        //Adding a player
        app.sendCommand("add player Tom");

        Assert.assertEquals("Tom", league.getRows().get(0).getPlayers().get(0));
    }

    @Test
    public void testRecordWin()
    {
        initMocks();
        League league = new League();
        App app = new App(league, renderer, null, null);
        addTestPlayers(app);

        // Jack is original winner:
        // Assert.assertEquals("Jack", league.getWinner()); = True

        //Jill becomes winner:
        app.sendCommand("record win Jill Jack");
        Assert.assertEquals("Jill", league.getWinner());
    }


}
