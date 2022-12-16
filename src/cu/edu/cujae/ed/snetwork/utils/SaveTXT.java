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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
}
