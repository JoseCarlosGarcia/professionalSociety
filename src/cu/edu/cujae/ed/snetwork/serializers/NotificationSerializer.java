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

import cu.edu.cujae.ed.snetwork.logic.PersonBuilder;
import cu.edu.cujae.ed.snetwork.utils.FileManager;
import cu.edu.cujae.ed.snetwork.utils.Notification;
import cu.edu.cujae.ed.snetwork.utils.NotificationType;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Amanda Méndez
 */
public class NotificationSerializer<T>
{
    private final Logger logger;
    private final FileManager fm;
    
    public NotificationSerializer(FileManager fm)
    {
        this.logger = LoggerFactory.getLogger(NotificationSerializer.class);
        this.fm = fm;
    }
    
    public File serialize(List<Notification<? extends T>> list) throws IOException
    {
        String typename;
        
         if(!list.isEmpty())
        {
            typename = list.get(0).getData().getClass().getSimpleName().toLowerCase();
        }
        else
        {
            typename = "empty";
        }
        
        File file = new File(fm.getAppDirectory(), "notifications-" + typename  + ".json");
        FileWriter flWriter = null;
        IOException exception = null;

        try
        {
            flWriter = new FileWriter(file);

            BufferedWriter bfWriter = new BufferedWriter(flWriter);

            for (Notification<? extends T> n : list)
            {
                JSONObject object = new JSONObject(n);
                object.write(bfWriter);
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
    
    public List<Notification<? extends T>> deserializar(File file) throws FileNotFoundException
    {
        List<Notification<? extends T>> list = new ArrayList<>();
        FileNotFoundException exception = null;
        
        try
        {
            Scanner scanner = new Scanner(file);
            
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                JSONObject json = new JSONObject(line);
                
                NotificationType nt = NotificationType.valueOf(json.getString(
                        "type"));
                UUID uuid = UUID.fromString(json.getString("uuid"));
                String message = json.getString("message");
                T data = (T) json.get("data");
                               
                list.add(new Notification(nt,message,data,uuid));
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
