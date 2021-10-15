package ru.gerasimov.sweeper;

class Bomb
{
    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs)
    {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start()
    {
        bombMap = new Matrix(Space.ZERO);
        for (int j = 0; j < totalBombs; j++)
            placeBomb();
    }

    Space get(Coords coords)
    {
        return bombMap.get(coords);
    }

    private void fixBombsCount()
    {
        int maxBombs = Diapazone.getSize().x + Diapazone.getSize().y / 2;
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
    }
    private void placeBomb()
    {
        while (true) {
            Coords coords = Diapazone.getRandomCoord();
            if (Space.BOMB == bombMap.get(coords))
                continue;
            bombMap.set(coords, Space.BOMB);
            incNumberAroundBomb(coords);
            break;
        }
    }

    private void incNumberAroundBomb(Coords coords)
    {
        for (Coords around: Diapazone.getCoordAround(coords))
            if (Space.BOMB != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
    }

    int getTotalBombs()
    {
        return totalBombs;
    }
}
