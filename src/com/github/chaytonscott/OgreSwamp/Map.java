package com.github.chaytonscott.OgreSwamp;

/*
    Map Notes...
        0 - empty spot...
        3 - sink hole...
        5 - Moved space...
        6 - ogre's gold...
        9 - 1/4 of ogre...
 */
public class Map {
    private int _map[][] = new int[10][10];

    public Map() {
    }

    public Map(int[][] map) {
        _map = map;
    }

    public int[][] getMap() {
        return _map;
    }

    public void editMap(int pos1, int pos2, int modifier) {
        _map[pos1][pos2] = modifier;
    }
}
