package com.codecool.capture_the_flag.actors;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ActorFactoryTest {

  @Test
  void getName() {
    assertThat(ActorFactory.getName()).isNotEqualTo(ActorFactory.getName());
  }

  @Test
  void createPlayer() {

  }

  @Test
  void createFlag() {
  }

  @Test
  void createFromChar() {
  }
}