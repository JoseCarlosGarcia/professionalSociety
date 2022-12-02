/*
 * Copyright (C) 2022 Amanda Méndez
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
import cu.edu.cujae.graphy.core.Node;
import cu.edu.cujae.graphy.core.Tree;
import cu.edu.cujae.graphy.core.TreeNode;
import cu.edu.cujae.graphy.core.iterators.GraphIterator;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Amanda Méndez
 */
public class TreeUtils
{
    private static void walk(TreeNode<Person> node, DefaultMutableTreeNode parent)
    {
        DefaultMutableTreeNode current = new DefaultMutableTreeNode(node.get().getName() +node.get().getLastName());
        parent.add(current);
        
        if (node.hasChildren())
        {
            for (TreeNode<Person> child : node.getChildren())
            {
                walk(child, current);
            }
        }
    }
    
    public static DefaultMutableTreeNode makeTree(Tree<Person> tree)
    {
        Person root = tree.getRoot().get();
        DefaultMutableTreeNode person = new DefaultMutableTreeNode(root.getName()+root.getLastName());
        
        walk(tree.getRoot(), person);
        
        return person;
    }
    
    public static void makeCommunities(List<List<Person>> list)
    {
        int i = 1;
        DefaultMutableTreeNode communities = new DefaultMutableTreeNode("Comunidades");
        
        for(List<Person> l : list)
        {
            DefaultMutableTreeNode community = new DefaultMutableTreeNode("Comunidad" + i);
            communities.add(community);
            i++;
            
            for(Person p : l)
            {
                DefaultMutableTreeNode person = new DefaultMutableTreeNode(p.getName()+p.getLastName());
                community.add(person);
                
            }
        }
    }
}
