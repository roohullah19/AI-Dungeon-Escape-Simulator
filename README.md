# AI Dungeon Escape Simulator

A Java-based Data Structures & Algorithms project that simulates a grid-based dungeon game where a player escapes a randomly generated maze while being chased by an AI enemy using BFS pathfinding.

---

## Project Overview

This project demonstrates the practical implementation of core Data Structures and Algorithms in a real-time interactive environment using Java Swing.

The main focus is on visualizing graph traversal (BFS) and applying it to AI-driven enemy movement in a maze-based game world.

---

## Current Features

### Maze System
- Randomly generated maze using 2D arrays
- Boundary walls for map constraints
- Grid-based dungeon layout (0 = path, 1 = wall)

### Player System
- Smooth grid-based movement
- WASD and Arrow key controls
- Collision detection with walls

### Enemy AI System
- AI enemy that actively chases the player
- Breadth First Search (BFS) pathfinding
- Shortest path calculation in real time
- Continuous movement using Java Timer

### Game Engine
- Built using Java Swing (JFrame + JPanel)
- Custom rendering via `paintComponent`
- Real-time update loop for gameplay

---

## Algorithms Implemented
- Breadth First Search (BFS) for shortest pathfinding

---

## Data Structures Used
- 2D Arrays (maze representation)
- Queue (BFS traversal)
- LinkedList (queue implementation)
- Custom Node class for path tracking

---

## Technologies Used
- Java
- Java Swing
- Java Collections Framework

---

## To Be Implemented

### AI Enhancements
- A* Pathfinding Algorithm (smarter enemy movement)
- Multiple enemy types with different behaviors

### Gameplay Features
- Player health system
- Score and survival time tracking
- Fog of war / limited visibility

### Maze Improvements
- Advanced procedural generation (DFS / Recursive Backtracking)
- Larger and more complex maps

### UI Improvements
- Start menu screen
- Game over screen
- Pause and restart system

---

## Project Purpose

This project was built as part of a Data Structures & Algorithms course at the University of Central Punjab to demonstrate:

- Graph traversal algorithms in real-world applications
- AI decision-making using BFS
- Game logic implementation using Java
- Visualization of abstract DSA concepts

---

## Future Vision

The goal is to evolve this project into a fully functional AI-driven dungeon crawler that visually demonstrates multiple pathfinding and graph algorithms in action.

---

## Author
Roohullah Khan  
University of Central Punjab
