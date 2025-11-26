package model.entities;

import model.world.Cell;

public abstract class Entity {
    private Cell cell;

    protected Entity(Cell cell) {
        this.cell = cell;
    }

    public int getX() { return cell.x; }
    public int getY() { return cell.y; }
    public Cell getCell() { return cell; }

    public void setPosition(int x, int y) {
        this.cell = new Cell(x, y);
    }

    public void setPosition(Cell cell) {
        this.cell = cell;
    }

    public abstract char getSymbol();
    public abstract boolean isPresent();
}