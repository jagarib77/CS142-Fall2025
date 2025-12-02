# ZOMBIE APOCALYPSE SIMULATOR
**CS 142 — Final Programming Project**  
*Fall 2025
Author: [Sang Vo](https://github.com/sangvo117)*


---

## Overview

A **real-time animated 2D grid-based zombie outbreak simulator** written in Java.

- Humans (Civilians & Soldiers) fight to survive
- Zombies (Common & Elite) hunt and infect
- Equipment (Weapon, Armor, Medkit) can be picked up → **modifies stats permanently**
- Infected humans turn into zombies
- Simulation ends when one side wins

---

## Architecture

```text

src/main/java/
    ├── controller/
    ├── model/
    │    ├── entities/      
    │    │   └── behavior/      
    │    ├── enums/             
    │    ├── items/             
    │    ├── render/            
    │    └── world/            
    ├── service/
    ├── util/
    └── view/

```

---

## Inheritance Hierarchy Diagram

```text
Entity
    ├── LivingEntity
    │   ├── Human
    │   │   ├── Civilian
    │   │   └── Soldier
    │   └── Undead
    │       ├── CommonZombie
    │       └── EliteZombie
    └── NoneLivingEntity
        └── Equipment
            ├── Weapon
            ├── Armor
            └── Medkit
```


---

## Demo

![Zombie Apocalypse Simulation](demo.gif)  
*Work in progress (GIF)*

---

## How to Run

1. Clone the repository
2. Open the project in any Java IDE
3. Run Main.java

**Sang Vo**  
CS142 — Fall 2025  
Final Project