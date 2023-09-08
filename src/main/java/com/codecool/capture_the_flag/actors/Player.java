package com.codecool.capture_the_flag.actors;

import com.codecool.capture_the_flag.GameMap;
import com.codecool.capture_the_flag.util.Direction;
import com.codecool.capture_the_flag.util.Vector;
import java.util.Objects;

/**
 * Base class for all Player
 */
public abstract class Player extends Actor {

  private final String name;
  private boolean alive;
  private int capturedFlags;
  private int killedPlayers;

  protected Player(String name, GameMap mapReference) {
    super(mapReference);
    this.name = name;
    this.alive = true;
    this.capturedFlags = 0;
    this.killedPlayers = 0;
  }

  /**
   * Returns a direction for the player's next move, depending on the nearest flag's position
   *
   * @param playerPosition player's current position
   * @param flagPosition  nearest flag's position
   * @return direction for the player's next move
   */
  public static Direction getMoveDirection(Vector playerPosition, Vector flagPosition) {
    if (flagPosition == null ) { throw new IllegalArgumentException();}
    else {
      int deltaX = flagPosition.getX() - playerPosition.getX();
      int deltaY = flagPosition.getY() - playerPosition.getY();
      if (deltaX == 0 && deltaY == 0) {
        throw new IllegalArgumentException("Player is already at the flag position.");
      }
      if (Math.abs(deltaX) >= Math.abs(deltaY)) {
        // Move horizontally if horizontal distance is bigger
        return (deltaX > 0) ? Direction.RIGHT : Direction.LEFT;
      } else {
        // Move vertically
        return (deltaY > 0) ? Direction.DOWN : Direction.UP;
      }
    }
  }

  /**
   * Returns this player's current score
   *
   * @return current score of this player
   */
  public int getCurrentScore() {
    int sum = 0;
    sum += (this.getKilledPlayers() * 5);
    sum += (this.getCapturedFlags() * 10);
    return sum;
  }

  /**
   * Cycles this player's behavior
   * Should be called on every game cycle
   * Specific subclass ought to override this method to add additional logic
   */
  public abstract void onGameCycle();

  /**
   * fight simulation between this and given player
   *
   * @param otherPlayer the player to fight with
   * @return 1 if this player won, 0 if the other player won, -1 if fight didn't happen (otherPlayer is a teammate)
   */
  public abstract short fight(Player otherPlayer);
  void validateFightConditions(Player otherPlayer) {
    if (!this.isAlive()) {
        throw new IllegalStateException("Player cannot fight since it is dead!");
    }
    Objects.requireNonNull(otherPlayer, "The `otherPlayer` cannot be null.");
    if (!otherPlayer.isAlive()) {
        throw new IllegalArgumentException("The other player is already dead!");
    }
  }
  boolean verifyPlayersCanFight(Player otherPlayer) {
    return this.isAlive() && otherPlayer != null && otherPlayer.isAlive();
  }
  short winFight(Player enemy) {
    enemy.setAlive(false);
    this.addKilledPlayer();
    return 1;
  }
  short looseFight(Player enemy) {
    this.setAlive(false);
    enemy.addKilledPlayer();
    return 0;
  }
  /**
   * Returns this player's team
   *
   * @return PlayerTeam enum indicating the team of this player
   */
  public abstract PlayerTeam getTeam();

  /**
   * Returns true after player was killed
   *
   * @return true if the player is alive, false otherwise
   */
  public boolean isAlive() {
    return alive;
  }

  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  /**
   * Returns current number of flags captured by this player
   *
   * @return current number of captured flags
   */
  public int getCapturedFlags() {
    return capturedFlags;
  }

  public void addCapturedFlag() {
    this.capturedFlags++;
  }

  /**
   * Returns the current number of players killed by this player
   *
   * @return current number of killed players
   */
  public int getKilledPlayers() {
    return killedPlayers;
  }

  // add javadoc to describe method below
  /**
   * Sets the number of killed players.
   * Specific subclasses may override this method to add additional logic.
   * Logic should be added to ensure that the number of killed players is always less
   * than or equal to the number of players that can be killed by players of given subclass.
   *
   * @param killedPlayers the number of killed players
   *                      (should be 0 or positive)
   *                      (should be less than or equal to the number of players)
   */
  public void setKilledPlayers(int killedPlayers) {
    this.killedPlayers = killedPlayers;
  }
  public void addKilledPlayer() {
    this.killedPlayers++;
  }
  /**
   * Returns this player's name
   *
   * @return name of the player
   */
  public String getName() {
    return name;
  }

  /**
   * PlayerTeam enum
   */
  public enum PlayerTeam {
    ROCK,
    PAPER,
    SCISSORS
  }
}
