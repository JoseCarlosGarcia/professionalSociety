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

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author Jose
 */
public final class FileManager
{
    public static final String APP_DIRECTORY = "snet/data";
    public static final String PPIC_NAME = "ppic.jpg";
    public static final String PEOPLE_TXT = "people.txt";
    public static final String ICON_DIRECTORY = "snet/data/icon.png";

    private final File appDirectory;
    private final File picsDirectory;
    private final Map<String, File> profilePics;
    
    public FileManager() throws IOException
    {
        appDirectory = new File(APP_DIRECTORY);
        if (!appDirectory.isDirectory() || !appDirectory.exists())
        {
            if (appDirectory.mkdirs() == false)
            {
                throw new IOException("El directorio de datos no existe.");
            }
        }
        
        picsDirectory = new File(getAppDirectory(), "pics");
        if (picsDirectory.exists() == false) picsDirectory.mkdir();
        
        profilePics = new HashMap<>(128);
    }
    
    public boolean addProfile(String profileId) throws IOException
    {
        if (profilePics.containsKey(profileId))
        {
            throw new IOException("Ya existe este perfil");
        }
        
        File directory = new File(getPicsDirectory(), profileId);
        
        profilePics.put(profileId, directory);
        return directory.mkdir();
    }

    public boolean existsFile(String file)
    {
        return new File(appDirectory, file).exists();
    }

    public File getFile(String name)
    {
        return new File(appDirectory, name);
    }

    public File getProfileDir(String id)
    {
        return profilePics.get(id);
    }
    
    public Image getPictureInProfile(String id, String photo) throws IOException
    {
        File image = new File(profilePics.get(id), photo);
        if (image.exists() == false)
            throw new IOException("La foto buscada no existe en el sistema de archivos: " + image.getAbsolutePath().
                toString());
        
        return ImageIO.read(image);
    }

    /**
     * @return the appDirectory
     */
    public File getAppDirectory()
    {
        return appDirectory;
    }

    /**
     * @return the picsDirectory
     */
    public File getPicsDirectory()
    {
        return picsDirectory;
    }

    public File getGraphSerializationFile()
    {
        return new File(appDirectory, "database.db");
    }

    public void deletePicsDirectory(String ID) throws IOException
    {
        File file = new File(getProfileDir(ID), "ppic.jpg");
        file.delete();
        profilePics.remove(ID);
    }
}
