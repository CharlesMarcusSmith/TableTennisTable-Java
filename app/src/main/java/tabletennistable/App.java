package tabletennistable;

public class App {
    private League league;
    private LeagueRenderer leagueRenderer;
    private FileService fileService;

    public App(League initialLeague, LeagueRenderer leagueRenderer, FileService fileService)
    {
        league = initialLeague;
        this.leagueRenderer = leagueRenderer;
        this.fileService = fileService;
    }

    public String sendCommand(String command)
    {
        try
        {
            if (command.startsWith("add player"))
            {
                String player = command.substring(11);
                league.addPlayer(player);
                return "Added player " + player;
            }

            if (command.startsWith("record win"))
            {
                String playersString = command.substring(11);
                String[] players = playersString.split(" ");
                String winner = players[0];
                String loser = players[1];
                league.recordWin(winner, loser);
                return "Recorded " + winner + " win against " + loser;
            }

            if (command.equals("print"))
            {
                return leagueRenderer.render(league);
            }

            if (command.equals("winner"))
            {
                return league.getWinner();
            }

            if (command.startsWith("save"))
            {
                String name = command.substring(5);
                fileService.save(name, league);
                return "Saved " + name;
            }

            if (command.startsWith("load"))
            {
                String name = command.substring(5);
                league = fileService.load(name);
                return "Loaded " + name;
            }

            if (command.startsWith("challenge"))
            {
                String playersString = command.substring(10);
                String[] players = playersString.split(" ");
                String challenger = players[0];
                String challengedPlayer = players[1];
                Challenge challenge = league.createChallenge(challenger, challengedPlayer);
                return league.initChallenge(challenge);
            }

            return "Unknown command: " + command;
        }
        catch (IllegalArgumentException e)
        {
            return e.getMessage();
        }
    }
}
