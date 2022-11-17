/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cu.edu.cujae.ed.snetwork.logic;

import cu.edu.cujae.ed.snetwork.utils.Friendship;
import cu.edu.cujae.ed.snetwork.utils.Notification;
import cu.edu.cujae.graphy.algorithms.IsolatedVertices;
import cu.edu.cujae.graphy.core.WeightedGraph;
import cu.edu.cujae.graphy.core.iterators.GraphIterator;
import cu.edu.cujae.graphy.core.utility.GraphBuilders;
import cu.edu.cujae.graphy.core.utility.Weights;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jose
 */
public class ApplicationController
{

    private static ApplicationController network;
    private final WeightedGraph<Person> socialNetWork;
    private int labelCounter;
    private final Map<Person, List<Notification<?>>> pendantNotifications;

    public ApplicationController()
    {
        this.pendantNotifications = new HashMap<>();
        this.socialNetWork = GraphBuilders.makeSimpleWeightedGraph(false);
        this.labelCounter = 0;
    }

    public static ApplicationController getInstance()
    {
        if (network == null)
        {
            network = new ApplicationController();
        }
        return network;
    }

    public WeightedGraph<Person> getsocialNetWork()
    {
        return this.socialNetWork;
    }

    public boolean addPerson(Person p)
    {
        boolean result = true;
        if (socialNetWork.size() > 0)
        {
            GraphIterator<Person> iter = (GraphIterator<Person>) socialNetWork.depthFirstSearchIterator(true);
            while (iter.hasNext() && result)
            {
                Person person = iter.next();
                if (person.equals(p))
                {
                    result = false;
                }
            }
        }
        return result ? socialNetWork.add(labelCounter++, p) : result;
    }
    
    //Obtener las personas que no tienen conexión con nadie más
    public LinkedList<Person> isolatedPersons()
    {
        LinkedList<Integer> aux = (LinkedList<Integer>) new IsolatedVertices(socialNetWork).apply().get();
        LinkedList<Person> isolatedPersons = new LinkedList<>();

        for (Integer i : aux)
        {
            GraphIterator<Person> iter = (GraphIterator<Person>) socialNetWork.iterator(i);
            isolatedPersons.add(iter.next(i));
        }
        return isolatedPersons;
    }
    
    //Conectar 2 personas y asignar la cantidad de trabajo que comparten
    public boolean friendRequest(Friendship friendship, Person person2)
    {
        Person person1 = friendship.getPerson();
        int amountOfWork = friendship.getAmountOfWork();
        boolean result = false;
        if (person1 != null)
        {
            if (person2 != null)
            {
                if (!person1.equals(person2))
                {
                    if (amountOfWork > 0)
                    {
                        int label1 = getLabelofPerson(person1);
                        if (label1 >= 0)
                        {
                            int label2 = getLabelofPerson(person2);
                            if (label2 >= 0)
                            { 
                                socialNetWork.connect(label1, label2, Weights.makeWeight(amountOfWork));
                                result = true;
                            }
                            else
                            {
                                throw new IllegalArgumentException("No existe " + person2.getName() + " " + person2.getLastName() + " en la red social");
                            }
                        }
                        else
                        {
                            throw new IllegalArgumentException("No existe " + person1.getName() + " " + person1.getLastName() + " en la red social");
                        }
                    }
                    else
                    {
                        throw new IllegalArgumentException("El peso debe ser positivo");
                    }
                }
            }
            else
            {
                throw new IllegalArgumentException("Inserte correctamente la persona");
            }
        }
        else
        {
            throw new IllegalArgumentException("Inserte correctamente la persona");
        }
        return result;
    }

    //Obtener el label de la persona en el grafo
    public int getLabelofPerson(Person p)
    {
        int label = -1;
        boolean found = false;
        GraphIterator<Person> iter = (GraphIterator<Person>) socialNetWork.depthFirstSearchIterator(true);
        while (iter.hasNext() && !found)
        {
            Person person = iter.next();
            if (person.equals(p))
            {
                found = true;
                label = iter.getLabel();
            }
        }
        return label;
    }

    //Obtener persona a partir del label
    public Person getPerson(int label)
    {
        GraphIterator<Person> iter = (GraphIterator<Person>) socialNetWork.iterator(label);
        return iter.next(label);
    }

    //Modificar a una persona del grafo
    public boolean modifyAPerson (Person p, String name, String lastName,String ID, String country, String profession){
        boolean result = false;
        //Las validaciones de estos campos serán hechos en la interfaz
        int label = getLabelofPerson(p);
        if(label>=0){
            GraphIterator<Person> iter = (GraphIterator<Person>) socialNetWork.iterator(label);
            Person person = iter.next(label);
            person.setName(name);
            person.setLastName(lastName);
            person.setID(ID);
            person.setCountry(country);
            person.setProfession(profession);
            result = true;
        }
        return result;
    }
    
    //TODO: Eliminar persona JOSE
    //FIXME: Modifiar cantidad de trabajo entre dos personas JOSE
    //Eliminar amistad JOSE
    //Método para obtener conexiones entre las personas JOSE
    
    //Listado
    //Método para obtener comunidades NANDA
    //Método para obtener relación jerárquica de los amigos AMANDA
    //Método para obtener líderes de investigación AMANDA

}
