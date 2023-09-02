package com.codecool.capture_the_flag;

import static org.junit.jupiter.api.Assertions.*;

import com.codecool.capture_the_flag.util.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameMapTest {
  private final String testMap = "FFFFFFFFFFFFFFFF" + System.lineSeparator() + "F..............F" + System.lineSeparator() + "F..............F" + System.lineSeparator() + "F..PRSPRSPRSP..F" + System.lineSeparator() + "F..S........R..F"
      + System.lineSeparator() + "F..R........S..F" + System.lineSeparator() + "F..P..FFFF..P..F" + System.lineSeparator() + "F..S..FFFF..R..F" + System.lineSeparator() + "F..R..FFFF..S..F" + System.lineSeparator() + "F..P..FFFF..P..F" + System.lineSeparator() + "F..S........R..F"
      + System.lineSeparator() + "F..R........S..F" + System.lineSeparator() + "F..PSRPSRPSRP..F" + System.lineSeparator() + "F..............F" + System.lineSeparator() + "F..............F" + System.lineSeparator() + "FFFFFFFFFFFFFFFF";

  private GameMap mockGameMap = new GameMap(testMap);

  @Test
  void testToString() {
  }

  @Test
  void getActor() {
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