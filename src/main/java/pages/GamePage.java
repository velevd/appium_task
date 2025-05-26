package pages;

import core.BasePage;
import core.Wait;
import io.appium.java_client.AppiumBy;
import models.Game;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamePage extends BasePage {

    private final By pageComponents = AppiumBy.xpath("//android.view.View[@resource-id='appComponents']");
    private final By homeTeamName = AppiumBy.xpath("*/android.view.View[1]/android.widget.TextView[1]");
    private final By awayTeamName = AppiumBy.xpath("*/android.view.View[1]/android.widget.TextView[2]");
    private final By homeTeamScore = AppiumBy.xpath("*/android.view.View/android.view.View[1]/android.widget.TextView");
    private final By awayTeamScore = AppiumBy.xpath("*/android.view.View/android.view.View[2]/android.widget.TextView");
    private final By marketName = AppiumBy.xpath("*/android.view.View[2]/android.widget.TextView");
    private final By oddsComponent = AppiumBy.xpath("*/android.view.View[2]");


    private List<WebElement> GetGameElements() {
        return Wait.retry(()->{
            Wait.waitForElementEnabled(pageComponents);
            var gameElements = AppiumBy.xpath("//android.view.View[@resource-id='appComponents']//android.view.View[@resource-id]");
            Wait.waitForElementDisplayed(gameElements);
            var games = findElements(gameElements);
            if (games.size() == 0){
                throw new RuntimeException("No game elements found!");
            }
            return games;
        },5,5000);
    }

    public List<Integer> GetHeights(){
        return  Wait.retry(()->{
            Wait.waitForElementEnabled(pageComponents);
            List<Integer> heights = new ArrayList<>();
            var games = GetGameElements();
            for (WebElement game: games) {
                var height = game.getSize().getHeight();
                heights.add(height);
            }
            if (heights.size() == 0){
                throw new RuntimeException("No height attributes found!");
            }
            return heights;

        }, 5, 5000);
    }

    private List<WebElement> GetMatchCards(){
        List<WebElement> matchCards = new ArrayList<>();
        var games = GetGameElements();
        for (WebElement game:games) {
            var height = game.getSize().getHeight();
            var attr = game.getAttribute("resource-id");
            try {
                Integer.parseInt(attr);
                if (height > 250 && height < 257){
                    matchCards.add(game);
                }
            }
            catch (NumberFormatException ignored){
            }
        }
        return matchCards;
    }

    public List<Game> GetGames(String market){
        Wait.waitForElementDisplayed(pageComponents);
        List<Game> gameEvents = new ArrayList<>();
        var games = GetMatchCards();

        for (var game:games) {
            Wait.waitForElementDisplayed(game.findElement(marketName));
            if (Objects.equals(game.findElement(marketName).getText(), market)){
                Game gameEvent = new Game();
                List<Float> oddsValues = new ArrayList<>();
                gameEvent.setHomeTeamName(game.findElement(homeTeamName).getText());
                gameEvent.setAwayTeamName(game.findElement(awayTeamName).getText());
                gameEvent.setHomeTeamScore(Integer.valueOf(game.findElement(homeTeamScore).getText()));
                gameEvent.setAwayTeamScore(Integer.valueOf(game.findElement(awayTeamScore).getText()));
                gameEvent.setMarketName(game.findElement(marketName).getText());
                var odd1Element = game.findElement(oddsComponent).findElements(By.xpath("*/android.view.View[1]/android.view.View[@resource-id]/android.widget.TextView"));
                var odd1 = odd1Element.size() > 0 ? odd1Element.get(0).getText() : "";
                var odd2Element = game.findElement(oddsComponent).findElements(By.xpath("*/android.view.View[2]/android.view.View[@resource-id]/android.widget.TextView"));
                var odd2 = odd2Element.size() > 0 ? odd2Element.get(0).getText() : "";
                var odd3Element = game.findElement(oddsComponent).findElements(By.xpath("*/android.view.View[3]/android.view.View[@resource-id]/android.widget.TextView"));
                var odd3 = odd3Element.size() > 0 ? odd3Element.get(0).getText() : "";
                oddsValues.add(odd1.isEmpty() ? 0f : Float.parseFloat(odd1));
                oddsValues.add(odd2.isEmpty() ? 0f : Float.parseFloat(odd2));
                oddsValues.add(odd3.isEmpty() ? 0f : Float.parseFloat(odd3));
                gameEvent.setOdds(oddsValues);
                gameEvents.add(gameEvent);
            }
        }
        return gameEvents;
    }
}
