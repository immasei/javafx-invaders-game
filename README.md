# Invaders Game

## Table of Contents
**[Overview](#Overview)**<br>
**[Dependencies](#Dependencies)**<br>
**[Execute](#Execute)**<br>
**[Features](#Features)**<br>
**[Testing](#Testing)**<br>
**[References](#References)**<br>

## Overview
Space Invaders is a 1978 shoot 'em up arcade video game developed and released by Taito in Japan. The goal is to defeat wave after wave of descending aliens with a horizontally moving laser to earn as many points as possible. Here's an [example](https://www.youtube.com/watch?v=uGjgxwiemms).

## Getting Started

### Dependencies

* Java SE Development Kit (JDK) 17
* JDK 17
* Gradle

### Execute

`cd` into `java-invaders-game` then `cd` into either:

* invaders-full-ver
* invaders-partial

```
gradle clean build run
```

## Features

### Switch difficulties

* You can switch difficulty at any time during the game.
* The default mode is `Easy` as in `config_easy.json`

* The game will restart with new difficulty level:

  * Press `Z` for `easy`
  * Press `X` for `medium`
  * Press `C` for `hard`

<p align='center'>
  <img align='center' src='readme-resources/switch-difficulty.gif' width='450'/>
</p>

### Cheat

The player can do a cheating operation to remove all aliens projectile of the same type or all the alien's who have the same strategy immediately. 

* Score won't increase when game ends (`red`)
* But Alien and Projectile will still be removed regardless.

  * Press `A` to remove `slow enemy projectile` (1 points)
  * Press `S` to remove `fast enemy projectile` (2 points)
  
  <p align='center'>
    <img align='center' src='readme-resources/cheat-projectile.gif' width='450'/>
  </p>

  * Press `Q` to remove `slow alien` (3 points)
  * Press `W` to remove `fast alien` (4 points)

  <p align='center'>
    <img align='center' src='readme-resources/cheat-alien.gif' width='450'/>
  </p>

## Undo

* The player can reset the game to an earlier state (including score, time, alien's position and alien projectile's position) so that a shot can be undo.
* Only 1 state is saved at a time.

  * Press `O` to save
  * Press `P` to undo

  <p align='center'>
    <img align='center' src='readme-resources/undo.gif' width='450'/>
  </p>
