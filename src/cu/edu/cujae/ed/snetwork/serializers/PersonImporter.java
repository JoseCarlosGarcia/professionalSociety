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
package cu.edu.cujae.ed.snetwork.serializers;

import cu.edu.cujae.ed.snetwork.logic.Person;
import cu.edu.cujae.ed.snetwork.logic.PersonBuilder;
import cu.edu.cujae.ed.snetwork.utils.FileManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Amanda Méndez
 */
public class PersonImporter 
{
    private final Logger logger;
    private final File file;
    
    public PersonImporter(File file)
    {
        this.file = file;
        this.logger = LoggerFactory.getLogger(PersonImporter.class);
    }
    
    public List<Person> deserialize() throws FileNotFoundException
    {
        List<Person> list = new ArrayList<>();
        FileNotFoundException exception = null;
        
        try
        {
            Scanner scanner = new Scanner(file);
            
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                Scanner delimit = new Scanner(line);
                delimit.useDelimiter("\\s*,\\s*");
                
                PersonBuilder person = new PersonBuilder();
                person.withName(delimit.next());
                person.withLastName(delimit.next());
                person.withID(delimit.next());
                person.withCountry(delimit.next());
                person.withProfession(delimit.next());
                person.withPasword(delimit.next());
                
                list.add(person.createPerson());
            }
            scanner.close();
        }
        catch(FileNotFoundException e)
        {
            logger.error("error en la lectura: ", e);
            exception = e;
        }
                
        if (exception != null)
            throw exception;
        
        return list;
    }
}
