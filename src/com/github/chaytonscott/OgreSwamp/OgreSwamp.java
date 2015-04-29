//*****************************************************************************
// Author:      Chayton
// Date:        04/2015
// ProgramName: OgreSwamp
// Description: A map that solves a maze that can be inputted by the user or
//      a map can randomly be generated by the program.
//*****************************************************************************
package com.github.chaytonscott.OgreSwamp;

import java.util.Scanner;

public class OgreSwamp {
    Map map   = new Map();
    Ogre ogre = new Ogre();
    Sinkhole sinkholes = new Sinkhole();

    final int OGRE_SIZE = 4;
    final int MAX_DIMENSIONS = 10;


    public static void main(String [] args) {
        OgreSwamp controller = new OgreSwamp();
        controller.startSystem();
    }

    public void startSystem() {
        System.out.printf("Welcome to Ogre Swamp, please enter a map. (Type random to randomly generate a map)\n");
        String input[] = getInput();
        generateMap(input);
        getOgrePosition();
        getSinkholePositions();

        System.out.print("OGRE POSITIONS:\t");
        for (int i = 0; i < 4; i++) {
            System.out.printf("%s ", ogre.get_position(i).toString());
        }

        System.out.print("\nSINKHOLE POSITIONS:\t");
        for (int i = 0; i < sinkholes.amountOfSinkholes(); i++) {
            System.out.printf("%s ", sinkholes.get_position(i).toString());
        }

        Coordinate goldPosition = getGoldPosition();
        System.out.print("\nGOLD POSITION:\t" + goldPosition.toString());
        printMap();
    }

    public String[] getInput() {
        Scanner in = new Scanner(System.in);
        String input[] = new String[MAX_DIMENSIONS];
        for (int i = 0; i < 10; i++) {
            input[i] = in.nextLine();
        }

        while(!validInput(input)) {
            System.out.print("Invalid input, please try again...\n");
            for (int i = 0; i < MAX_DIMENSIONS; i++) {
                input[i] = in.nextLine();
            }
        }
        return input;
    }

    public boolean validInput(String input[]) {
        if (input[0].equalsIgnoreCase("random")) {
            return true;
        }

        for (int i = 0; i < MAX_DIMENSIONS; i++) {
            if (input[i].charAt(i) == '@' || input[i].charAt(i) == '.' || input[i].charAt(i) == '$' || input[i].charAt(i) == 'O') {
            } else {
                return false;
            }
        }
        return true;
    }

    public void generateMap(String[] input) {
        int tempMap[][] = new int[MAX_DIMENSIONS][MAX_DIMENSIONS];
        for (int line = 0; line < MAX_DIMENSIONS; line++) {
            for (int column = 0; column < MAX_DIMENSIONS; column++) {
                if (input[line].charAt(column) == '.') {
                    tempMap[line][column] = 0;
                } else if (input[line].charAt(column) == 'O') {
                    tempMap[line][column] = 3;
                } else if (input[line].charAt(column) == '$') {
                    tempMap[line][column] = 6;
                } else if (input[line].charAt(column) == '@') {
                    tempMap[line][column] = 9;
                }
            }
        }
        map = new Map(tempMap);
    }

    public void getOgrePosition() {
        int map[][] = this.map.getMap();
        int coordinateLoop = 0;
        for (int line = 0; line < MAX_DIMENSIONS; line++) {
            for (int column = 0; column < MAX_DIMENSIONS; column++) {
                if (map[line][column] == 9) {
                    Coordinate ogrePosition = new Coordinate(line, column);
                    ogre.setPositions(ogrePosition, coordinateLoop++);
                }
            }
        }
    }

    public void getSinkholePositions() {
        int map[][] = this.map.getMap();
        for (int line = 0; line < MAX_DIMENSIONS; line++) {
            for (int column = 0; column < MAX_DIMENSIONS; column++) {
                if (map[line][column] == 3) {
                    Coordinate sinkholePosition = new Coordinate(line, column);
                    sinkholes.setPositions(sinkholePosition);
                }
            }
        }
    }

    public Coordinate getGoldPosition() {
        int map[][] = this.map.getMap();
        for (int line = 0; line < MAX_DIMENSIONS; line++) {
            for (int column = 0; column < MAX_DIMENSIONS; column++) {
                if (map[line][column] == 6) {
                    Coordinate goldPosition = new Coordinate(line, column);
                    return goldPosition;
                }
            }
        }
        return null;
    }

    public void moveOgreUp() {
        ogre.editX(-1);
    }

    public void moveOgreDown() {
        ogre.editX(+1);
    }

    public void moveOgreRight() {
        ogre.editY(+1);
    }

    public void moveOgreLeft() {
        ogre.editY(-1);
    }

    public void findSolution() {
        String moves = "";
        int moveCount = 0;
        while (!didWeFindGold()) {
            int move = findSafeMove();
            switch(move) {
                case 0:
                    moveOgreUp();
                    moves += "U";
                    break;
                case 1:
                    moveOgreDown();
                    moves += "D";
                    break;
                case 2:
                    moveOgreRight();
                    moves += "R";
                    break;
                case 3:
                    moveOgreLeft();
                    moves += "L";
                    break;
            }
            System.out.println(moves);
        }

    }

    public boolean didWeFindGold() {
        for (int i = 0; i < OGRE_SIZE; i++) {
            if (ogre.get_position(i).get_x() == getGoldPosition().get_x() || ogre.get_position(i).get_y() == getGoldPosition().get_y()) {
                return true;
            }
        }
        return false;
    }

    public int findSafeMove() {
        Ogre tempOgre = ogre;
        tempOgre.editY(+1);
        if (safeMove(tempOgre)) {
            return 2;
        } else {
            tempOgre.editY(-1);
        }

        tempOgre.editY(-1);
        if (safeMove(tempOgre)) {
            return 3;
        } else {
            tempOgre.editY(+1);
        }

        tempOgre.editX(-1);
        if (safeMove(tempOgre)) {
            return 0;
        } else {
            tempOgre.editX(+1);
        }

        tempOgre.editY(+1);
        if (safeMove(tempOgre)) {
            return 1;
        } else {
            tempOgre.editX(-1);
        }
        return -1;
    }

    public boolean safeMove(Ogre ogre) {
        Coordinate testOgre[] = ogre.get_positions();
        for (int i = 0; i < sinkholes.amountOfSinkholes(); i++) {
            for (int x = 0; x < OGRE_SIZE; x++) {
                if (compare(testOgre[x], sinkholes.get_position(i))) {
                    return false;
                }
                if (testOgre[x].get_x() < 0 || testOgre[x].get_x() > 9) {
                    return false;
                }
                if (testOgre[x].get_x() < 0 || testOgre[x].get_x() > 9) {
                    return false;
                }
            }
        }
        return true;
    }

    //Method to print the map...
    public void printMap() {
        int[][] printMap = map.getMap();
        System.out.println();
        for (int x = 0; x < MAX_DIMENSIONS; x++) {
            for (int y = 0; y < MAX_DIMENSIONS; y++) {
                System.out.printf("%d ", printMap[x][y]);
            }
            System.out.println();
        }
    }

    public boolean compare(Coordinate a, Coordinate b) {
        if (a.get_x() == b.get_x() && a.get_y() == b.get_y()) {
            return true;
        }
        return false;
    }
}