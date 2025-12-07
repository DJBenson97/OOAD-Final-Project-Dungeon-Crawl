package dungeoncrawl.model;

import java.util.Objects;

public class Position {
    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    public Position up() {
        return new Position(row - 1, col);
    }

    public Position down() {
        return new Position(row + 1, col);
    }

    public Position left() {
        return new Position(row, col - 1);
    }

    public Position right() {
        return new Position(row, col + 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position)) return false;
        Position other = (Position) obj;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}