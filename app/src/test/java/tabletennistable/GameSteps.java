    package tabletennistable;

    import io.cucumber.java.Before;
    import io.cucumber.java.en.And;
    import io.cucumber.java.en.Given;
    import io.cucumber.java.en.Then;
    import io.cucumber.java.en.When;
    import org.junit.Assert;

    public class GameSteps {
        // Current app instance
        private App app;

        // Last response
        private String response;

        @Before
        public void createApp()
        {
            League league = new League();
            LeagueRenderer leagueRenderer = new LeagueRenderer();
            FileService fileService = new FileService();
            app = new App(league, leagueRenderer, fileService);
        }

        @Given("the league has no players")
        public void givenTheLeagueHasNoPlayers()
        {
            // Nothing to do - the default league starts with no players
        }

        @When("I add a player")
        public void whenIAddAPlayer(String player)
        {

            app.sendCommand("add player " + player);
        }

        @Given("I add players to the league")
        public void givenIAddPlayersToTheLeague()
        {
            app.sendCommand("add player Neo");
            app.sendCommand("add player Joe");
            app.sendCommand("add player Jon");
            app.sendCommand("add player Bill");
            app.sendCommand("add player Bob");
            app.sendCommand("add player Ben");
        }

        @When("I print the league")
        public void whenIPrintTheLeague()
        {
            response = app.sendCommand("print");
        }

        @Then("I should see {string}")
        public void iShouldSeeString(String expected)
        {
            Assert.assertEquals(expected, response);
        }

        @Then("I should see a correctly displayed league")
        public void iShouldSeeACorrectlyDisplayedLeague(){
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

        @When("The winner is printed")
        public void theWinnerIsPrinted()
        {
            response = app.sendCommand("winner");
        }

        @Then("The winner should be {string}")
        public void theWinnerShouldBe(String expected)
        {
            Assert.assertEquals(expected, response);
        }

        @When("A player wins the match")
        public void aPlayerWinsTheMatch()
        {
            response = app.sendCommand("record win Joe Neo");
            System.out.println(response);
        }

        @Then("The win is recorded")
        public void theWinIsRecorded()
        {
            Assert.assertEquals("Recorded Joe win against Neo", response);
        }
    }
