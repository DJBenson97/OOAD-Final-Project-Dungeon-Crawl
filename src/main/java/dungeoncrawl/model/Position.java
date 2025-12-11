package dungeoncrawl.model;

import java.util.Objects;

public class Position {

    // row index of the position in the maze
    private final int row;

    // column index of the position in the maze
    private final int col;

    // creates a new position with the given row and column
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // returns the row value
    public int getRow() {
        return row;
    }

    // returns the column value
    public int getCol() {
        return col;
    }

    // shorthand accessor for row
    public int row() {
        return row;
    }

    // shorthand accessor for column
    public int col() {
        return col;
    }

    // returns a new position one row above
    public Position up() {
        return new Position(row - 1, col);
    }

    // returns a new position one row below
    public Position down() {
        return new Position(row + 1, col);
    }

    // returns a new position one column to the left
    public Position left() {
        return new Position(row, col - 1);
    }

    // returns a new position one column to the right
    public Position right() {
        return new Position(row, col + 1);
    }

    @Override
    public boolean equals(Object obj) {
        // check for reference equality
        if (this == obj) return true;

        // ensure the object is a position
        if (!(obj instanceof Position)) return false;

        // compare row and column values
        Position other = (Position) obj;
        return row == other.row && col == other.col;
    }

    @Override
    public int hashCode() {
        // generate a hash code based on row and column
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        // return a readable string representation of the position
        return "(" + row + ", " + col + ")";
    }
}