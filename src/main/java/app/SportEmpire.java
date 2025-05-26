package app;

import core.Driver;
import models.Game;
import pages.GamePage;
import pages.HomePage;
import pages.SideBarPage;

import java.util.*;

public class SportEmpire {

    static HomePage homePage = new HomePage();
    static SideBarPage sideBarPage = new SideBarPage();
    static GamePage gamePage = new GamePage();

    public static void main(String[] args) {
        Driver.startDriverAndService();
        try {
            homePage.OpenSideBar();
            sideBarPage.OpenLiveTab();
            PrintSportsWithEventsOver(10);
            sideBarPage.OpenSport("Football");
            PrintHeights();
            PrintGamesSortedByOddsSum();
            PrintGamesFilteredByOddRange(1.50f, 2.50f);
            PrintGamesFilteredByScore();
        }
        finally {
            Driver.closeDriverAndService();
        }
    }

    private static void PrintSportsWithEventsOver(Integer limit){
        var sports = sideBarPage.GetSports(limit);
        System.out.println("List of sports:");
        for (Map.Entry<String,String> sport : sports.entrySet()){
            System.out.println(sport.getKey() + " " + sport.getValue());
        }
    }

    private static void PrintHeights(){
        var heights = gamePage.GetHeights();
        System.out.println("Heights:");
        for (Integer height: heights) {
            System.out.println(height);
        }
    }

    private static List<Game> FilterByOddRange(Float low, Float high){
        List<Game> filteredGames = new ArrayList<>();
        var games = gamePage.GetGames("Match Result");
        for (Game game:games) {
            if (game.getOdds().stream().anyMatch(x -> x > low && x < high)){
                filteredGames.add(game);
            }
        }
        return filteredGames;
    }

    private static void PrintGamesFilteredByOddRange(Float low, Float high){
        var games = FilterByOddRange(low, high);
        System.out.println("Game odds:");
        for (Game game:games) {
            System.out.println("Game " + games.indexOf(game));
            game.getOdds().forEach(System.out::println);
        }
    }

    private static List<Game> SortByOddsSum(){
        var games = gamePage.GetGames("Match Result");
        games.sort(Collections.reverseOrder(Comparator.comparing(game -> game.getOdds().stream().mapToDouble(Float::floatValue).sum())));
        return games;
    }

    private static void PrintGamesSortedByOddsSum(){
        var games = SortByOddsSum();
        System.out.println("Sum of game odds:");
        for (Game game:games) {
            System.out.println("Game " + games.indexOf(game));
            System.out.println(game.getOdds().stream().mapToDouble(Float::floatValue).sum());
        }
    }

    private static List<Game> FilterByScore(){
        List<Game> filteredGames = new ArrayList<>();
        var games = gamePage.GetGames("Match Result");
        for (Game game:games) {
            if (Objects.equals(game.getHomeTeamScore(), game.getAwayTeamScore())){
                filteredGames.add(game);
            }
        }
        return filteredGames;
    }

    private static void PrintGamesFilteredByScore(){
        var games = FilterByScore();
        System.out.println("Game score:");
        for (Game game:games) {
            System.out.println("Game " + games.indexOf(game));
            System.out.println(game.getHomeTeamScore() + " : " + game.getAwayTeamScore());
        }
    }
}
