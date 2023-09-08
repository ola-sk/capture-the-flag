package com.codecool.capture_the_flag;

import com.codecool.capture_the_flag.actors.Actor;
import com.codecool.capture_the_flag.actors.ActorFactory;
import com.codecool.capture_the_flag.actors.Flag;
import com.codecool.capture_the_flag.actors.Player;
import com.codecool.capture_the_flag.util.Direction;
import com.codecool.capture_the_flag.util.Vector;
import java.util.ArrayList;
import java.util.List;

import static com.codecool.capture_the_flag.util.GameUtils.getDistance;
import static com.codecool.capture_the_flag.util.GameUtils.toVector;

/**
 * GameMap class
 */
public class GameMap {

  /**
   * A 2D matrix of all actors references (null if the field is empty)
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
    Actor[][] actors = new Actor[lines.length][lines[0].length()];
    final List<Player> playerList= new ArrayList<>();
    final List<Flag> flagList = new ArrayList<>();
    for (int i = 0; i<lines.length; i++) {
      char[] characters = lines[i].toCharArray();
      for (int j = 0; j<characters.length; j++){
        Actor fromChar = ActorFactory.createFromChar(characters[j], this);
        actors[i][j] = fromChar;
        if (fromChar != null && fromChar.getClass().isAssignableFrom(Player.class)) {
          playerList.add((Player) fromChar);
        } else if (fromChar != null && fromChar.getClass().isAssignableFrom(Flag.class)) {
          flagList.add((Flag) fromChar);
        }
      }
    }
    this.actorMatrix = actors;
    this.players = playerList;
    this.flags = flagList;
  }

  /**
   * Returns a char matrix of map's current state
   *
   * @return char matrix of map's current state
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
   * @param position position
   * @return actor instance present on given position
   */
  public Actor getActor(Vector position) {
    try {
      return getActorMatrix()[position.getY()][position.getX()];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Position outside of map boundaries.");
    }
  }

  /**
   * Returns a position of given actor instance
   * Should throw an IllegalArgumentException if an actor is not found or no actor is given
   *
   * @param actor actor
   * @return Vector position of the actor
   */
  public Vector getPosition(Actor actor) {
    for (int i = 0; i < actorMatrix.length; i++) {
      for (int j = 0; j < actorMatrix[i].length; j++) {
        if (actorMatrix[i][j] == actor) {
          return new Vector(j, i);
        }
      }
    }
    throw new IllegalArgumentException("Actor not found in the actorMatrix!");
  }

  /**
   * Assigns a given actor to given position
   * Should throw an IllegalArgumentException if the position is occupied by another actor
   *
   * @param actor actor to be assigned to given position
   * @param position the position to which the actor should be assigned
   */
  public void setPosition(Actor actor, Vector position) {
    this.getActorMatrix()[position.getY()][position.getX()] = actor;
  }

  /**
   * Attempts to move given player to a new position
   * If necessary, restricts movement or simulates fights between players
   *
   * @param player player to be moved
   * @param currentPosition player's current position
   * @param dir the direction in which the player wants to move
   */
  public void tryMovePlayer(Player player, Vector currentPosition, Direction dir) {
    Vector dirVector = toVector(dir);
    Vector targetPosition = new Vector(currentPosition.getX() + dirVector.getX(), currentPosition.getY() + dirVector.getY());

    if (!withinBoundaries(targetPosition))
      return;

    Actor actorOnTargetPosition = getActor(targetPosition);

    if (actorOnTargetPosition == null) {
      emptyPosition(currentPosition, player);
      setPosition(player, targetPosition);
    } else if (actorOnTargetPosition instanceof Flag) {
      emptyPosition(currentPosition, player);
      emptyPosition(targetPosition, actorOnTargetPosition);
      setPosition(player, targetPosition);

      player.addCapturedFlag();
      ((Flag) actorOnTargetPosition).setCaptured(true);
    } else if (actorOnTargetPosition instanceof Player) {
      Player otherPlayer = (Player) actorOnTargetPosition;
      int fightResult = player.fight(otherPlayer);

      if (fightResult >= 0) {
        emptyPosition(currentPosition, player);
        emptyPosition(targetPosition, otherPlayer);
        if (fightResult == 1) {
          // This player has won, move to the target position
          setPosition(player, targetPosition);
        } else if (fightResult == 0) {
          // The other player has won
          setPosition(otherPlayer, currentPosition);
        }
      }
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Returns the position of uncaptured flag that is closest to given player
   * Should throw IllegalArgumentException if there are no uncaptured flags
   *
   * @param player player for which the nearest flag should be found
   * @return position of the nearest flag
   */
  public Vector getNearestFlagPosition(Player player) {
    Vector playerPosition = getPosition(player);
    Vector nearestFlagPosition = null;
    double shortestDistance = Double.MAX_VALUE;

    for (Flag flag : flags) {
      Vector flagPosition = getPosition(flag);
      double distance = getDistance(playerPosition, flagPosition);
      if (distance < shortestDistance) {
        shortestDistance = distance;
        nearestFlagPosition = flagPosition;
      }
    }
    return nearestFlagPosition;
  }

  /**
   * Returns true if given position is within the map's boundaries
   *
   * @param position position
   * @return true if given position is within the map's boundaries
   */
  public boolean withinBoundaries(Vector position) {
    return isWithinBoundaries(position.getX(), position.getY(), getActorMatrix());
  }

  private boolean isWithinBoundaries(int x, int y, Actor[][] actorMatrix) {
    int rows = actorMatrix.length;
    int columns = actorMatrix[0].length;

    return x >= 0 && x < columns && y >= 0 && y < rows;
  }

  public Actor[][] getActorMatrix() {
    return actorMatrix;
  }

  /**
   * Empties a given position if it is within the map's boundaries and the actor stands on it
   *
   * @param position position
   * @param actor actor that should be removed from the position
   */
  public void emptyPosition(Vector position, Actor actor) {
    // if position within boundaries and the actor stands on it, empty it
    if (withinBoundaries(position) && getActor(position) == actor) {
      setPosition(null, position);
    }
  }

  public List<Player> getPlayers() {
    return players;
  }

  public List<Flag> getFlags() {
    return flags;
  }
}
