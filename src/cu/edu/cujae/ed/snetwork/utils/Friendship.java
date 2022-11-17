/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cu.edu.cujae.ed.snetwork.utils;

import cu.edu.cujae.ed.snetwork.logic.Person;

/**
 *
 * @author Jose
 */
public class Friendship
{
    private Person person;
    private int amountOfWork;
    
    public Friendship (Person person, int amountOfWork){
        this.person = person;
        this.amountOfWork = amountOfWork;
    }
    
    public Person getPerson (){return person;}
    public int getAmountOfWork(){return amountOfWork;}
}
