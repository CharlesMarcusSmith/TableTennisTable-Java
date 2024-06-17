package tabletennistable;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class IntegrationTest
{
    @Test
    public void testPrintsEmptyGame()
    {
        App app = createApp();

        Assert.assertEquals("No players yet", app.sendCommand("print"));
    }

    private App createApp()
    {
        return new App(new League(), new LeagueRenderer(), new FileService());
    }

    @Test
    public void testPrintsFirstRow()
    {
        App app = createApp();
        app.sendCommand("add player Neo");

        Assert.assertEquals(
                "          -------------------\r\n" +
                "          |       Neo       |\r\n" +
                "          -------------------", app.sendCommand("print")
        );
    }

    @Test
    public void testPrintsTwoRows()
    {
        App app = createApp();
        app.sendCommand("add player Neo");
        app.sendCommand("add player Joe");
        app.sendCommand("add player Jon");

        Assert.assertEquals(
                "                    -------------------\r\n" +
                        "                    |       Neo       |\r\n" +
                        "                    -------------------\r\n" +
                        "          ------------------- -------------------\r\n" +
                        "          |       Joe       | |       Jon       |\r\n" +
                        "          ------------------- -------------------", app.sendCommand("print")
        );
    }

    @Test
    public void testPrintsThreeRows()
    {
        App app = createApp();
        app.sendCommand("add player Neo");
        app.sendCommand("add player Joe");
        app.sendCommand("add player Jon");
        app.sendCommand("add player Bill");
        app.sendCommand("add player Bob");
        app.sendCommand("add player Ben");


        String output = app.sendCommand("print");

        Assert.assertEquals(
                "                              -------------------\r\n" +
                        "                              |       Neo       |\r\n" +
                        "                              -------------------\r\n" +
                        "                    ------------------- -------------------\r\n" +
                        "                    |       Joe       | |       Jon       |\r\n" +
                        "                    ------------------- -------------------\r\n" +
                        "          ------------------- ------------------- -------------------\r\n" +
                        "          |      Bill       | |       Bob       | |       Ben       |\r\n" +
                        "          ------------------- ------------------- -------------------", app.sendCommand("print")
        );
    }

    @Test
    public void testPrintsWinner()
    {
        App app = createApp();
        app.sendCommand("add player Neo");
        app.sendCommand("add player Joe");
        app.sendCommand("add player Jon");
        app.sendCommand("record win Joe Neo");

        Assert.assertEquals("Joe", app.sendCommand("winner"));
    }

    @Test
    public void testPrintsThreeLeagueAfterWin() {
        App app = createApp();
        app.sendCommand("add player Neo");
        app.sendCommand("add player Joe");
        app.sendCommand("add player Jon");
        app.sendCommand("record win Joe Neo");

        Assert.assertEquals(
                "                    -------------------\r\n" +
                        "                    |       Joe       |\r\n" +
                        "                    -------------------\r\n" +
                        "          ------------------- -------------------\r\n" +
                        "          |       Neo       | |       Jon       |\r\n" +
                        "          ------------------- -------------------", app.sendCommand("print")
        );
    }
}
