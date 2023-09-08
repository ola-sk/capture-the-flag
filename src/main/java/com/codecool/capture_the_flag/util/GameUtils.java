package com.codecool.capture_the_flag.util;

import com.codecool.capture_the_flag.actors.Actor;

public class GameUtils {

  private GameUtils() {
  }

  /**
   * Returns a character representing given actor
   * Throws an IllegalArgumentException when given invalid input
   *
   * @param actor actor
   * @return character representing a given actor
   */
  public static char getChar(Actor actor) {
    return actor.toString().charAt(0);
  }


  /**
   * Returns a vector representing given direction
   * Throws an IllegalArgumentException when given invalid input
   *
   * @param dir the direction in which the player wants to move
   * @return vector representing given direction
   */
  public static Vector toVector(Direction dir) {
    switch (dir) {
      case UP: return new Vector(0, -1);
      case DOWN: return new Vector(0, 1);
      case LEFT: return new Vector(-1, 0);
      case RIGHT:return new Vector(1, 0);
    }
    throw new IllegalArgumentException();
  }

  /**
   * Returns a direction converted from given vector
   * Throws an IllegalArgumentException when given invalid input
   *
   * @param vector vector
   * @return direction converted from given vector
   */
  public static Direction toDirection(Vector vector) {

    if (vector.getX() == 0 && vector.getY() == -1)
      return Direction.UP;

    if (vector.getX() == 0 && vector.getY() == 1)
      return Direction.DOWN;

    if (vector.getX() == -1 && vector.getY() == 0)
      return Direction.LEFT;

    if (vector.getX() == 1 && vector.getY() == 0)
      return Direction.RIGHT;

    throw new IllegalArgumentException();
  }

  /**
   * Returns a direction that is opposite to given direction
   * Throws an IllegalArgumentException when given invalid input
   *
   * @param dir direction
   * @return direction that is opposite to a given direction
   */
  public static Direction inverted(Direction dir) {
    switch (dir) {
      case UP: return Direction.DOWN;
      case DOWN: return Direction.UP;
      case LEFT: return Direction.RIGHT;
      case RIGHT: return Direction.LEFT;
    }
    throw new IllegalArgumentException();
  }

  /**
   * Returns the amount of steps a player has to make in order to get from pos1 to pos2
   *
   * @param pos1 position 1
   * @param pos2 position 2
   * @return distance between pos1 and pos2 in steps
   * 1 step = one field on the map either in horizontal or vertical direction
   */
  public static int getDistance(Vector pos1, Vector pos2) {
    int deltaX = pos2.getX() - pos1.getX();
    int deltaY = pos2.getY() - pos1.getY();
    return deltaX + deltaY;
  }
}
