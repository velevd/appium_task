package models;

import lombok.Data;

import java.util.List;

@Data
public class Game {

    private String homeTeamName;
    private String awayTeamName;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private String marketName;
    private List<Float> odds;
}
