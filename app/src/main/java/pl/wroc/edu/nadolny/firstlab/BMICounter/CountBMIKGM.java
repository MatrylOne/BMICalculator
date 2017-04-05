package pl.wroc.edu.nadolny.firstlab.BMICounter;

/**
 * Created by matryl1 on 15.03.2017.
 */

public class CountBMIKGM implements ICountBMI{
    static final float MINIMAL_MASS = 10f;
    static final float MAXIMAL_MASS = 250f;
    static final float MINIMAL_HEIGHT = 0.5f;
    static final float MAXIMAL_HEIGHT = 2.5f;

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
            return mass/(height*height);
        else throw new Exception("Jedna lub więcej niepoprawnych wartości : mass = " + mass +", height = " + height);
    }
}
