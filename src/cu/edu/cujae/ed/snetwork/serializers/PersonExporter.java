/*
 * Copyright (C) 2022 CUJAE
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
package cu.edu.cujae.ed.snetwork.serializers;

import cu.edu.cujae.ed.snetwork.logic.Person;
import cu.edu.cujae.ed.snetwork.utils.FileManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Amanda
 */
public class PersonExporter
{

    private final Logger logger;
    private final List<Person> list;
    private final FileManager fm;

    public PersonExporter(List<Person> list, FileManager fm)
    {
        this.list = list;
        this.logger = LoggerFactory.getLogger(PersonExporter.class);
        this.fm = fm;
    }

    public File serialize() throws IOException
    {
        File file = new File(fm.getAppDirectory(), "people.txt");
        FileWriter flWriter = null;
        IOException exception = null;

        try
        {
            flWriter = new FileWriter(file);

            BufferedWriter bfWriter = new BufferedWriter(flWriter);

            for (Person p : list)
            {
                bfWriter.write(p.getName() + "," + p.getLastName() + "," + p.
                        getID() + "," + p.getCountry() + "," + p.getProfession()
                        + "," + p.getPassword() + "\n");

            }
            bfWriter.close();
            
        } catch(IOException e)
        {
            logger.error("error en la escritura: ", e);
            exception = e;
        }
        finally 
        {
            if(flWriter != null)
            {
                try 
                {
                    flWriter.close();
                }
                catch(IOException e)
                {
                    logger.error("error al cerrar el writer.", e);
                    exception = e;
                }
            }
        }
        if (exception != null)
            throw exception;
        
        return file;      
    }
}
