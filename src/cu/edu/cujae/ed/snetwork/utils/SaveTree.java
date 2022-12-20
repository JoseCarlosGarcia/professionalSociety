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
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.out;
import java.util.Collection;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Jose
 */
public class SaveTree
{
    public static void saveTreeG(DefaultTreeModel modelC, FileManager filemanager) throws IOException
    {
        Object root = modelC.getRoot();
        File file = new File(filemanager.getAppDirectory(), "-Amigos.txt");
        if (file.exists() == false)
        {
            file.createNewFile();
        }

        int cant = modelC.getChildCount(root);
        while (cant > 0)
        {

        }
        
        FileWriter fileWriter = new FileWriter(file);
        try ( BufferedWriter bfWriter = new BufferedWriter(fileWriter))
        {

            bfWriter.close();
        }

    }

    public void escribir(DefaultTreeModel modelC, Object node, int cant)
    {

    }
}
