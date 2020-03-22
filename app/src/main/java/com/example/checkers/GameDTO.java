package com.example.checkers;

import android.graphics.Point;

import java.util.Arrays;

public class GameDTO {

    private static final int BOARD_HIGH = 8;
    private static final int BOARD_WIDTH = 8;

    private PlayerEnum currentPlayer;
    private boolean status;
    private boolean gameOwner;
    private StoneEnum gameBoard[][];
    private StoneEnum oldGameBoard[][];

    public GameDTO(){
        currentPlayer = PlayerEnum.WHITE;
        status = true;
        gameOwner = true;
        gameBoard = new StoneEnum[][]{
                {StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null},
                {null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER},
                {StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null},
                {null, StoneEnum.BLACK_SOLIDER, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, StoneEnum.BLACK_SOLIDER, null, StoneEnum.BLACK_SOLIDER, null, StoneEnum.BLACK_SOLIDER, null, StoneEnum.BLACK_SOLIDER},
                {StoneEnum.BLACK_SOLIDER, null, StoneEnum.BLACK_SOLIDER, null, StoneEnum.BLACK_SOLIDER, null, StoneEnum.BLACK_SOLIDER, null},
                {null, StoneEnum.BLACK_SOLIDER, null, StoneEnum.BLACK_SOLIDER, null, StoneEnum.BLACK_SOLIDER, null, StoneEnum.BLACK_SOLIDER}
        };
        oldGameBoard = copy(gameBoard);

    }

    public PlayerEnum getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerEnum currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public StoneEnum[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(StoneEnum[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    public StoneEnum[][] getOldGameBoard() {
        return oldGameBoard;
    }

    public void setOldGameBoard(StoneEnum[][] oldGameBoard) {
        this.oldGameBoard = oldGameBoard;
    }

    public boolean isGameOwner() {
        return gameOwner;
    }

    public void setGameOwner(boolean gameOwner) {
        this.gameOwner = gameOwner;
    }

    public boolean isMove(Point from, Point to) {
        if(gameBoard[to.y][to.x] != null){
            return false;
        }
        if(currentPlayer == PlayerEnum.WHITE){
            if(from.y + 1 != to.y){
                return false;
            }
            if(Math.abs(from.x - to.x) != 1){
                return false;
            }
        }
        return  true;
    }

    public void move(Point from, Point to) {
        gameBoard[from.y][from.x] = null;
        switch (currentPlayer){
            case WHITE:
                gameBoard[to.y][to.x] = StoneEnum.WHITE_SOLIDER;
                break;
            case BLACK:
                gameBoard[to.y][to.x] = StoneEnum.BLACK_SOLIDER;
                break;
            default:
                break;
        }

    }

    public boolean isEat(Point from, Point to) {
        if(gameBoard[to.y][to.x] != null){
            return false;
        }
        if(currentPlayer == PlayerEnum.WHITE){
            if(from.y + 2 != to.y){
                return false;
            }
            if(Math.abs(from.x - to.x) != 2){
                return false;
            }
            if(getBetween(to, from) != StoneEnum.BLACK_SOLIDER){
                return false;
            }
        }
        return true;
    }

    private StoneEnum getBetween(Point to, Point from) {
        int x = (from.x + to.x)/2;
        int y = (from.y + to.y)/2;
        return gameBoard[y][x];
    }

    public void eat(Point from, Point to) {
        move(from, to);
        int x = (from.x + to.x)/2;
        int y = (from.y + to.y)/2;
        gameBoard[y][x] = null;
    }

    public Point getEatPoint(Point from, Point to) {
        int x = (from.x + to.x)/2;
        int y = (from.y + to.y)/2;
        return new Point(x, y);
    }

    private StoneEnum[][] copy(StoneEnum[][] original) {
        StoneEnum[][] res = new StoneEnum[BOARD_HIGH][BOARD_WIDTH];
        for(int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                res[i][j] = original[i][j];
            }
        }
        return res;
    }

    public void refreshGameBoard() {
        gameBoard = copy(oldGameBoard);
    }

    public void updateOldGameBoard() {
        oldGameBoard = copy(gameBoard);
    }

    public void switchPlayer() {
        if(currentPlayer == PlayerEnum.BLACK){
            currentPlayer = PlayerEnum.WHITE;
        } else{
            currentPlayer = PlayerEnum.BLACK;
        }
    }
}
