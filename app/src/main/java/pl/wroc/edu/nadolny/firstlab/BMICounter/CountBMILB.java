package pl.wroc.edu.nadolny.firstlab.BMICounter;

/**
 * Created by matryl1 on 15.03.2017.
 */

public class CountBMILB implements ICountBMI{
    static final float MINIMAL_MASS = 22f;
    static final float MAXIMAL_MASS = 551f;
    static final float MINIMAL_HEIGHT = 20f;
    static final float MAXIMAL_HEIGHT = 99f;

    @Override
    public boolean isValidMass(float mass) {
        return mass >= MINIMAL_MASS && mass <= MAXIMAL_MASS;
    }

    @Override
    public boolean isValidHeight(float height) {
        return height >= MINIMAL_HEIGHT && height <= MAXIMAL_HEIGHT;
    }

    @Override
    public float countBMI(float mass, float height) throws Exception{
        if(isValidHeight(height) && isValidMass(mass))
            return 703 * mass/(height*height);
        else throw new Exception("Jedna lub więcej niepoprawnych wartości : mass = " + mass +", height = " + height);
    }
}
