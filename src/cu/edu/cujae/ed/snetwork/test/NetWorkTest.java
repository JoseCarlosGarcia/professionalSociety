/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cu.edu.cujae.ed.snetwork.test;

import cu.edu.cujae.ed.snetwork.logic.ApplicationController;
import cu.edu.cujae.ed.snetwork.logic.Person;
import cu.edu.cujae.graphy.core.utility.Weights;
import java.util.LinkedList;

/**
 *
 * @author Jose
 */
public class NetWorkTest
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        ApplicationController snet = ApplicationController.getInstance();
        Person brian = new Person("Brian Michel", "Morales", "02102768988", "Australia", "Ingeniero Automatico");
        Person jose = new Person("Jose Carlos", "Garcia Cruz", "01081268460", "Espana", "Analista de requisitos");
        Person nanda = new Person("Ananda", "Morales", "02090566997", "Rusia", "Redactora de informes");
        Person camilo = new Person("Camilo", "Chamorro", "01080725688", "Cuba", "Probador de software");
        Person amanda = new Person("Amanda", "Mendez", "02091868898", "Mexico", "Disenadora");
        Person javi = new Person("Javier", "Marrero", "01040368898","Alemania","Disenador de Software");
        Person amaya = new Person ("Amaya","Delgado", "02111268898","Inglaterra", "Desarrolladora de videojuegos");
        Person dory = new Person ("Barbara", "Teresa", "04031568898", "Grecia", "Psicologa");
        snet.addPerson(brian);
        snet.addPerson(jose);
        snet.addPerson(nanda);
        snet.addPerson(camilo);
        snet.addPerson(amanda);
        snet.addPerson(javi);
        snet.addPerson(amaya);
        snet.addPerson(dory);
        snet.getsocialNetWork().connect(0, 1, Weights.makeWeight(4));
        snet.getsocialNetWork().connect(0, 2, Weights.makeWeight(3));
        snet.getsocialNetWork().connect(1, 2, Weights.makeWeight(5));
        snet.getsocialNetWork().connect(2, 3, Weights.makeWeight(10));
        snet.getsocialNetWork().connect(2, 4, Weights.makeWeight(6));
        snet.getsocialNetWork().connect(3, 4, Weights.makeWeight(8));
        snet.getsocialNetWork().connect(4, 5, Weights.makeWeight(9));
        LinkedList<Person> aux = snet.isolatedPersons();
        for(Person p : aux){
            System.out.println(p.getName());
        }
        
        
        
    }
    
}
