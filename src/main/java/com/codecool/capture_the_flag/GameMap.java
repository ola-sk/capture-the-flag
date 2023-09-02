package com.codecool.capture_the_flag;

import com.codecool.capture_the_flag.actors.Actor;
import com.codecool.capture_the_flag.actors.ActorFactory;
import com.codecool.capture_the_flag.actors.Flag;
import com.codecool.capture_the_flag.actors.Player;
import com.codecool.capture_the_flag.util.Direction;
import com.codecool.capture_the_flag.util.GameUtils;
import com.codecool.capture_the_flag.util.Vector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.codecool.capture_the_flag.util.GameUtils.toVector;

/**
 * GameMap class
 */
public class GameMap {

  /**
   * A 2D matrix of all actor references (null if the field is empty)
   */
  private final Actor[][] actorMatrix;

  /**
   * Contains all players present on the map (also dead ones)
   */
  private final List<Player> players;

  /**
   * Contains all players present on the map (also captured ones)
   */
  private final List<Flag> flags;


  /**
   * Returns a new GameMap instance, constructed from given char matrix
   */
  public GameMap(String charMatrix) {
    String[] lines = charMatrix.split("\r\n|\r|\n");
    Actor[][] actorMatrix = new Actor[lines.length][lines[0].length()];
    final List<Player> players= new ArrayList<>();
    final List<Flag> flags = new ArrayList<>();
    for (int i = 0; i<lines.length; i++) {
      char[] characters = lines[i].toCharArray();
      for (int j = 0; j<characters.length; j++){
        Actor fromChar = ActorFactory.createFromChar(characters[j], this);
        actorMatrix[i][j] = fromChar;
        if (fromChar != null && fromChar.getClass().isAssignableFrom(Player.class)) {
          players.add((Player) fromChar);
        } else if (fromChar != null && fromChar.getClass().isAssignableFrom(Flag.class)) {
          flags.add((Flag) fromChar);
        }
      }
    }
    this.actorMatrix=actorMatrix;
    this.players = players;
    this.flags = flags;
  }

  /**
   * Returns a char matrix of map's current state
   *
   * @return
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Actor[] row : actorMatrix) {
      for (Actor actor : row) {
        if (actor != null) {
          sb.append(actor.toString());
        } else {
          sb.append(".");
        }
      }
      sb.append(System.lineSeparator());
    }
    if (sb.length() > 0) {
      sb.setLength(sb.length() - System.lineSeparator().length());
    }
    return sb.toString();
  }

  /**
   * Returns an actor instance present on given position
   * Should return null if no actor is present
   * Should throw an IllegalArgumentException if the position is outside map's boundaries
   *
   * @param position
   * @return
   */
  public Actor getActor(Vector position) {
    try {
      Actor actor = getActorMatrix()[position.getY()][position.getX()];
      return actor;
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Position outside of map boundaries.");
    }
  }

  /**
   * Returns a position of given actor instance
   * Should throw an IllegalArgumentException if actor is not found or no actor is given
   *
   * @param actor
   * @return
   */
  public Vector getPosition(Actor actor) {
    throw new RuntimeException("Method not implemented!");
  }

  /**
   * Assignes given actor to given position
   * Should throw an IllegalArgumentException if the position is occupied by an another actor
   *
   * @param actor
   * @param position
   */
  public void setPosition(Actor actor, Vector position) {
    this.getActorMatrix()[position.getY()][position.getX()] = actor;
  }

  /**
   * Attempts to move given player to a new position
   * If necessary, restricts movement or simulates fights between players
   *
   * @param player
   * @param currentPosition
   * @param dir
   */
  public void tryMovePlayer(Player player, Vector currentPosition, Direction dir) {
    Vector dirVector = toVector(dir);
    Vector targetPosition = new Vector(currentPosition.getX() + dirVector.getX(), currentPosition.getY() + dirVector.getY());

    if (!withinBoundaries(targetPosition))
      return;

    Actor actorOnTargetPosition = getActor(targetPosition);

    if (actorOnTargetPosition == null) {
      actorMatrix[currentPosition.getY()][currentPosition.getX()] = null;
      setPosition(player, targetPosition);
    } else if (actorOnTargetPosition instanceof Flag) {
      actorMatrix[currentPosition.getY()][currentPosition.getX()] = null;
      actorMatrix[targetPosition.getY()][targetPosition.getX()] = null;
      setPosition(player, targetPosition);

      player.setCapturedFlags(player.getCapturedFlags() + 1);
      ((Flag) actorOnTargetPosition).setCaptured(true);
    } else if (actorOnTargetPosition instanceof Player) {
      Player otherPlayer = (Player) actorOnTargetPosition;
      int fightResult = player.Fight(otherPlayer);

      if (fightResult == 1) {
        // Player has won, move to the target position
        otherPlayer.setAlive(false);
        actorMatrix[currentPosition.getY()][currentPosition.getX()] = null;
        actorMatrix[targetPosition.getY()][targetPosition.getX()] = null;
        setPosition(player, targetPosition);

        player.setKilledPlayers(player.getKilledPlayers() + 1);
      } else if (fightResult == 0) {
        // Other player has won
        player.setAlive(false);
        actorMatrix[currentPosition.getY()][currentPosition.getX()] = null;
        actorMatrix[targetPosition.getY()][targetPosition.getX()] = null;
        setPosition(otherPlayer, currentPosition);

        otherPlayer.setKilledPlayers(otherPlayer.getKilledPlayers() + 1);
      }
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Returns the position of an uncaptured flag that is closest to given player
   * Should throw IllegalArgumentException if there are no uncaptured flags
   *
   * @param player
   * @return
   */
  public Vector getNearestFlagPosition(Player player) {
    if (flags.isEmpty()) throw new IllegalArgumentException ("There are no more flags");
    throw new RuntimeException("Method not implemented!");
  }

  /**
   * Returns true if given position is within the map's boundaries
   *
   * @param position
   * @return
   */
  public boolean withinBoundaries(Vector position) {
    try {
      Actor actor = getActorMatrix()[position.getY()][position.getX()];
      return true;
    }
    catch(ArrayIndexOutOfBoundsException e) {
      return false;
    }
  }

  public Actor[][] getActorMatrix() {
    return actorMatrix;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public List<Flag> getFlags() {
    return flags;
  }
}
