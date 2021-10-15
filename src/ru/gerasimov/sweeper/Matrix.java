package ru.gerasimov.sweeper;

class Matrix
{
    private Space[][] matrix;

    Matrix(Space defaultSpace)
    {
        matrix = new Space[Diapazone.getSize().x][Diapazone.getSize().y];
        for (Coords coords : Diapazone.getAllCoords())
        {
            matrix[coords.x][coords.y] = defaultSpace;
        }
    }

    Space get(Coords coords)
    {
        if (Diapazone.inRange (coords))
            return matrix[coords.x][coords.y];
        return null;
    }

    void set (Coords coords, Space space)
    {
        if (Diapazone.inRange (coords))
            matrix[coords.x][coords.y] = space;
    }
}
