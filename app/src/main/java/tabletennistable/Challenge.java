package tabletennistable;

public class Challenge {
    String challenger;
    String challengedPlayer;

    public Challenge(String challenger, String challengedPlayer) {
        this.challenger = challenger;
        this.challengedPlayer = challengedPlayer;
    }

    public String getChallenger() {
        return challenger;
    }

    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    public String getChallengedPlayer() {
        return challengedPlayer;
    }

    public void setChallengedPlayer(String challengedPlayer) {
        this.challengedPlayer = challengedPlayer;
    }

    public boolean isPlayerValid(String player){
        return player.equals(challenger) || player.equals(challengedPlayer);
    }

    public String getOtherPlayer(String player){
        if(player.equals(challenger)){
            return challengedPlayer;
        } else {
            return challenger;
        }
    }
}
