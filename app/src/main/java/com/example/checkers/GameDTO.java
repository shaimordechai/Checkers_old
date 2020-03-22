package com.example.checkers;

import android.graphics.Point;

public class GameDTO {

    PlayerEnum currentPlayer;
    boolean status;
    boolean gameOwner;
    StoneEnum gameBoard[][];
    StoneEnum oldGameBoard[][];

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

    public boolean canMove(Point from, Point to) {
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

    public boolean canEat(Point from, Point to) {
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
        int x = (from.x + to.x)/2;
        int y = (from.y + to.y)/2;
        gameBoard[y][x] = null;
    }

    public Point getEatPoint(Point from, Point to) {
        int x = (from.x + to.x)/2;
        int y = (from.y + to.y)/2;
        return new Point(x, y);
    }
}
