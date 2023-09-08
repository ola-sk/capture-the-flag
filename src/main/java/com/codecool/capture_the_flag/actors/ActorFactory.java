package com.codecool.capture_the_flag.actors;

import com.codecool.capture_the_flag.GameMap;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Static class for creating new actor instances
 */
public class ActorFactory {

  /**
   * A predefined collection of names for the players
   */
  private static final Queue<String> names = new LinkedList<>(Arrays.asList(
      "Marcel", "Moises", "Zane", "Dashawn", "Sean", "Rashad", "Seth", "Oliver", "Chris", "Quinton",
      "August", "Yusuf", "Jeramiah", "Bryce", "Rory", "Preston", "Eli", "Elisha", "Vicente", "Cristian",
      "Justice", "Eddie", "Allan", "Aarav", "Layne", "Owen", "Julio", "Soren", "Levi", "Mekhi", "Paul",
      "Griffin", "Agustin", "Johan", "Jaydin", "Skylar", "Rodrigo", "Brian", "John", "Davon", "Damari",
      "Ty", "Raymond", "Ronald", "Noah", "Abdiel", "Tyree", "Trent", "Rafael", "Jamarion"));


  /**
   * Use this method for getting names
   *
   * @return name
   */
  public static String getName() {
    return names.remove();
  }

  /**
   * Returns a new player instance, depending on given team
   *
   * @param team
   * @param mapReference
   * @return
   */
  public static Actor createPlayer(Player.PlayerTeam team, GameMap mapReference) {
    switch (team) {
      case ROCK: return new Rock(getName(), mapReference);
      case PAPER: return new Paper(getName(), mapReference);
      case SCISSORS: return new Scissors(getName(), mapReference);
      default: throw new IllegalStateException("'Team' was not recognised.");
    }
  }

  /**
   * Returns a new Flag instance
   *
   * @param mapReference
   * @return
   */
  public static Actor createFlag(GameMap mapReference) {
    return new Flag(mapReference);
  }

  /**
   * Returns a new actor instance, depending on given character
   *
   * @param c
   * @param mapReference
   * @return
   */
  public static Actor createFromChar(char c, GameMap mapReference) {
    switch (Character.toUpperCase(c)) {
      case '.': return null;
      case 'P': return new Paper(getName(), mapReference);
      case 'R': return new Rock(getName(), mapReference);
      case 'S': return new Scissors(getName(), mapReference);
      case 'F': return new Flag(mapReference);
      default: throw new IllegalStateException("Character could not be interpreted: '" + c + "'");
    }
  }
}
