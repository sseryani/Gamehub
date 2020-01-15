package fall2018.csc2017.GameCentre.ui.menu.view.leaderboard;

public class LeaderboardEntry {

    private String username;
    private int score;

    LeaderboardEntry() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
