package stratego;

/**
 * Player is a public class whose instances are created for both the players playing a game.
 */
public class Player {
    private String name;
    private int playerNumber;
    /**
     * gameResult holds the result of the game.
     */
    private CombatResult gameResult;

    /**
     * Constructor of this class which takes name and number of the player.
     *
     * @param name         is String value of the name of the player.
     * @param playerNumber is the int value of the number of the player.
     */
    public Player(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
    }

    /**
     * getter method of name attribute.
     *
     * @return name which is a String value.
     */
    public String getName() {
        return name;
    }

    /**
     * setGameResult is the setter method of gameResult attribute.
     *
     * @param gameResult is of CombatResult type.
     */
    public void setGameResult(CombatResult gameResult) {
        this.gameResult = gameResult;
    }

    /**
     * getPlayerNumber is the getter method of the playerNumber attribute.
     *
     * @return playerNumber is an int value.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * loseGame is a public method which when called sets the gameResult to lose.
     */
    public void loseGame() {
        this.gameResult = CombatResult.LOSE;
        Game.getAllSquares().clear();
    }

    /**
     * hasLost is a public boolean method this returns if the player is lost or not.
     *
     * @return boolean true if the player loses.
     */
    public boolean hasLost() {
        return gameResult == CombatResult.LOSE ? true : false;
    }
}
