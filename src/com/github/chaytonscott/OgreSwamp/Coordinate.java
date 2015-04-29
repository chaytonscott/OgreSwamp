package com.github.chaytonscott.OgreSwamp;

public class Coordinate {
    private int _x, _y;

    public Coordinate(int x, int y) {
        _x = x;
        _y = y;
    }

    public int get_x() {
        return _x;
    }

    public void set_x(int _x) {
        this._x = _x;
    }

    public int get_y() {
        return _y;
    }

    public void set_y(int _y) {
        this._y = _y;
    }

    public String toString() {
        return "(" + _x + "," + _y + ")";
    }

}
