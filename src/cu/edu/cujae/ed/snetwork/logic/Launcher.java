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
import cu.edu.cujae.ed.snetwork.ui.MainWindow;
import cu.edu.cujae.ed.snetwork.utils.FileManager;
import cu.edu.cujae.ed.snetwork.utils.Friendship;
import cu.edu.cujae.ed.snetwork.utils.Notification;
import cu.edu.cujae.ed.snetwork.utils.NotificationType;
import cu.edu.cujae.graphy.core.iterators.GraphIterator;
import cu.edu.cujae.graphy.utils.Pair;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Jose
 */
public class Launcher
{

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
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    FileManager manager = new FileManager();
                    //
                    //
                    //
                    ApplicationController snet = ApplicationController.getInstance();
                    Person brian = new Person("Brian Michel", "Morales", "02102768988", "Australia",
                                              "Ingeniero Automatico",
                                              "16_03");
                    Person jose
                               = new Person("Jose Carlos", "Garcia Cruz", "01081268460", "Espana",
                                            "Analista de requisitos",
                                            "0102");
                    Person nanda = new Person("Ananda", "Morales", "02090566997", "Rusia", "Redactora de informes",
                                              "0203");
                    Person camilo = new Person("Camilo", "Chamorro", "01080725688", "Cuba", "Probador de software",
                                               "0205");
                    Person amanda = new Person("Amanda", "Mendez", "02091868898", "Mexico", "Disenadora", "0206");
                    Person javi
                               = new Person("Javier", "Marrero", "01040368898", "Alemania", "Disenador de Software",
                                            "0208");
                    Person amaya = new Person("Amaya", "Delgado", "02111268898", "Inglaterra",
                                              "Desarrolladora de videojuegos",
                                              "0209");
                    Person dory = new Person("Barbara", "Teresa", "04031568898", "Grecia", "Psicologa", "0503");
                    Person leo = new Person("Leo", "Zayas", "04031529685", "Brasil", "Arquitecto", "0213");
                    snet.addPerson(brian);
                    snet.addPerson(jose);
                    snet.addPerson(nanda);
                    snet.addPerson(camilo);
                    snet.addPerson(amanda);
                    snet.addPerson(javi);
                    snet.addPerson(amaya);
                    snet.addPerson(dory);
                    snet.addPerson(leo);
                    snet.friendRequest(new Friendship(brian, 2), jose);
                    snet.friendRequest(new Friendship(brian, 2), amanda);
                    snet.friendRequest(new Friendship(brian, 2), camilo);
                    snet.friendRequest(new Friendship(brian, 4), javi);
                    snet.friendRequest(new Friendship(brian, 3), amaya);
                    snet.friendRequest(new Friendship(brian, 4), dory);
                    snet.friendRequest(new Friendship(jose, 6), amanda);
                    snet.friendRequest(new Friendship(jose, 5), nanda);
                    snet.friendRequest(new Friendship(camilo, 8), amanda);
                    Pair<Notification<Friendship>, Person> pair = new Pair<>(new Notification<>(
                        NotificationType.FRINDSHIP_REQUEST, null, new Friendship(jose, 20), UUID.randomUUID()), javi);
                    snet.addNotification(pair);

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
                    Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex, ex.toString(), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
}
