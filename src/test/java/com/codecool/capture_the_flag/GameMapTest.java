package com.codecool.capture_the_flag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codecool.capture_the_flag.actors.Actor;
import com.codecool.capture_the_flag.actors.ActorFactory;
import com.codecool.capture_the_flag.actors.Player.PlayerTeam;
import com.codecool.capture_the_flag.util.Vector;
import org.junit.jupiter.api.Test;

class GameMapTest {
  private final String testMap = "FFFFFFFFFFFFFFFF" + System.lineSeparator() + "F..............F" + System.lineSeparator() + "F..............F" + System.lineSeparator() + "F..PRSPRSPRSP..F" + System.lineSeparator() + "F..S........R..F"
      + System.lineSeparator() + "F..R........S..F" + System.lineSeparator() + "F..P..FFFF..P..F" + System.lineSeparator() + "F..S..FFFF..R..F" + System.lineSeparator() + "F..R..FFFF..S..F" + System.lineSeparator() + "F..P..FFFF..P..F" + System.lineSeparator() + "F..S........R..F"
      + System.lineSeparator() + "F..R........S..F" + System.lineSeparator() + "F..PSRPSRPSRP..F" + System.lineSeparator() + "F..............F" + System.lineSeparator() + "F..............F" + System.lineSeparator() + "FFFFFFFFFFFFFFFF";
  // in JUnit 5 the line below behaves like @BeforeEach: runs before each test.
  private GameMap mockGameMap = new GameMap(testMap);

  @Test
  void testToString() {
  }

  @Test
  void setPositionAndGetActor() {
    Actor newActor = ActorFactory.createPlayer(PlayerTeam.ROCK, mockGameMap);
    mockGameMap.setPosition(newActor, new Vector(1, 1));
    Actor result = mockGameMap.getActor(new Vector(1, 1));
    assertEquals(newActor, result);
  }
  @Test
  public void getActorWithInvalidPositionThrowsException() {
    // Test the getActor method with an invalid position
    assertThrows(IllegalArgumentException.class, () -> mockGameMap.getActor(new Vector(100, 1000)));
  }
  @Test
  void getPosition() {
  }

  @Test
  void setPosition() {
  }

  @Test
  void tryMovePlayer() {
  }

  @Test
  void getNearestFlagPosition() {
  }

  @Test
  void withinBoundaries() {
    assertTrue(mockGameMap.withinBoundaries(new Vector(8, 0)));
    assertTrue(mockGameMap.withinBoundaries(new Vector(10, 3)));
    assertTrue(mockGameMap.withinBoundaries(new Vector(15, 7)));
    assertFalse(mockGameMap.withinBoundaries(new Vector(16, 3)));
    assertFalse(mockGameMap.withinBoundaries(new Vector(8, 100)));
  }

  @Test
  void getActorMatrix() {
  }

  @Test
  void getPlayers() {
  }

  @Test
  void getFlags() {
  }
}