/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cu.edu.cujae.ed.snetwork.test;

import cu.edu.cujae.ed.snetwork.logic.ApplicationController;
import cu.edu.cujae.ed.snetwork.logic.Person;
import cu.edu.cujae.ed.snetwork.ui.SidePanel;
import cu.edu.cujae.ed.snetwork.utils.Friendship;
import cu.edu.cujae.graphy.core.Tree;
import cu.edu.cujae.graphy.core.iterators.GraphIterator;
import java.awt.Dimension;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
        Person brian = new Person("Brian Michel", "Morales", "02102768988", "Australia", "Ingeniero Automatico","16_03");
        Person jose = new Person("Jose Carlos", "Garcia Cruz", "01081268460", "Espana", "Analista de requisitos","0102");
        Person nanda = new Person("Ananda", "Morales", "02090566997", "Rusia", "Redactora de informes","0203");
        Person camilo = new Person("Camilo", "Chamorro", "01080725688", "Cuba", "Probador de software","0205");
        Person amanda = new Person("Amanda", "Mendez", "02091868898", "Mexico", "Disenadora","0206");
        Person javi = new Person("Javier", "Marrero", "01040368898","Alemania","Disenador de Software","0208");
        Person amaya = new Person ("Amaya","Delgado", "02111268898","Inglaterra", "Desarrolladora de videojuegos","0209");
        Person dory = new Person ("Barbara", "Teresa", "04031568898", "Grecia", "Psicologa","0503");
        Person leo = new Person ("Leo", "Zayas", "04031529685","Brasil", "Arquitecto","0213");

        snet.addPerson(brian);
        snet.addPerson(jose);
        snet.addPerson(nanda);
        snet.addPerson(camilo);
        snet.addPerson(amanda);
        snet.addPerson(javi);
        snet.addPerson(amaya);
        snet.addPerson(dory);
        snet.addPerson(leo);
        
        /*snet.friendRequest(new Friendship (brian, 1), jose);
        snet.friendRequest(new Friendship (brian, 1), nanda);
        snet.friendRequest(new Friendship (brian, 1), camilo);
        snet.friendRequest(new Friendship (jose, 1), nanda);
        snet.friendRequest(new Friendship (jose, 1), camilo);
        snet.friendRequest(new Friendship (jose, 1), amanda);
        snet.friendRequest(new Friendship (camilo, 1), nanda);
        snet.friendRequest(new Friendship (camilo, 1), amaya);
        snet.friendRequest(new Friendship (amanda, 1), javi);
        snet.friendRequest(new Friendship (javi, 1), dory);
        snet.friendRequest(new Friendship (amaya, 1), dory);
        
        ArrayList<ArrayList<Person>> communities = snet.getCommunities();
        for (ArrayList<Person> a : communities){
            System.out.println("Community");
            for (Person p : a){
                System.out.println(p.getName());
            }
        }*/
        
        
        snet.friendRequest(new Friendship (brian,2), jose);
        snet.friendRequest(new Friendship (brian,2), amanda);
        snet.friendRequest(new Friendship (brian,2), camilo);
        snet.friendRequest(new Friendship (brian,4), javi);
        snet.friendRequest(new Friendship (brian,3), amaya);
        snet.friendRequest(new Friendship (brian,4), dory);
        snet.friendRequest(new Friendship (jose,6), amanda);
        snet.friendRequest(new Friendship (jose,5), nanda);
        snet.friendRequest(new Friendship (camilo,8), amanda);
        GraphIterator<Person> it = snet.getsocialNetWork().iterator(0);
        it.next(0);
        snet.getCollaborationExpansionTree(it);
        Tree <Person> tree = snet.getCollaborationExpansionTree(it);
        
        LinkedList<Person> list = snet.getResearchLeaders();
        for(Person p : list){
            System.out.println(p.getName());
        }
        
        
        /*snet.getsocialNetWork().connect(0, 1, Weights.makeWeight(4));
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
        snet.modifyAmountOfWork(new Friendship(brian, 6), jose);
        System.out.println(brian.getName());
        snet.friendRequest(new Friendship(brian, 6), amaya);
        LinkedList<Person> aux2 = snet.isolatedPersons();
        for(Person p : aux2){
            System.out.println(p.getName());
        }
        snet.getsocialNetWork().connect(1, 6, Weights.makeWeight(9));
        
        System.out.println(snet.getLabelofPerson(dory));

        
        snet.deletePerson(jose);
        
        System.out.println(snet.getLabelofPerson(dory));

       DefaultGeneralTree <Person> tree = snet.getConexionOfAPerson(brian);
       System.out.println(tree.toString());
     
       LinkedList<Person> aux3 = snet.isolatedPersons();
        for(Person p : aux3){
            System.out.println(p.getName());
        }
        */
        
        JFrame frame = new JFrame("pruebita");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 200));
        
        SidePanel panel = new SidePanel();
        frame.add(panel);
        
        GraphIterator<Person> gi = (GraphIterator<Person>) snet.getsocialNetWork().depthFirstSearchIterator(true);
        while (gi.hasNext())
        {
            panel.addPerson(gi.next());
        }
        
        frame.pack();
        frame.setVisible(true);
    }
    
}
