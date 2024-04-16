package org.example.checkers.game;

import javafx.scene.Group;
import org.example.checkers.move.*;
import org.example.checkers.piece.*;

import java.util.ArrayList;

public class Game {
    public static final int BORDER_SIZE = 60;
    public static final int TILE_SIZE = 90;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private final GameOver gameOver = new GameOver(this);
    private final TimeInfo timeInfo = new TimeInfo(this);
    private final TurnInfoOnline turnInfoOnline = new TurnInfoOnline(this);
    private final TurnInfoTwoPlayers turnInfoTwoPlayers = new TurnInfoTwoPlayers(this);

    MoveValidator normalMoveValidator = new NormalMoveValidator();
    PieceMover normalMover = new NormalMover();
    PieceKiller normalPieceKiller = new NormalPieceKiller();
    KillScanner normalKillScanner = new NormalKillScanner();
    Move normalMove = new NormalMove(normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner);

    MoveValidator queenMoveValidator = new QueenMoveValidator();
    PieceMover queenMover = new QueenMover();
    PieceKiller queenPieceKiller = new QueenPieceKiller();
    KillScanner queenKillScanner = new QueenKillScanner();
    Move queenMove = new QueenMove(queenMoveValidator, queenMover, queenPieceKiller, queenKillScanner);

    private final Group tileGroup = new Group();
    private final Group pieceGroup = new Group();

    private final ArrayList<Piece> board = new ArrayList<>();
    private final BlackPieces blackPieces = new BlackPieces(this, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner);
    private final WhitePieces whitePieces = new WhitePieces(this, normalMoveValidator, normalMover, normalPieceKiller, normalKillScanner);

    private Gamemode gamemode = Gamemode.TWO_PLAYERS;
    private PieceColor turn;
    private boolean isTurnChanged = false;
    private PieceColor playerColor = PieceColor.WHITE;
    private boolean wasKill;
    private boolean canBlackKill;
    private boolean canWhiteKill;
    private int numberOfWhitePieces;
    private int numberOfBlackPieces;
    private boolean isRunning = true;


    public Game() {
        timeInfo.startCounting();
        prepareBoard();
    }

    public void prepareBoard() {
        tileGroup.getChildren().clear();
        pieceGroup.getChildren().clear();
        board.clear();

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                tileGroup.getChildren().add(tile);
            }
        }

        board.addAll(blackPieces.createUpBlackPieces());
        board.addAll(whitePieces.setUpWhitePieces());

        board.forEach(piece -> pieceGroup.getChildren().add(piece));

        turn = PieceColor.WHITE;
        wasKill = false;
        canBlackKill = false;
        canWhiteKill = false;
        numberOfWhitePieces = 0;
        numberOfBlackPieces = 0;

        timeInfo.restartCounting();
    }

    public boolean isPieceOnCords(int col, int row) {
        for (Piece pieceSearch : board) {
            if (pieceSearch.getCol() == col && pieceSearch.getRow() == row) {
                return true;
            }
        }

        return false;
    }

    public Piece findPiece(int col, int row) {
        Piece piece = null;

        for (Piece pieceSearch : board) {
            if (pieceSearch.getCol() == col && pieceSearch.getRow() == row) {
                piece = pieceSearch;
                break;
            }
        }

        return piece;
    }

    public void removePiece(Piece piece) {
        board.remove(piece);
        pieceGroup.getChildren().remove(piece);
    }

    public void addPiece(Piece piece) {
        board.add(piece);
        pieceGroup.getChildren().add(piece);
    }

    public void clearBoard() {
        board.clear();
        pieceGroup.getChildren().clear();
    }

    public void createQueen(Piece piece) {
        if (piece.getColor().isWhite()) turn = PieceColor.BLACK;
        if (piece.getColor().isBlack()) turn = PieceColor.WHITE;

        board.remove(piece);
        pieceGroup.getChildren().remove(piece);
        Piece Queen = new QueenPiece(this, piece.getCol(), piece.getRow(), PieceType.QUEEN, piece.getColor(), queenMoveValidator, queenMover, queenPieceKiller, queenKillScanner);
        board.add(Queen);
        pieceGroup.getChildren().add(Queen);
    }

    public void calculate() {
        int blackCounter = 0;
        int whiteCounter = 0;

        for (Piece pieceSearch : board) {
            if (pieceSearch.canKill(this)) {
                pieceSearch.setCanKill(true);
                if (pieceSearch.getColor().isBlack()) blackCounter++;
                else whiteCounter++;
            } else pieceSearch.setCanKill(false);
        }

        canBlackKill = blackCounter > 0;
        canWhiteKill = whiteCounter > 0;
    }

    public void changeTurn(Piece piece) {
        countPieces();

        calculate();

        if (piece != null) {
            if (piece.getCanKill() && wasKill) return;
        }

        wasKill = false;

        if (piece != null) {
            if (piece.getColor().isWhite()) turn = PieceColor.BLACK;
            else turn = PieceColor.WHITE;
            notifyTurnChange();
        }

        if (gamemode.isTwoPlayers()) turnInfoTwoPlayers.updateTurnLabel();
        if (isGameOver()) getGameInfo().displayEndGameMessage();
    }

    public void countPieces() {
        numberOfBlackPieces = 0;
        numberOfWhitePieces = 0;
        for (Piece pieceSearch : board) {
            if (pieceSearch.getColor().isBlack()) numberOfBlackPieces++;
            if (pieceSearch.getColor().isWhite()) numberOfWhitePieces++;
        }
    }

    public boolean isGameOver() {
        if (getNumberOfWhitePieces() == 0 || getNumberOfBlackPieces() == 0) {
            isRunning = false;
            return true;
        }
        return false;
    }

    public synchronized void waitForTurnChange() throws InterruptedException {
        while (!isTurnChanged) {
            wait();
        }
        isTurnChanged = false;
    }

    public synchronized void notifyTurnChange() {
        isTurnChanged = true;
        notifyAll();
    }

    public TurnInfoOnline getTurnInfoOnline() {
        return turnInfoOnline;
    }

    public TurnInfoTwoPlayers getTurnInfoTwoPlayers() {
        return turnInfoTwoPlayers;
    }

    public Group getTileGroup() {
        return tileGroup;
    }

    public Group getPieceGroup() {
        return pieceGroup;
    }

    public GameOver getGameInfo() {
        return gameOver;
    }

    public PieceColor getTurn() {
        return turn;
    }

    public void setTurn(PieceColor turn) {
        this.turn = turn;
    }

    public PieceColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PieceColor playerColor) {
        this.playerColor = playerColor;
    }

    public void setWasKill(boolean wasKill) {
        this.wasKill = wasKill;
    }

    public boolean getCanBlackKill() {
        return canBlackKill;
    }

    public boolean getCanWhiteKill() {
        return canWhiteKill;
    }

    public int getNumberOfWhitePieces() {
        return numberOfWhitePieces;
    }

    public int getNumberOfBlackPieces() {
        return numberOfBlackPieces;
    }

    public MoveValidator getNormalMoveValidator() {
        return normalMoveValidator;
    }

    public PieceMover getNormalMover() {
        return normalMover;
    }

    public PieceKiller getNormalPieceKiller() {
        return normalPieceKiller;
    }

    public KillScanner getNormalKillScanner() {
        return normalKillScanner;
    }

    public Move getNormalMove() {
        return normalMove;
    }

    public MoveValidator getQueenMoveValidator() {
        return queenMoveValidator;
    }

    public PieceMover getQueenMover() {
        return queenMover;
    }

    public PieceKiller getQueenPieceKiller() {
        return queenPieceKiller;
    }

    public KillScanner getQueenKillScanner() {
        return queenKillScanner;
    }

    public Move getQueenMove() {
        return queenMove;
    }

    public TimeInfo getTimeInfo() {
        return timeInfo;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public ArrayList<Piece> getBoard() {
        return board;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}