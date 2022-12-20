package com.example.ronlulwi_205857394;

public class Position {

    private int row;
    private int col;
    public enum types { UFO, COIN, HEART }
    private types type;


    public Position(int row, int col, types type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }

    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row += row;
    }

    public int getCol() {
        return col;
    }
    public void setCol(int col) {
        this.col = col;
    }

    public types getType() { return type; }
    public void setType(types type) { this.type = type; }
}
