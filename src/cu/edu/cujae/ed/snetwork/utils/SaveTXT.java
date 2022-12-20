/*
 * Copyright (C) 2022 Jose
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cu.edu.cujae.ed.snetwork.utils;

import cu.edu.cujae.ed.snetwork.logic.Person;
import cu.edu.cujae.graphy.core.Tree;
import cu.edu.cujae.graphy.core.TreeNode;
import cu.edu.cujae.graphy.core.trees.DefaultGeneralTree;
import cu.edu.cujae.graphy.utils.Pair;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Jose
 */
public class SaveTXT
{

    public static void saveIsolatedVertices(List<Person> list, FileManager filemanager) throws IOException
    {
        File file = new File(filemanager.getAppDirectory(), "islas.txt");
        if (file.exists() == false)
        {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);

        try ( BufferedWriter bfWriter = new BufferedWriter(fileWriter))
        {
            bfWriter.write("Personas aisladas:\n");
            for (Person p : list)
            {
                bfWriter.write(p.getName() + " " + p.getLastName() + " ID: " + p.
                    getID() + " País: " + p.getCountry() + " Profesión: " + p.getProfession()
                               + "\n");

            }
            bfWriter.close();
        }
    }

    public static void saveCommunities(ArrayList<ArrayList<Person>> communities, FileManager filemanager) throws
        IOException
    {
        File file = new File(filemanager.getAppDirectory(), "Comunidades.txt");
        if (file.exists() == false)
        {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bfWriter = new BufferedWriter(fileWriter);
        System.out.println(communities.size());
        int i = 1;
        for (ArrayList<Person> ar : communities)
        {
            System.out.println(i);
            bfWriter.write("Comunidad: " + i + "\n");
            for (Person p : ar)
            {
                bfWriter.write(p.getName() + " " + p.getLastName() + " ID: " + p.
                    getID() + " País: " + p.getCountry() + " Profesión: " + p.getProfession()
                               + "\n");
            }
            bfWriter.write("\n");
            i++;
        }
        bfWriter.close();
    }

    public static void saveResearchLeaders(List<Person> list, FileManager filemanager) throws IOException
    {
        File file = new File(filemanager.getAppDirectory(), "lideres.txt");
        if (file.exists() == false)
        {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);

        try ( BufferedWriter bfWriter = new BufferedWriter(fileWriter))
        {
            bfWriter.write("Líderes de investigación:\n");
            for (Person p : list)
            {
                bfWriter.write(p.getName() + " " + p.getLastName() + " ID: " + p.
                    getID() + " País: " + p.getCountry() + " Profesión: " + p.getProfession()
                               + "\n");

            }
            bfWriter.close();
        }
    }

    public static void saveTree(Tree<Pair<Person, Integer>> tree, FileManager filemanager) throws
        IOException
    {
        Pair<Person, Integer> root = tree.getRoot().get();
        TreeNode<Pair<Person, Integer>> rootTree = tree.getRoot();
        File file = new File(filemanager.getAppDirectory(), root.getFirst().getName() + "-Amigos.txt");
        if (file.exists() == false)
        {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        try ( BufferedWriter bfWriter = new BufferedWriter(fileWriter))
        {
            bfWriter.write("Relación jerárquica de los amigos de " + root.getFirst().getName() + ":\n");
            Object[] coll = tree.toArray();
            Integer inte = Integer.MAX_VALUE;
            for (int i = 1; i < coll.length; i++)
            {
                @SuppressWarnings("unchecked")
                Pair<Person, Integer> p = (Pair<Person, Integer>) coll[i];
                if (p.getLast() < inte)
                {
                    //String line = p.getFirst().getName() + " " + p.getFirst().getLastName() + " " + p.getLast().toString();
                    bfWriter.write("\n");
                }
                bfWriter.write(p.getFirst().getName() + " " + p.getFirst().getLastName() + " " + p.getLast() + "   ");
                inte = p.getLast();
            }
            bfWriter.close();
        }
    }
    
    public static void saveTreeConexion(Tree<Person> tree, FileManager filemanager) throws IOException
    {
        Person p = tree.getRoot().get();
        File file = new File(filemanager.getAppDirectory(), p.getName() + "-Conexiones.txt");
        if (file.exists() == false)
        {
            file.createNewFile();
        }
        
        FileWriter fileWriter = new FileWriter(file);
        
        try ( BufferedWriter bfWriter = new BufferedWriter(fileWriter))
        {
            bfWriter.write("Conexión del grafo a partir de" + p.getLastName() + "\n");
            bfWriter.write(tree.toString());
            bfWriter.close();
            
        }
    }
    
    public static void saveTreeG(Tree<Person> tree) throws IOException
    {
        //File file = new File(filemanager.getAppDirectory(), "-Amigos.txt");
        /*if (file.exists() == false)
        {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);*/
 /*try ( BufferedWriter bfWriter = new BufferedWriter(fileWriter))
        {*/
            TreeNode<Person> node = tree.getRoot();
            String result = node.get().getName() + "\n";
            Collection<TreeNode<Person>> coll = node.getChildren();
            //bfWriter.write(node.get().getName() + "\n");
            for (TreeNode<Person> tn : coll)
            {
                imprimir(tn);
        }
        /*}*/
        System.out.println();
    }
    
    public static String imprimir(TreeNode<Person> tn)
    {
        Person p = tn.get();
        String result = "  -" + p.getName() + "\n  ";
        for (TreeNode<Person> treenode : tn.getChildren())
        {
            result += imprimir(tn);
        }
        return result;
    }
}
