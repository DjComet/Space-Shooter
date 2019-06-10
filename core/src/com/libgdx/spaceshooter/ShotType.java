package com.libgdx.spaceshooter;

public enum ShotType {
    SE(2),
    AE(3),
    PLNORMAL(1),
    PLSPECIAL(0),
    OVNINORMAL(4),
    OVNILASER(5);

    private int value;

     ShotType(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }
}
