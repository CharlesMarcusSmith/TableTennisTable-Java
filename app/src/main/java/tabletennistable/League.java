package tabletennistable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class League {
    private List<LeagueRow> _rows;
    Scanner in = new Scanner(System.in);
    private List<String> _strikes;

    public League()
    {
        this._rows = new ArrayList<>();
        this._strikes = new ArrayList<>();
    }

    public League(List<LeagueRow> rows)
    {
        _rows = rows;
    }

    public void addPlayer(String player)
    {
        validateName(player);
        checkPlayerIsUnique(player);

        if (areAllRowsFull())
        {
            addNewRow();
        }

        bottomRow().add(player);
    }

    public List<LeagueRow> getRows()
    {
        return _rows;
    }

    public void recordWin(String winner, String loser)
    {
        checkPlayerIsInGame(winner);
        checkPlayerIsInGame(loser);

        int winnerRowIndex = findPlayerRowIndex(winner);
        int loserRowIndex = findPlayerRowIndex(loser);

        if (winnerRowIndex - loserRowIndex != 1)
        {
            throw new IllegalArgumentException("Cannot record match result. Winner " + winner + " must be one row below loser " + loser);
        }

        _rows.get(winnerRowIndex).swap(winner, loser);
        _rows.get(loserRowIndex).swap(loser, winner);
    }

    public String getWinner()
    {
        if (_rows.size() > 0)
        {
            return _rows.get(0).getPlayers().get(0);
        }

        return null;
    }

    private boolean areAllRowsFull()
    {
        return _rows.isEmpty() || bottomRow().isFull();
    }

    private void addNewRow()
    {
        _rows.add(new LeagueRow(_rows.size() + 1));
    }

    private LeagueRow bottomRow()
    {
        return _rows.get(_rows.size() - 1);
    }

    private void validateName(String player)
    {
        String _validNameRegex = "^\\w+$";
        if (!player.matches(_validNameRegex))
        {
            throw new IllegalArgumentException("Player name " + player + " contains invalid");
        }
    }

    private void checkPlayerIsInGame(String player)
    {
        if (!isPlayerInGame(player))
        {
            throw new IllegalArgumentException("Player " + player + " is not in the game");
        }
    }

    private void checkPlayerIsUnique(String player)
    {
        if (isPlayerInGame(player))
        {
            throw new IllegalArgumentException("Cannot add player " + player + " because they are already in the game");
        }
    }

    private boolean isPlayerInGame(String player)
    {
        return findPlayerRowIndex(player) >= 0;
    }

    private int findPlayerRowIndex(String player)
    {
        int rowIndex = 0;
        for (LeagueRow row : _rows) {
            if (row.includes(player)) {
                return rowIndex;
            }
            rowIndex++;
        }
        return -1;
    }

    public Challenge createChallenge(String challenger, String challengedPlayer)
    {
        if(!isPlayerInGame(challenger) ||
        !isPlayerInGame(challengedPlayer)){
            throw new IllegalArgumentException("player not recognised.");
        }

        isChallengePlayerUnique(challenger,challengedPlayer);

        Challenge challenge = new Challenge(challenger, challengedPlayer);
        return challenge;
    }

    private void isChallengePlayerUnique(String challenger, String challengedPlayer)
    {
        if(challenger.equals(challengedPlayer)){
            throw new IllegalArgumentException("A player can not challenge themself.");
        }
    }

    public String initChallenge(Challenge challenge)
    {
        String answer = "";
        while (!answer.equals("yes") && !answer.equals("no")) {
            System.out.println(challenge.challenger + " has challenged " + challenge.getChallengedPlayer());
            System.out.println("Does " + challenge.getChallengedPlayer() + " accept? ('yes' or 'no')");
            answer = in.nextLine();
        }
        if (answer.equals("yes")) {
            return challengeResult(challenge);
        }
        else {
            addStrikes(challenge.challengedPlayer);
            return countStrikes(challenge);
        }
    }

    public void addStrikes(String player){
        _strikes.add(player);
    }

    public String challengeResult(Challenge challenge)
    {
        String answer = "";
        while (!answer.equals(challenge.challenger) && !answer.equals(challenge.challengedPlayer)) {
            System.out.println("Who won? " + challenge.challenger + " or " + challenge.getChallengedPlayer());
            answer = in.nextLine();
        }
        recordWin(answer, challenge.getOtherPlayer(answer));
        return "Recorded " + answer + " win against " + challenge.getOtherPlayer(answer);
    }

    public String countStrikes(Challenge challenge)
    {
        int strikes = Collections.frequency(_strikes, challenge.challengedPlayer);
        if(strikes < 3){
            return "Strike added, " + challenge.challengedPlayer + " now has " + strikes + " strikes.";
        }
        else {
            strikeOut(challenge);
            return "Strike out! " + challenge.challengedPlayer + " has been removed from the league.";
        }
    }

    public void strikeOut(Challenge challenge)
    {
        if(
                findPlayerRowIndex(challenge.challenger) >
                        findPlayerRowIndex(challenge.challengedPlayer)
        ){
            recordWin(challenge.challenger, challenge.challengedPlayer);
        }

        RemovePlayerFromLeague(challenge.challengedPlayer);
        RemoveStrikesForPlayer(challenge.challengedPlayer);
    }

    public void RemovePlayerFromLeague(String player) {
        while(findPlayerRowIndex(player) < _rows.size()-1){
            recordWin(
                    _rows.get(findPlayerRowIndex(player) + 1)
                            .getPlayers()
                            .get(0)
                    ,player
            );
        }
        int playerRow = findPlayerRowIndex(player);
        _rows.get(playerRow).deletePlayer(player);
    }

    public void RemoveStrikesForPlayer(String player) {
        _strikes.removeIf(
                strike -> strike.equals(player)
        );
    }
}
