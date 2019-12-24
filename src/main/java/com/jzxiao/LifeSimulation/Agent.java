package com.jzxiao.LifeSimulation;

import java.util.*;
public class Agent implements Display
{
    // instance variables
    private int type, energy, stepdrain,aging,lifespan,counter,direction;
    private boolean immune, healer;
    private Disease disease;
    private Random r = new Random();

    public Agent(int type, int energy, int stepdrain, int aging, int lifespan, boolean immune, boolean healer)
    {
        // initialise instance variables
        this.type = type;
        this.energy = energy;
        this.stepdrain = stepdrain;
        this.aging = aging;
        this.lifespan = lifespan;
        counter = 0;
    }

    public int getType() //return type of Agent
    {
        return type;
    }

    public int getEnergy()//return energy of Agent
    {
        return energy; 
    }

    public void setDirection(int num)//updates direction 
    {
        direction = num;
    }

    public int getDirection() //returns direction of Agent 
    {
        return direction; 
    }

    public void step() //removes the step energy from energy 
    {
        energy -= stepdrain;
    }

    public int getCounter() //returns counter
    {
        return counter;
    }

    public void gainEnergy(int num)//adds num to energy 
    {
        energy += num;
    }

    public void loseEnergy(int num)//subtracts num to energy 
    {
        energy -= num;
    }

    public boolean isImmune()//returns immune status 
    {
        return immune;
    }

    public boolean isHealer()//returns healer status 
    {
        return healer;
    }

    public Disease getDisease()//return Disease 
    {
        return disease;
    }

    public void gainDisease(Disease d) //updates disease 
    {
        if (!immune) //if agent is not immune
        {
            disease = d; //they get diseased 
        }
    }

    public void iterate() //describes under what conditions agent lives 
    {
        counter += aging;
        if (disease != null) //if agent has disease 
        {
            energy -= disease.getDamage(); //subtract dammage amount from energy 
        }
        if(energy <= 0 || counter > lifespan) //if energy <= 0, or  counter>lifespan, agent dies 
        {
            type = 0;
        }
    }

    public int move(int prevmove) //decides movement of agent 
    {
        if (r.nextInt(2) == 0) //strong chance agent will move in same direction 
        {
            direction = prevmove; 
            return direction;
        }
        else //agent will move in a randomly chosen direction 
        {
            direction = r.nextInt(5)+1;
            return direction; 
        }
    }
}
