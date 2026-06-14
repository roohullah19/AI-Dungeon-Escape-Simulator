# AI Dungeon Escape Simulator

A Java-based Data Structures & Algorithms project that simulates a grid-based dungeon game where a player escapes a procedurally generated maze while being chased by multiple AI enemies using BFS, DFS, and A* pathfinding.

---

## Project Overview

This project demonstrates the practical implementation of core Data Structures and Algorithms in a real-time interactive game environment using Java Swing.

It focuses on visualizing graph traversal algorithms and applying them to AI-driven enemy behavior inside a procedurally generated maze world.

The project has evolved into a modular game engine with multiple AI behaviors, power-ups, scoring, and UI systems.

---

## Key Features

### Maze System
- Procedurally generated maze using recursive backtracking (DFS)
- Randomized additional paths for complexity
- Grid-based representation (0 = path, 1 = wall)
- Dynamic maze generation per game restart

### Player System
- Grid-based movement (WASD + Arrow keys)
- Undo move system (move history stack)
- Collision handling with walls
- Phase-wall power-up allows temporary wall passing

### Enemy AI System
- Multiple AI enemies with different strategies:
  - BFS Enemy (shortest path)
  - DFS Enemy (exploration-based movement)
  - A* Enemy (heuristic-based optimal pathfinding)
- Real-time path recalculation

### Power-Up System
- Freeze Enemy (temporarily stops AI movement)
- Phase Wall (allows player to pass through walls)
- Random spawn system with safe placement logic

### Game Systems
- Score tracking based on survival time + achievements
- Leaderboard using TreeMap (sorted high score system)
- Pause / Resume system
- Game reset and menu navigation
- Game window system using CardLayout

---

## Algorithms Implemented
- Breadth First Search (BFS)
- Depth First Search (DFS)
- A* Search Algorithm
- Recursive Backtracking (Maze Generation)

---

## Data Structures Used
- 2D Arrays (maze grid)
- Queue (BFS traversal)
- Stack (move history / undo system)
- PriorityQueue (A* open list)
- HashSet (visited nodes in A*)
- TreeMap (leaderboard)
- ArrayList (entities and paths)

---

## Architecture Highlights
- Modular package-based structure:
  - `entities`
  - `maze`
  - `pathfinding`
  - `powerups`
  - `stats`
  - `difficulty`
  - `game`
- Separation of concerns (GamePanel, Pathfinding, Entities, UI)
- Extensible design for adding new AI types and mechanics

---

## Built With
- Java
- Java Swing (GUI rendering)
- Java Collections Framework

---

## Performance Stats (Visualized In-Game)
- BFS vs DFS vs A* comparison
- Visited nodes tracking
- Path length analysis
- Heap operations (A* efficiency)
- Execution time benchmarking (nanoseconds)

---

## To Be Improved / Future Work
- Fog of War system
- Advanced AI behaviors (aggressive/defensive modes)
- Multiplayer mode
- Animated sprites instead of blocks
- Sound effects and game polish
- Improved procedural generation (perfect maze + loops control)

---

## Project Purpose

This project was built as part of a Data Structures & Algorithms course at the University of Central Punjab to demonstrate:

- Graph traversal algorithms in real-world applications  
- AI decision-making using BFS, DFS, and A*  
- Game development using Java Swing  
- Visualization of abstract DSA concepts in an interactive system  

---

## Future Vision

To evolve this project into a fully functional AI-driven dungeon crawler engine that demonstrates real-time pathfinding, adaptive AI, and scalable game architecture.

---

## Author

**Rohullah**  
University of Central Punjab
