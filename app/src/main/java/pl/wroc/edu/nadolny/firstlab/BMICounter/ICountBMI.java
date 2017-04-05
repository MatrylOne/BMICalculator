package pl.wroc.edu.nadolny.firstlab.BMICounter;

public interface ICountBMI {
    boolean isValidMass(float mass);
    boolean isValidHeight(float height);
    float countBMI(float mass, float height) throws Exception;
}
