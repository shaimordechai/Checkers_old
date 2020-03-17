package com.example.checkers;

public class GameDTO {

    PlayerEnum currentPlayer;
    boolean status;
    StoneEnum gameBoard[][];
    StoneEnum oldGameBoard[][];

    public GameDTO(){
        currentPlayer = PlayerEnum.WHITE;
        status = true;
        gameBoard = new StoneEnum[][]{
                {StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null},
                {null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER},
                {StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null, StoneEnum.WHITE_SOLIDER, null},
                {null, null, null, null, null, null, null, null},
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
}
