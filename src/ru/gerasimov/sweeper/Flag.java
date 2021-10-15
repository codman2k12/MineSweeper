package ru.gerasimov.sweeper;

class Flag
{
    private Matrix flagMap;
    private int getCountOfCloseBoxes;

    void start()
    {
        flagMap = new Matrix(Space.CLOSED);
        getCountOfCloseBoxes = Diapazone.getSize().x * Diapazone.getSize().y;
    }

    Space get(Coords coords)
    {
        return flagMap.get(coords);
    }

    void setOpenedToBox(Coords coords)
    {
        flagMap.set(coords, Space.OPENED);
        getCountOfCloseBoxes --;
    }

    void toggleFlagedToBox(Coords coords)
    {
        switch (flagMap.get(coords))
        {
            case FLAGED : setClosedToBox(coords); break;
            case CLOSED : setFlagedToBox(coords); break;
        }
    }

    void setClosedToBox(Coords coords)
    {
        flagMap.set(coords, Space.CLOSED);
    }

    void setFlagedToBox(Coords coords)
    {
        flagMap.set(coords, Space.FLAGED);
    }

    int getCountOfCloseBoxes()
    {
        return getCountOfCloseBoxes;
    }

    public void setBombedToBox(Coords coords)
    {
        flagMap.set(coords, Space.BOMBED);
    }

    void setOpenedToCloseBombBox(Coords coords)
    {
        if(flagMap.get(coords) == Space.CLOSED)
            flagMap.set(coords, Space.OPENED);
    }

    void setNobombToFlagedSafeBox(Coords coords)
    {
        if (flagMap.get(coords) == Space.FLAGED)
            flagMap.set(coords, Space.NOBOMB);
    }

    int getCountOfFlagedBoxesAround(Coords coords)
    {
        int count = 0;
        for (Coords around: Diapazone.getCoordAround(coords))
            if (flagMap.get(around) == Space.FLAGED)
                count++;
        return count;
    }
}
