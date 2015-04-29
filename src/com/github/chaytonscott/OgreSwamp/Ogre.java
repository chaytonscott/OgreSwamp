package com.github.chaytonscott.OgreSwamp;

public class Ogre {
    private Coordinate _positions[] = new Coordinate[4];

    public Coordinate get_position(int input) {
        return _positions[input];
    }

    public Coordinate[] get_positions() {
        return  _positions;
    }

    public void setPositions(Coordinate position, int order) {
        _positions[order] = position;
    }

    public void editX(int modifier) {
        for (int i = 0; i < 4; i ++) {
            _positions[i].set_x(_positions[i].get_x() + modifier);
        }
    }
    public void editY(int modifier) {
        for (int i = 0; i < 4; i++) {
            _positions[i].set_x(_positions[i].get_x() + modifier);
        }
    }
}
