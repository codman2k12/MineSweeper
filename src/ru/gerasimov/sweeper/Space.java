package ru.gerasimov.sweeper;

public enum Space
{
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;

    public Object image;

    Space getNextNumberBox()
    {
        return Space.values()[this.ordinal() + 1];
    }

    int getNumber()
    {
        return this.ordinal();
    }
}
