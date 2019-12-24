package com.jzxiao.LifeSimulation;

public class Food implements Display
{
    // instance variables
    private int type, counter;
    private double deplete, energy, limit;
    private Disease disease;

    public Food(int type, double growth, double deplete, int limit)
    {
        // initialise instance variables
        this.type = type;
        this.energy = 1;
        this.deplete = deplete;
        this.limit = limit;
        counter = 0;
    }

    public Food(int type, double growth, double deplete, int limit, Disease disease)
    {
        // initialise instance variables
        this.type = type;
        this.energy = 1;
        this.deplete = deplete;
        this.limit = limit;
        this.disease = disease;
        counter = 0;
    }

    public int getType() //returns type 
    {
        return type;
    }

    public int getNutrition() //returns the amount of energy a food gives 
    {
        return (int)Math.round(deplete*10);
    }

    public void loseEnergy() //depletes depletiong thing to energy 
    {
        energy -= deplete;
    }

    public Disease getDisease() //access method to disease
    {
        return disease;
    }

    public boolean isDiseased() //returns true is food is diseased 
    {
        if (disease!=null)
            return true;
        else
            return false;
    }

    public void iterate() //if energy is less than 0 or counter greater than age limit, food becomes dead
    {
        counter++;
        if (energy <= 0 || counter > limit)
            type = 0; 
    }
}