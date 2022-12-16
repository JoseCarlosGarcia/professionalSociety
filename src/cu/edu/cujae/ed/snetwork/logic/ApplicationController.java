/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cu.edu.cujae.ed.snetwork.logic;

import cu.edu.cujae.ed.snetwork.utils.FileManager;
import cu.edu.cujae.ed.snetwork.utils.Friendship;
import cu.edu.cujae.ed.snetwork.utils.Notification;
import cu.edu.cujae.graphy.algorithms.FirstLevelCommunities;
import cu.edu.cujae.graphy.algorithms.IsolatedVertices;
import cu.edu.cujae.graphy.core.Edge;
import cu.edu.cujae.graphy.core.Tree;
import cu.edu.cujae.graphy.core.TreeNode;
import cu.edu.cujae.graphy.core.WeightedGraph;
import cu.edu.cujae.graphy.core.iterators.GraphIterator;
import cu.edu.cujae.graphy.core.serialization.CustomSerializer;
import cu.edu.cujae.graphy.core.serialization.DefaultGraphSerializer;
import cu.edu.cujae.graphy.core.serialization.GraphSerializer;
import cu.edu.cujae.graphy.core.trees.DefaultGeneralTree;
import cu.edu.cujae.graphy.core.utility.GraphBuilders;
import cu.edu.cujae.graphy.core.utility.Weights;
import cu.edu.cujae.graphy.utils.Pair;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Jose
 */
public class ApplicationController
{

    private static ApplicationController network;

    public static ApplicationController getInstance()
    {
        if (network == null)
        {
            network = new ApplicationController();
        }
        return network;
    }
    private WeightedGraph<Person> socialNetWork;
    private int labelCounter;
    private final Map<Person, List<Notification<?>>> pendantNotifications;
    private FileManager fileManager;
    private Map<String, Person> originalPersons;

    private ApplicationController()
    {
        this.pendantNotifications = new HashMap<>();
        this.socialNetWork = GraphBuilders.makeSimpleWeightedGraph(false);
        this.labelCounter = 0;

        this.originalPersons = new HashMap<>(150);
    }

    private CustomSerializer<Object> getPersonSerializer()
    {
        return new CustomSerializer<Object>()
        {
            @Override
            public byte[] serialize(Object object) throws IOException
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] result;
                try ( DataOutputStream dos = new DataOutputStream(baos))
                {
                    dos.writeUTF(((Person) object).getID());
                    result = baos.toByteArray();
                }

                return result;
            }

            @Override
            public Person deserialize(byte[] stream) throws IOException
            {
                String id;

                ByteArrayInputStream bais = new ByteArrayInputStream(stream);
                try ( DataInputStream dis = new DataInputStream(bais))
                {
                    id = dis.readUTF();
                }
                return originalPersons.get(id);
            }
        };
    }

    public void saveGraph() throws FileNotFoundException, IOException
    {
        try ( FileOutputStream stream = new FileOutputStream(fileManager.getGraphSerializationFile()))
        {
            GraphSerializer serializer = new DefaultGraphSerializer();
            CustomSerializer<Object> cs = getPersonSerializer();
            serializer.serialize(stream, socialNetWork, cs);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadGraph() throws FileNotFoundException, IOException, ClassNotFoundException
    {
        FileInputStream stream = new FileInputStream(fileManager.getGraphSerializationFile());
        socialNetWork = (WeightedGraph<Person>) new DefaultGraphSerializer().deserialize(stream, getPersonSerializer());
    }

    public void constructOriginalMap(List<Person> persons)
    {
        for (Person p : persons)
        {
            originalPersons.put(p.getID(), p);
        }
    }

    public Map<Person, List<Notification<?>>> getPendantNotifications()
    {
        return pendantNotifications;
    }

    public WeightedGraph<Person> getsocialNetWork()
    {
        return this.socialNetWork;
    }

    //Añadir una persona al grafo
    public boolean addPerson(Person p)
    {
        boolean result = true;
        if (!socialNetWork.isEmpty())
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
        if (result)
        {
            pendantNotifications.put(p, new LinkedList<>());
        }
        return result ? socialNetWork.add(labelCounter++, p) : result;
    }

    //Obtener las personas que no tienen conexión con nadie más
    public LinkedList<Person> isolatedPersons()
    {
        LinkedList<Integer> aux = new IsolatedVertices<>(socialNetWork).apply().get();
        LinkedList<Person> isolatedPersons = new LinkedList<>();

        for (Integer i : aux)
        {
            GraphIterator<Person> iter = socialNetWork.iterator(i);
            isolatedPersons.add(iter.next(i));
        }
        return isolatedPersons;
    }

    //Añadir notificación a la cola de notificaciones de una persona
    public void addNotification(Pair<Notification<Friendship>, Person> p)
    {
        if (p.getFirst() != null && p.getLast() != null)
        {
            pendantNotifications.get(p.getLast()).add(p.getFirst());
        }
    }

    //Conectar 2 personas y asignar la cantidad de trabajo que comparten
    public boolean friendRequest(Friendship friendship, Person person2) throws IllegalArgumentException
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
                                //GraphIterator<Person> it = (GraphIterator<Person>) socialNetWork.iterator();
                                //it.next(label1);
                                if (!socialNetWork.isVertexAdjacent(label2, label2))
                                {
                                    socialNetWork.connect(label1, label2, Weights.makeWeight(amountOfWork));
                                    result = true;
                                }
                                else
                                {
                                    throw new IllegalArgumentException("Ya son amigos");
                                }

                            }
                            else
                            {
                                throw new IllegalArgumentException("No existe " + person2.getName() + " " + person2.
                                    getLastName() + " en la red social");
                            }
                        }
                        else
                        {
                            throw new IllegalArgumentException("No existe " + person1.getName() + " " + person1.
                                getLastName() + " en la red social");
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

    //Modificar cantidad de trabajo entre dos personas
    public boolean modifyAmountOfWork(Friendship friendship, Person person2) throws IllegalArgumentException
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
                                GraphIterator<Person> iter = socialNetWork.iterator(label1);
                                GraphIterator<Person> iter2 = socialNetWork.iterator(label2);
                                if (iter.isAdjacent(iter2))
                                {
                                    iter.getAdjacentEdge(label2).setWeight(Weights.makeWeight(amountOfWork));
                                    result = true;
                                }
                            }
                            else
                            {
                                throw new IllegalArgumentException("No existe " + person2.getName() + " " + person2.
                                    getLastName() + " en la red social");
                            }
                        }
                        else
                        {
                            throw new IllegalArgumentException("No existe " + person1.getName() + " " + person1.
                                getLastName() + " en la red social");
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

    //Obtener la cantidad de trabajos que comparten dos personas
    public int getAmountOfWork(Person person1, Person person2)
    {
        int result = -1;
        if (person1 != null)
        {
            if (person2 != null)
            {
                if (!person1.equals(person2))
                {
                    int label1 = getLabelofPerson(person1);
                    if (label1 >= 0)
                    {
                        int label2 = getLabelofPerson(person2);
                        if (label2 >= 0)
                        {
                            GraphIterator<Person> iter = socialNetWork.iterator(label1);
                            GraphIterator<Person> iter2 = socialNetWork.iterator(label2);
                            if (iter.isAdjacent(iter2))
                            {
                                result = (Integer) iter.getAdjacentEdge(label2).getWeight().getValue();

                            }
                        }
                        else
                        {
                            throw new IllegalArgumentException("No existe " + person2.getName() + " " + person2.
                                getLastName() + " en la red social");
                        }
                    }
                    else
                    {
                        throw new IllegalArgumentException("No existe " + person1.getName() + " " + person1.
                            getLastName() + " en la red social");
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
        GraphIterator<Person> iter = socialNetWork.iterator(label);
        return iter.next(label);
    }

    //Modificar a una persona del grafo
    public boolean modifyAPerson(Person p, String name, String lastName, String ID, String country, String profession)
    {
        boolean result = false;
        //Las validaciones de estos campos serán hechos en la interfaz
        int label = getLabelofPerson(p);
        if (label >= 0)
        {
            GraphIterator<Person> iter = socialNetWork.iterator(label);
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

    //Eliminar persona
    public boolean deletePerson(Person p)
    {
        int label = getLabelofPerson(p);
        if (label >= 0)
        {
            socialNetWork.removeAt(label);
        }
        return label >= 0;
    }

    //Eliminar amistad
    public boolean deleteFriendship(Person person1, Person person2) throws IllegalArgumentException
    {
        boolean result = false;
        if (person1 != null)
        {
            if (person2 != null)
            {
                if (!person1.equals(person2))
                {
                    int label1 = getLabelofPerson(person1);
                    if (label1 >= 0)
                    {
                        int label2 = getLabelofPerson(person2);
                        if (label2 >= 0)
                        {
                            //GraphIterator<Person> it = socialNetWork.iterator(label1);

                            result = socialNetWork.disconnect(label1, label2);
                            if (result)
                            {
                                System.out.println("AAAAAA");
                            }
                        }
                        else
                        {
                            throw new IllegalArgumentException("No existe " + person2.getName() + " " + person2.
                                getLastName() + " en la red social");
                        }
                    }
                    else
                    {
                        throw new IllegalArgumentException("No existe " + person1.getName() + " " + person1.
                            getLastName() + " en la red social");
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
        }
        else
        {
            throw new IllegalArgumentException("Inserte correctamente la persona");
        }
        return result;

    }

    /**
     * Este método permite obtener una representación de las conexiones de las personas donde la persona en cuestión es
     * la raiz y en el primer nivel están las personas con las que se tiene conexión directa, en el siguiente nivel,
     * están las personas con las que se tiene conexión a través de la persona del nivel anterior asi sucesivamente
     *
     * @param p
     * @return
     */
    public DefaultGeneralTree<Person> getConexionOfAPerson(Person p)
    {
        DefaultGeneralTree<Person> tree = new DefaultGeneralTree<>();
        tree.add(null, p);
        GraphIterator<Person> iter = socialNetWork.iterator(getLabelofPerson(p));
        iter.next(getLabelofPerson(p));
        Collection<Integer> adjacents = iter.getAllAdjacentVertices();
        Set<Integer> visited = new TreeSet<>();
        visited.add(getLabelofPerson(p));
        /*
         Se adicionan a visitados con los que tiene conexión directa garantizando que en el caso
        de que tenga conexion con otra persona, no se conecte,dandole prioridad a la conexion con la
        persona seleccionada
         */
        visited.addAll(adjacents);
        for (Integer i : adjacents)
        {
            TreeNode<Person> padre = tree.add(tree.getRoot(), getPerson(i));
            GraphIterator<Person> iterator = socialNetWork.iterator(i);
            iterator.next(i);
            Collection<Integer> friends = iterator.getAllAdjacentVertices();
            // En conectarP quedarán las personas que estén conectadas a la persona actual y no haya sido añadida al árbol
            LinkedList<Integer> conectarP = new LinkedList<>();
            for (Integer f : friends)
            {
                if (!visited.contains(f))
                {
                    conectarP.add(f);
                }
            }
            conect(tree, visited, conectarP, padre);
        }

        return tree;
    }

    /**
     *
     * @param tree
     * @param visited
     * @param conectarP
     * @param padre
     */
    public void conect(DefaultGeneralTree<Person> tree, Set<Integer> visited, LinkedList<Integer> conectarP,
                       TreeNode<Person> padre)
    {
        if (!conectarP.isEmpty())
        {
            visited.addAll(conectarP);
            for (Integer i : conectarP)
            {
                TreeNode<Person> tn = tree.add(padre, getPerson(i));
                GraphIterator<Person> iterator = socialNetWork.iterator(i);
                iterator.next(i);
                Collection<Integer> friends = iterator.getAllAdjacentVertices();
                LinkedList<Integer> conectarPP = new LinkedList<>();
                for (Integer f : friends)
                {
                    if (!visited.contains(f))
                    {
                        conectarPP.add(f);
                    }
                }
                conect(tree, visited, conectarPP, tn);
            }
        }
    }

    /**
     * Método para obtener relación jerárquica de los amigos
     *
     * @param ite GraphIterator<Person> en la posicion de la persona
     * @return
     */
    public Tree<Person> getCollaborationExpansionTree(GraphIterator<Person> ite)
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
    }

    /**
     * Método para obtener líderes de investigación
     *
     * @return
     */
    public LinkedList<Person> getResearchLeaders()
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
    }

    /**
     * Método para obtener comunidades
     *
     * @return
     */
    public ArrayList<ArrayList<Person>> getCommunities()
    {
        ArrayList<ArrayList<Person>> comunitiesPerson = new ArrayList<>();
        @SuppressWarnings("unchecked")
        ArrayList<Collection<Integer>> comunitiesInt = new FirstLevelCommunities<>(
            socialNetWork).apply().get();
        for (Collection<Integer> c : comunitiesInt)
        {
            ArrayList<Person> a = new ArrayList<>();
            for (Integer i : c)
            {
                a.add(getPerson(i));
            }
            comunitiesPerson.add(a);
        }
        return comunitiesPerson;
    }

    public ArrayList<Person> getPersons()
    {
        ArrayList<Person> persons = new ArrayList<>();
        GraphIterator<Person> it = (GraphIterator<Person>) socialNetWork.breadthFirstSearchIterator(true);
        while (it.hasNext())
        {
            Person p = it.next();
            persons.add(p);
        }
        return persons;
    }
//Listado

    /**
     * @return the fileManager
     */
    public FileManager getFileManager()
    {
        return fileManager;
    }

    /**
     * @param fileManager the fileManager to set
     */
    public void setFileManager(FileManager fileManager)
    {
        this.fileManager = fileManager;
    }
}
