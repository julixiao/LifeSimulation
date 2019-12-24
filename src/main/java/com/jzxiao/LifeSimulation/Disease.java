package com.jzxiao.LifeSimulation;

public class Disease
{
    // instance variables
    private int damage;
    private double spread, contact; 
    private int recovery;
    private boolean[] victims;

    public Disease(int damage, double contact, double spread, int recovery, boolean[] victims)
    {
        // initialise instance variables
        this.damage = damage;
        this.spread = spread;
        this.recovery = recovery;
        this.victims = victims;
        this.contact = contact; 
    }

    public int getDamage() //method returns damage 
    {
        return damage;
    }
    
    public double getContact() //method returns contact value 
    {
        return contact; 
    }

    public double getSpread() //method returns spread value 
    {
        return spread;
    }

    public int getRecovery() //method returns recovery time 
    {
        return recovery;
    }
    
    public boolean[] getVictims() //method returns who the disease affects 
    {
        return victims; 
    }
}