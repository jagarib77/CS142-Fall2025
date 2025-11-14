# ZOMBIE APOCALYPSE SIMULATOR
**CS 142 — Final Programming Project**  
*Fall 2025 | Group: DeadCodeWalking | Author: [Sang Vo](https://github.com/sangvo117)*


---

## Overview

A **real-time animated 2D grid-based zombie outbreak simulator** written in Java.

- Humans (Civilians & Soldiers) fight to survive
- Zombies (Common & Elite) hunt and infect
- Equipment (Weapon, Armor, Medkit) can be picked up → **modifies stats permanently**
- Infected humans turn into zombies
- Simulation ends when one side wins or max turns reached

---

## Inheritance Hierarchy Diagram

```text
Entity
    ├── LivingEntity
    │   ├── Human
    │   │   ├── Civilian
    │   │   └── Soldier
    │   └── Zombie
    │       ├── CommonZombie
    │       └── EliteZombie
    └── Equipment
        ├── Weapon
        ├── Armor
        └── Medkit
```


---

## Class List & Public Methods

### 1. `Entity.java`

```java
/**
 * Abstract base class for anything that can exist on the grid.
 */
public abstract class Entity {
    /** @return current grid column */
    public int getX();
    
    /** @return current grid row */
    public int getY();
    
    /** Move entity to new coordinates */
    public void setPosition();
    
    /** @return single character for text and GUI display */
    public abstract char getSymbol();
    
    /** @return true if this entity is still active in the simulation */
    public abstract boolean isAlive();
}
```

### 2. `LivingEntity.java`

```java
/**
 * All living beings with health, stats, and combat ability.
 */
public abstract class LivingEntity extends Entity {
    /** @return current health points */
    public int getHealth();
    
    /** @return maximum health */
    public int getMaxHealth();
    
    /** @return total damage per attack (base + equipment) */
    public int getDamage();
    
    /** @return total defense (reduces incoming damage) */
    public int getDefense();
    
    /** @return cells moved per turn */
    public int getSpeed();

    /** Apply damage after defense reduction */
    public void takeDamage();
    
    /** @return true if health <= 0 */
    public boolean isDead();

    /** Attack another living entity */
    public void attack();

    /** Perform movement (subclass-specific logic) */
    public abstract void move();

    /** Perform full turn: move, act, attack, pickup, etc. */
    public abstract void act();
}
```

### 3. `Human.java`

```java
/**
 * Base class for all humans. Handles infection and equipment.
 */
public abstract class Human extends LivingEntity {
    /** @return true if bitten and will turn into zombie */
    public boolean isInfected();
    
    /** @return chance (0-1) of becoming infected when attacked */
    public double getInfectionChance();
    
    /** Called by zombie attack */
    public void infect();
    
    /** Try to pick up adjacent equipment */
    protected void pickup();
}
```

### 4. `Civillian.java`

```java
/** Regular human citizen — panics, weak combat, moves randomly */
public class Civilian extends Human {
    public Civilian();
    public char getSymbol();
    public void move();
    public void act();
}
```

### 5. `Soldier.java`

```java
/** Trained soldier — stronger, tactical movement, longer attack range */
public class Soldier extends Human {
    public Soldier();
    public char getSymbol();
    public void move();
    public void act();
}
```

### 6. `Zombie.java`

```java
/**
 * Abstract base class for all zombies.
 * Defines infection-spreading behavior.
 */
public abstract class Zombie extends LivingEntity {
    /** @return probability (0.0–1.0) of infecting a human on successful attack */
    public double getInfectionRate();
}
```

### 7. `CommonZombie.java`

```java
/** Standard slow zombie — basic movement toward humans */
public class CommonZombie extends Zombie {
    public CommonZombie();
    public char getSymbol();
    public void move();
    public void act();
}
```

### 8. `EliteZombie.java`

```java
/** Fast, strong, highly infectious zombie — elite threat */
public class EliteZombie extends Zombie {
    public EliteZombie();
    public char getSymbol();
    public void move();
    public void act();
}
```

### 9. `Equipment.java`

```java
/**
 * Abstract base class for all pick-up items (weapons, armor, medkits).
 */
public abstract class Equipment extends Entity {
    public boolean isAlive();

    /** Apply effect to entity and remove from grid */
    public void useOn(LivingEntity target, SimulationGrid grid);

    /** Apply stat changes to the entity */
    public abstract void applyEffect(LivingEntity target);

    public abstract char getSymbol();
}
```

### 10. `Weapon.java`

```java
/** Increases damage*/
public class Weapon extends Equipment {
    public Weapon();
    public void applyEffect();
    public char getSymbol();
}
```

### 11. `Armor.java`

```java
/** Increases defense*/
public class Armor extends Equipment {
    public Armor();
    public void applyEffect();
    public char getSymbol();
}
```

### 12. `Medkit.java`

```java
/** Instantly restores health when picked up */
public class Medkit extends Equipment {
    public Medkit();
    public void applyEffect();
    public char getSymbol();
}
```