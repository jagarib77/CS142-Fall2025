package model.entities;

import model.render.SymbolRegistry;
import model.world.Cell;

import java.util.Objects;

public abstract class Entity {

    private Cell cell;
    private final String displayName;

    protected Entity(Cell cell, String displayName) {
        this.cell = Objects.requireNonNull(cell);
        this.displayName = displayName != null ? displayName : "";
    }

    public int getX() {
        return cell.x;
    }

    public int getY() {
        return cell.y;
    }

    public Cell getCell() {
        return cell;
    }

    public void setPosition(Cell cell) {
        this.cell = Objects.requireNonNull(cell, "Cell cannot be null");
    }

    public void setPosition(int x, int y) {
        this.cell = new Cell(x, y);
    }

    public boolean isAdjacentTo(Entity other) {
        return other != null
                && this.cell != null
                && other.cell != null
                && this.cell.isAdjacentTo(other.cell);
    }

    public boolean isAt(Cell cell) {
        return this.cell != null && this.cell.equals(cell);
    }

    public boolean isAtSamePositionAs(Entity other) {
        return other != null
                && this.cell != null
                && this.cell.equals(other.cell);
    }

    public char getSymbol() {
        return SymbolRegistry.getSymbolFor(this);
    }

    public String getDisplayName() {
        return displayName.isEmpty() ? getClass().getSimpleName() : displayName;
    }

    public boolean isPresent() {
        return true;
    }

    @Override
    public String toString() {
        String name = getDisplayName();
        String id = Integer.toHexString(System.identityHashCode(this) & 0xFFFF);
        String pos = String.format("@(%d,%d)", getX(), getY());
        return String.format("%s#%s%s", name, id, pos);
    }
}