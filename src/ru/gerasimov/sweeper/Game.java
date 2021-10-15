package ru.gerasimov.sweeper;

public class Game
{
    private Bomb bomb;
    private Flag flag;
    private GameState state;

    public GameState getState()
    {
        return state;
    }

    public Game(int cols, int rows, int bombs)
    {
        Diapazone.setSize(new Coords(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start()
    {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public Space getBox (Coords coords)
    {
        if (flag.get(coords) == Space.OPENED)
            return bomb.get(coords);
        else
           return flag.get(coords);
    }

    public void pressLeftButton(Coords coords)
    {
        if(gameOver()) return;
        openBox(coords);
        checkWinner();
    }

    private void checkWinner()
    {
        if (state == GameState.PLAYED)
        {
            if (flag.getCountOfCloseBoxes() == bomb.getTotalBombs())
                state = GameState.WINNER;
        }
    }

    private void openBox(Coords coords)
    {
        switch (flag.get(coords))
        {
            case OPENED : setOpenedToClosedBoxesAroundNumber(coords);return;
            case FLAGED : return;
            case CLOSED:
                switch (bomb.get(coords))
                {
                    case ZERO : openBoxesAround(coords); return;
                    case BOMB : openBombs(coords); return;
                    default : flag.setOpenedToBox(coords); return;
                }

        }
    }

    private void setOpenedToClosedBoxesAroundNumber(Coords coords)
    {
        if(bomb.get(coords) != Space.BOMB)
            if (flag.getCountOfFlagedBoxesAround(coords) == bomb.get(coords).getNumber())
                for (Coords around: Diapazone.getCoordAround(coords))
                    if (flag.get(around) == Space.CLOSED)
                        openBox(around);
    }

    private void openBombs(Coords bombed)
    {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coords coords : Diapazone.getAllCoords())
            if (bomb.get(coords) == Space.BOMB)
                flag.setOpenedToCloseBombBox(coords);
            else
                flag.setNobombToFlagedSafeBox(coords);
    }

    private void openBoxesAround(Coords coords)
    {
        flag.setOpenedToBox(coords);
        for (Coords around : Diapazone.getCoordAround(coords))
            openBox(around);
    }

    public void pressRightButton(Coords coords)
    {
        if(gameOver()) return;
        flag.toggleFlagedToBox(coords);
    }

    private boolean gameOver()
    {
        if(state == GameState.PLAYED)
            return false;
        start();
        return true;
    }
}
