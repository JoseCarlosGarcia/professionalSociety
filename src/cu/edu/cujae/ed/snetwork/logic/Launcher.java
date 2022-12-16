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
package cu.edu.cujae.ed.snetwork.logic;

import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import cu.edu.cujae.ed.snetwork.serializers.PersonExporter;
import cu.edu.cujae.ed.snetwork.serializers.PersonImporter;
import cu.edu.cujae.ed.snetwork.ui.MainWindow;
import cu.edu.cujae.ed.snetwork.utils.FileManager;
import cu.edu.cujae.ed.snetwork.utils.Friendship;
import cu.edu.cujae.graphy.core.iterators.GraphIterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jose
 */
public class Launcher
{

    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(new FlatLightFlatIJTheme());
        }
        catch (UnsupportedLookAndFeelException ex)
        {
            LOGGER.error("Excepcion", ex);
        }

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    FileManager manager = new FileManager();
                    ApplicationController snet = ApplicationController.getInstance();
                    snet.setFileManager(manager);

                    if (manager.existsFile(FileManager.PEOPLE_TXT))
                    {
                        PersonImporter imp = new PersonImporter(manager.getFile(FileManager.PEOPLE_TXT));
                        List<Person> persons = imp.deserialize();
                        snet.constructOriginalMap(persons);

                        for (Person p : persons)
                        {
                            String id = p.getID();
                            manager.addProfile(id);

                            try
                            {
                                p.setPhoto(manager.getPictureInProfile(id, FileManager.PPIC_NAME));
                            }
                            catch (IOException ex)
                            {
                                LOGGER.error("No pudo cargar la foto. {}: {}", ex.getClass().getName(), ex.
                                             getLocalizedMessage());
                            }
                        }
                        try
                        {
                            snet.loadGraph();
                        }
                        catch (FileNotFoundException ex)
                        {
                            java.util.logging.Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        catch (ClassNotFoundException ex)
                        {
                            java.util.logging.Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "¿Primera vez que ejecuta la app?", "Inicialización",
                                                      JOptionPane.WARNING_MESSAGE);

                        //
                        //
                        //
                        Person brian = new Person("Brian Michel", "Morales", "02102768988", "Australia",
                                                  "Ingeniero Automatico",
                                                  "16_03");
                        Person jose
                                   = new Person("José Carlos", "García Cruz", "01081268460", "España",
                                                "Analista de requisitos",
                                                "0102");
                        Person nanda = new Person("Ananda", "Morales", "02090566997", "Rusia", "Redactora de informes",
                                                  "0203");
                        Person camilo = new Person("Camilo", "Chamorro", "01080725688", "Cuba", "Probador de software",
                                                   "0205");
                        Person amanda = new Person("Amanda", "Méndez", "02091868898", "Mexico", "Disenadora", "0206");
                        Person javi
                                   = new Person("Javier", "Marrero", "01040368898", "Alemania", "Disenador de Software",
                                                "0208");
                        Person amaya = new Person("Amaya", "Delgado", "02111268898", "Inglaterra",
                                                  "Desarrolladora de videojuegos",
                                                  "0209");
                        Person dory = new Person("Barbara", "Teresa", "04031568898", "Grecia", "Psicologa", "0503");
                        Person leo = new Person("Leo", "Zayas", "04031529685", "Brasil", "Arquitecto", "0213");
                        Person alejandro = new Person("Alejandro", "Bernal Roca", "01081167363", "Angola",
                                                      "Licenciado en Física", "7363");
                        Person marcos = new Person("Marcos Rafael", "Arencibia Rodríguez", "01062366306", "Cuba",
                                                   "Inflador de Software", "6306");
                        Person cesar = new Person("César", "Fernández García", "01122666547", "Venezuela",
                                                  "Desarrollador", "6547");
                        Person david = new Person("David", "García Francecsh", "01081767228", "Brasil",
                                                  "Ingeniero Químico", "7228");
                        Person hector = new Person("Héctor Ángel", "Gómez Robaina", "01112568886", "Colombia",
                                                   "Desarrollador en JavaScript", "8886");
                        Person amalia = new Person("Amalia", "Hernández Echeverria", "02101668575", "España",
                                                   "Licenciada en química", "8575");
                        Person gabriela = new Person("Gabriela", "Hernández Felipe", "02101267230", "Costa Rica",
                                                     "Licenciada en Educación", "7230");
                        Person melissa = new Person("Melissa", "Suárez Urquiza", "02032267050", "Argentina",
                                                    "Licenciada en Biología", "7050");
                        Person carlos = new Person("Carlos Daniel", "Robaina Rivero", "01090466805", "Sudáfrica",
                                                   "Licenciado en Matemática", "6805");
                        Person jordi = new Person("Jordi", "Pinto Pedraza", "01110867763", "Marruecos",
                                                  "Probador de software", "7763");
                        Person veronica = new Person("Verónica", "Rigual Cabezas", "02022066976", "Egipto",
                                                     "Ingeniera Civil", "6976");

   
                        
                        snet.addPerson(brian);
                        snet.addPerson(jose);
                        snet.addPerson(nanda);
                        snet.addPerson(camilo);
                        snet.addPerson(amanda);
                        snet.addPerson(javi);
                        snet.addPerson(amaya);
                        snet.addPerson(dory);
                        snet.addPerson(leo);
                        snet.addPerson(alejandro);
                        snet.addPerson(marcos);
                        snet.addPerson(cesar);
                        snet.addPerson(david);
                        snet.addPerson(hector);
                        snet.addPerson(amalia);
                        snet.addPerson(gabriela);
                        snet.addPerson(melissa);
                        snet.addPerson(carlos);
                        snet.addPerson(jordi);
                        snet.addPerson(veronica);

                        snet.friendRequest(new Friendship(brian, 10), jose);
                        snet.friendRequest(new Friendship(brian, 10), javi);
                        snet.friendRequest(new Friendship(brian, 5), dory);
                        snet.friendRequest(new Friendship(brian, 3), amanda);

                        snet.friendRequest(new Friendship(jose, 20), dory);
                        snet.friendRequest(new Friendship(jose, 8), nanda);
                        snet.friendRequest(new Friendship(jose, 6), alejandro);
                        snet.friendRequest(new Friendship(jose, 17), amaya);
                        snet.friendRequest(new Friendship(jose, 17), javi);

                        snet.friendRequest(new Friendship(javi, 22), amaya);
                        snet.friendRequest(new Friendship(javi, 5), amanda);

                        snet.friendRequest(new Friendship(amanda, 14), camilo);
                        snet.friendRequest(new Friendship(amanda, 9), carlos);

                        snet.friendRequest(new Friendship(dory, 11), nanda);
                        snet.friendRequest(new Friendship(dory, 2), camilo);

                        snet.friendRequest(new Friendship(amaya, 12), camilo);
                        snet.friendRequest(new Friendship(amaya, 5), carlos);
                        snet.friendRequest(new Friendship(amaya, 5), amalia);

                        snet.friendRequest(new Friendship(carlos, 30), nanda);
                        snet.friendRequest(new Friendship(carlos, 15), camilo);

                        snet.friendRequest(new Friendship(camilo, 8), nanda);

                        snet.friendRequest(new Friendship(leo, 30), amaya);
                        snet.friendRequest(new Friendship(leo, 12), carlos);
                        snet.friendRequest(new Friendship(leo, 10), nanda);
                        snet.friendRequest(new Friendship(leo, 8), marcos);
                        snet.friendRequest(new Friendship(leo, 8), hector);
                        snet.friendRequest(new Friendship(leo, 8), melissa);

                        snet.friendRequest(new Friendship(hector, 20), jose);
                        snet.friendRequest(new Friendship(hector, 20), marcos);
                        snet.friendRequest(new Friendship(hector, 10), melissa);
                        snet.friendRequest(new Friendship(hector, 7), jordi);
                        snet.friendRequest(new Friendship(hector, 7), gabriela);

                        snet.friendRequest(new Friendship(marcos, 12), melissa);
                        snet.friendRequest(new Friendship(marcos, 12), jordi);
                        snet.friendRequest(new Friendship(marcos, 20), gabriela);

                        snet.friendRequest(new Friendship(gabriela, 15), jordi);



                        /*snet.friendRequest(new Friendship(brian, 2), jose);
                        snet.friendRequest(new Friendship(brian, 2), amanda);
                        snet.friendRequest(new Friendship(brian, 2), camilo);
                        snet.friendRequest(new Friendship(brian, 4), javi);
                        snet.friendRequest(new Friendship(brian, 3), amaya);
                        snet.friendRequest(new Friendship(brian, 4), dory);
                        snet.friendRequest(new Friendship(jose, 6), amanda);
                        snet.friendRequest(new Friendship(jose, 5), nanda);
                        snet.friendRequest(new Friendship(camilo, 8), amanda);*/

                        ArrayList<Person> people = snet.getPersons();
                        for (Person p : people)
                        {
                            File ppicDir = new File(manager.getPicsDirectory(), p.getID());
                            ppicDir.mkdir();
                        }

                        PersonExporter exp = new PersonExporter(people, manager);
                        exp.serialize();

                        snet.saveGraph();
                    }
                    //
                    //
                    //
                    MainWindow mw = new MainWindow(manager);
                    mw.setVisible(true);
                    GraphIterator<Person> gi = (GraphIterator<Person>) snet.getsocialNetWork().
                        depthFirstSearchIterator(true);
                    while (gi.hasNext())
                    {
                        mw.getSidePanel().addPerson(gi.next());
                    }

                }
                catch (IOException ex)
                {
                    LOGGER.error("Excepcion", ex);
                    JOptionPane.showMessageDialog(null, ex, ex.toString(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
