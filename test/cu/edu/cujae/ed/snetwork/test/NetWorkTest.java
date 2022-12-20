/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cu.edu.cujae.ed.snetwork.test;

import cu.edu.cujae.ed.snetwork.logic.ApplicationController;
import cu.edu.cujae.ed.snetwork.logic.Person;
import cu.edu.cujae.ed.snetwork.ui.SidePanel;
import cu.edu.cujae.ed.snetwork.utils.Friendship;
import cu.edu.cujae.ed.snetwork.utils.SaveTXT;
import cu.edu.cujae.graphy.core.Tree;
import cu.edu.cujae.graphy.core.iterators.GraphIterator;
import cu.edu.cujae.graphy.utils.Pair;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    public static void main(String[] args) throws IOException
    {
        ApplicationController snet = ApplicationController.getInstance();
        /*Person brian
                   = new Person("Brian Michel", "Morales", "02102768988", "Australia", "Ingeniero Automatico", "16_03");
        Person jose
                   = new Person("Jose Carlos", "Garcia Cruz", "01081268460", "Espana", "Analista de requisitos", "0102");
        Person nanda = new Person("Ananda", "Morales", "02090566997", "Rusia", "Redactora de informes", "0203");
        Person camilo = new Person("Camilo", "Chamorro", "01080725688", "Cuba", "Probador de software", "0205");
        Person amanda = new Person("Amanda", "Mendez", "02091868898", "Mexico", "Disenadora", "0206");
        Person javi = new Person("Javier", "Marrero", "01040368898", "Alemania", "Disenador de Software", "0208");
        Person amaya = new Person("Amaya", "Delgado", "02111268898", "Inglaterra", "Desarrolladora de videojuegos",
                                  "0209");
        Person dory = new Person("Barbara", "Teresa", "04031568898", "Grecia", "Psicologa", "0503");
        Person leo = new Person("Leo", "Zayas", "04031529685", "Brasil", "Arquitecto", "0213");
        
        snet.addPerson(brian);
        snet.addPerson(jose);
        snet.addPerson(nanda);
        snet.addPerson(camilo);
        snet.addPerson(amanda);
        snet.addPerson(javi);
        snet.addPerson(amaya);
        snet.addPerson(dory);
        snet.addPerson(leo);*/

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
 /*snet.friendRequest(new Friendship(brian, 2), jose);
        snet.friendRequest(new Friendship(brian, 2), amanda);
        snet.friendRequest(new Friendship(brian, 2), camilo);
        snet.friendRequest(new Friendship(brian, 4), javi);
        snet.friendRequest(new Friendship(brian, 3), amaya);
        snet.friendRequest(new Friendship(brian, 4), dory);
        snet.friendRequest(new Friendship(jose, 6), amanda);
        snet.friendRequest(new Friendship(jose, 5), nanda);
        snet.friendRequest(new Friendship(camilo, 8), amanda);*/
        Person brian = new Person("Brian Michel", "Morales", "02102768988", "Australia",
                                  "Ingeniero Automatico",
                                  "16_03");
        Person jose
                   = new Person("José Carlos", "García Cruz", "01081268460", "España",
                                "Analista de requisitos",
                                "0102");
        Person nanda = new Person("Ananda", "Morales", "02090566997", "Rusia", "Redactora de informes",
                                  "0203");
        Person camilo = new Person("Camilo", "Chamorro", "01080725688", "Cuba", "Probador de software",
                                   "0205");
        Person amanda = new Person("Amanda", "Méndez", "02091868898", "Mexico", "Disenadora", "0206");
        Person javi
                   = new Person("Javier", "Marrero", "01040368898", "Alemania", "Disenador de Software",
                                "0208");
        Person amaya = new Person("Amaya", "Delgado", "02111268898", "Inglaterra",
                                  "Desarrolladora de videojuegos",
                                  "0209");
        Person dory = new Person("Barbara", "Teresa", "04031568898", "Grecia", "Psicologa", "0503");
        Person leo = new Person("Leo", "Zayas", "04031529685", "Brasil", "Arquitecto", "0213");
        Person alejandro = new Person("Alejandro", "Bernal Roca", "01081167363", "Angola",
                                      "Licenciado en Física", "7363");
        Person marcos = new Person("Marcos Rafael", "Arencibia Rodríguez", "01062366306", "Cuba",
                                   "Inflador de Software", "6306");
        Person cesar = new Person("César", "Fernández García", "01122666547", "Venezuela",
                                  "Desarrollador", "6547");
        Person david = new Person("David", "García Francecsh", "01081767228", "Brasil",
                                  "Ingeniero Químico", "7228");
        Person hector = new Person("Héctor Ángel", "Gómez Robaina", "01112568886", "Colombia",
                                   "Desarrollador en JavaScript", "8886");
        Person amalia = new Person("Amalia", "Hernández Echeverria", "02101668575", "España",
                                   "Licenciada en química", "8575");
        Person gabriela = new Person("Gabriela", "Hernández Felipe", "02101267230", "Costa Rica",
                                     "Licenciada en Educación", "7230");
        Person melissa = new Person("Melissa", "Suárez Urquiza", "02032267050", "Argentina",
                                    "Licenciada en Biología", "7050");
        Person carlos = new Person("Carlos Daniel", "Robaina Rivero", "01090466805", "Sudáfrica",
                                   "Licenciado en Matemática", "6805");
        Person jordi = new Person("Jordi", "Pinto Pedraza", "01110867763", "Marruecos",
                                  "Probador de software", "7763");
        Person veronica = new Person("Verónica", "Rigual Cabezas", "02022066976", "Egipto",
                                     "Ingeniera Civil", "6976");

        snet.addPerson(brian);
        snet.addPerson(jose);
        snet.addPerson(nanda);
        snet.addPerson(camilo);
        snet.addPerson(amanda);
        snet.addPerson(javi);
        snet.addPerson(amaya);
        snet.addPerson(dory);
        snet.addPerson(leo);
        snet.addPerson(alejandro);
        snet.addPerson(marcos);
        snet.addPerson(cesar);
        snet.addPerson(david);
        snet.addPerson(hector);
        snet.addPerson(amalia);
        snet.addPerson(gabriela);
        snet.addPerson(melissa);
        snet.addPerson(carlos);
        snet.addPerson(jordi);
        snet.addPerson(veronica);

        snet.friendRequest(new Friendship(brian, 10), jose);
        snet.friendRequest(new Friendship(brian, 10), javi);
        snet.friendRequest(new Friendship(brian, 5), dory);
        snet.friendRequest(new Friendship(brian, 3), amanda);

        snet.friendRequest(new Friendship(jose, 20), dory);
        snet.friendRequest(new Friendship(jose, 8), nanda);
        snet.friendRequest(new Friendship(jose, 6), alejandro);
        snet.friendRequest(new Friendship(jose, 17), amaya);
        snet.friendRequest(new Friendship(jose, 17), javi);

        snet.friendRequest(new Friendship(javi, 22), amaya);
        snet.friendRequest(new Friendship(javi, 5), amanda);

        snet.friendRequest(new Friendship(amanda, 14), camilo);
        snet.friendRequest(new Friendship(amanda, 9), carlos);

        snet.friendRequest(new Friendship(dory, 11), nanda);
        snet.friendRequest(new Friendship(dory, 2), camilo);

        snet.friendRequest(new Friendship(amaya, 12), camilo);
        snet.friendRequest(new Friendship(amaya, 5), carlos);
        snet.friendRequest(new Friendship(amaya, 5), amalia);

        snet.friendRequest(new Friendship(carlos, 30), nanda);
        snet.friendRequest(new Friendship(carlos, 15), camilo);

        snet.friendRequest(new Friendship(camilo, 8), nanda);

        snet.friendRequest(new Friendship(leo, 30), amaya);
        snet.friendRequest(new Friendship(leo, 12), carlos);
        snet.friendRequest(new Friendship(leo, 10), nanda);
        snet.friendRequest(new Friendship(leo, 8), marcos);
        snet.friendRequest(new Friendship(leo, 8), hector);
        snet.friendRequest(new Friendship(leo, 8), melissa);

        snet.friendRequest(new Friendship(hector, 20), jose);
        snet.friendRequest(new Friendship(hector, 20), marcos);
        snet.friendRequest(new Friendship(hector, 10), melissa);
        snet.friendRequest(new Friendship(hector, 7), jordi);
        snet.friendRequest(new Friendship(hector, 7), gabriela);

        snet.friendRequest(new Friendship(marcos, 12), melissa);
        snet.friendRequest(new Friendship(marcos, 12), jordi);
        snet.friendRequest(new Friendship(marcos, 20), gabriela);

        snet.friendRequest(new Friendship(gabriela, 15), jordi);
        int label = ApplicationController.getInstance().getLabelofPerson(jose);
        GraphIterator<Person> it = snet.getsocialNetWork().iterator(label);
        it.next(label);
        Tree<Pair<Person, Integer>> treeF = snet.getCollaborationExpansionTree(it);
        Tree<Person> treeeee = snet.getConexionOfAPerson(jose);
        System.out.println(treeeee.toString());

        
        
         

        /*public Tree<Person> getCollaborationExpansionTree(GraphIterator<Person> ite)
    {
        int work = Integer.MAX_VALUE;
        Person root = ite.next(ite.getLabel());
        Tree<Person> tree = new DefaultGeneralTree<>();
        TreeNode<Person> temporalNode = tree.add(null, root);
        TreeNode<Person> parent = temporalNode;
        LinkedList<Pair<Person, Integer>> list = new LinkedList<>();
        for (Edge e : ite.getAllAdjacentEdges())
        {
            @SuppressWarnings("unchecked")
            Pair<Person, Integer> pair = new Pair(e.getFinalNode().get() == root ? e.getStartNode().get() : e.
                getFinalNode().get(),
                                                  e.getWeight().getValue());
            list.add(pair);
        }

        Collections.sort(list, (Pair<Person, Integer> o1, Pair<Person, Integer> o2) -> o2.getLast().compareTo(o1.
            getLast()));

        while (!list.isEmpty())
        {
            Pair<Person, Integer> p = list.poll();
            if (p.getLast() == work)
            {
                temporalNode = tree.add(parent, p.getFirst());
            }
            else if (p.getLast() < work)
            {
                parent = temporalNode;
                temporalNode = tree.add(parent, p.getFirst());
            }
            work = p.getLast();
        }

        return tree;
    }*/

    /**
     * Método para obtener líderes de investigación
     *
     * @return
     */
        /*public LinkedList<Person> getResearchLeaders()
    {
        LinkedList<Person> researchLeaders = new LinkedList<>();
        int depthMax = 0;

        GraphIterator<Person> it = (GraphIterator<Person>) socialNetWork.breadthFirstSearchIterator(true);
        while (it.hasNext())
        {
            Person p = it.next();
            if (!it.getAllAdjacentEdges().isEmpty())
            {
                Tree<Person> tree = getCollaborationExpansionTree(socialNetWork.iterator(it.getLabel()));
                int depthTree = tree.countLevels();

                if (depthTree > depthMax)
                {
                    researchLeaders.clear();
                    researchLeaders.add(p);
                    depthMax = depthTree;
                }
                else if (depthTree == depthMax)
                {
                    researchLeaders.add(p);
                }
            }
        }

        return researchLeaders;
    }*/
        
 /*LinkedList<Person> list = snet.getResearchLeaders();
        for (Person p : list)
        {
            System.out.println(p.getName());
        }*/
        /*System.out.println(snet.getsocialNetWork().isVertexAdjacent(1, 4));
        snet.deleteFriendship(jose, amanda);
        System.out.println(snet.getsocialNetWork().isVertexAdjacent(1, 4));*/
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
 /*JFrame frame = new JFrame("pruebita");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 200));
        
        SidePanel panel = new SidePanel();
        frame.add(panel);
        
        panel.addSelectionListener((object) -> 
        {
            System.out.println("Es la pinga para " + object.getName());
        });
        
        GraphIterator<Person> gi = (GraphIterator<Person>) snet.getsocialNetWork().depthFirstSearchIterator(true);
        while (gi.hasNext())
        {
            panel.addPerson(gi.next());
        }
        
        frame.pack();
        frame.setVisible(true);*/
    }
    
}
