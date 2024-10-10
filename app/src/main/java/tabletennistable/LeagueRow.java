package tabletennistable;

import java.util.ArrayList;
import java.util.List;

public class LeagueRow {
    private int maxSize;
    private List<String> players;

    public LeagueRow(int maxSize)
    {
        //this.maxSize = maxSize; // pre-docker exercises
        this.maxSize = computeMaxSizeWithSizeCap(maxSize);
        players = new ArrayList<>();
    }

    public void swap(String playerToRemove, String playerToAdd) {
        int index = players.indexOf(playerToRemove);
        if (index != -1)
        {
            players.set(index, playerToAdd);
        }
        else
        {
            throw new IllegalArgumentException("Player " + playerToRemove + " did not exist in row: " + String.join("|", players));
        }
    }

    public int getMaxSize()
    {
        return maxSize;
    }

    public List<String> getPlayers()
    {
        return players;
    }

    public void add(String player)
    {
        if (isFull())
        {
            throw new IllegalArgumentException("Row is full");
        }
        players.add(player);
    }

    public boolean isFull()
    {
        return players.size() >= maxSize;
    }

    public boolean includes(String player)
    {
        return players.contains(player);
    }

    public void deletePlayer(String player)
    {
        players.remove(player);
    }

     private int computeMaxSizeWithSizeCap(int maxSize){
         var sizeCapString = System.getenv("TABLE_TENNIS_LEAGUE_ROW_SIZE_CAP");
         if (sizeCapString == null) return maxSize;

         var sizeCap = Integer.parseInt(sizeCapString);
         return maxSize <= sizeCap
                 ? maxSize
                 : sizeCap;
     }
}
