/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cu.edu.cujae.ed.snetwork.logic;

import cu.edu.cujae.ed.snetwork.utils.Friendship;
import cu.edu.cujae.ed.snetwork.utils.Notification;
import cu.edu.cujae.ed.snetwork.utils.NotificationType;
import cu.edu.cujae.graphy.utils.Pair;

/**
 *
 * @author Jose
 */
public final class Person
{
    private String name;
    private String lastName;
    private String ID;
    private String country;
    private String profession;
    
    public Person (String name, String lastName, String ID, String country, String profession){
        setName(name);
        setLastName(lastName);
        setID(ID);
        setCountry(country);
        setProfession(profession);
        
    }
    
    //Getters
    public String getName(){ return this.name;}
    public String getLastName(){return this.lastName;}
    public String getID(){ return this.ID;}
    public String getCountry(){ return this.country;}
    public String getProfession(){ return this.profession;}
    
    //Setters
    public void setName(String name) throws IllegalArgumentException{
        if(name!=null){
            this.name = name;
        } else{
            throw new IllegalArgumentException();
        }
    }
    
    public void setLastName(String lastName) throws IllegalArgumentException{
        if(lastName != null){
            this.lastName = lastName;
        }else {
            throw new IllegalArgumentException();
        }
    }
        public void setCountry( String country) throws IllegalArgumentException{
        if(country != null){
            this.country = country;
        } else{
            throw new IllegalArgumentException();
        }
    }
    
    public void setProfession (String profession) throws IllegalArgumentException{
        if(profession!= null){
            this.profession = profession;
        } else{
            throw new IllegalArgumentException();
        }
    }
    
    public void setID (String ID){
        		boolean flag = true;
		String dayA, monthA, yearA;
		int dayB, monthB, yearB;
		if(ID.length() == 11){
			for(int i =0; i<ID.length(); i++) {
				flag = Character.isDigit(ID.charAt(i));
			}
			if(flag) {
				dayA = ID.substring(4, 6);
				monthA = ID.substring(2, 4);
				yearA = ID.substring(0, 2);
				dayB = Integer.parseInt(dayA);
				monthB = Integer.parseInt(monthA);
				yearB = Integer.parseInt(yearA);

				if((yearB>50 && yearB<100) || (yearB>=0 && yearB<5)){
					if(monthB>0 && monthB<=12) {
						if(monthB==1 || monthB==3 || monthB==5 || monthB==7 || monthB==8 || monthB==10 || monthB==12) {
							if(dayB>31) {
								throw new IllegalArgumentException ("Fecha no valida");
							}
						}
						else if (monthB==4 || monthB==6 || monthB==9 || monthB==11) {
							if(dayB>30) {
								throw new IllegalArgumentException("Fecha no valida");
							}
						}
						else if(monthB == 2) {
							if(yearB%4!=0 && dayB>28 ) {
								throw new IllegalArgumentException("Fecha no valida");
							}
							else if(yearB%4==0 && dayB>29) {
								throw new IllegalArgumentException("Fecha no valida");
							}
						}
					}
					else {
						throw new IllegalArgumentException("Mes no valido");
					}
				}
				else {
					throw new IllegalArgumentException("Ano no valido");
				}
			}

			else {
				throw new IllegalArgumentException("El CI solo puede contener digitos");
			}
			
			this.ID = ID;
		}
		else {
			throw new IllegalArgumentException ("El CI debe tener 11 digitos"+ ID);
		}
    }
    
    public boolean equals (Person p){
         return p.getCountry().equalsIgnoreCase(country)? p.getID().equals(ID) : false;
    }
    
    public Pair<Notification<Friendship>,Person> sendFriendRequest (int amountOfWork, Person p){
        return new Pair <> ( new Notification<> (NotificationType.FRINDSHIP_REQUEST,"Desearía trabajar con usted",new Friendship (this,amountOfWork)),p) ;
    }
    
    public Pair<Notification<Friendship>,Person> sendWorkLoadModification (int amountOfWork, Person p){
        return new Pair <>(new Notification<> (NotificationType.WORKLOAD_MODIFICATION,"Desearia modificar la cantidad de trabajo que compartimos", new Friendship (this,amountOfWork)),p);
    }
    
}
