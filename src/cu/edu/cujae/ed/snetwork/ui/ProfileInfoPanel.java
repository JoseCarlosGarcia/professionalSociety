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
package cu.edu.cujae.ed.snetwork.ui;

import cu.edu.cujae.ed.snetwork.logic.ApplicationController;
import cu.edu.cujae.ed.snetwork.logic.Person;
import cu.edu.cujae.ed.snetwork.utils.FileManager;
import cu.edu.cujae.ed.snetwork.utils.Notification;
import cu.edu.cujae.ed.snetwork.utils.NotificationType;
import cu.edu.cujae.ed.snetwork.utils.TreeUtils;
import cu.edu.cujae.graphy.core.Tree;
import cu.edu.cujae.graphy.core.iterators.GraphIterator;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import static javax.swing.JComponent.TOOL_TIP_TEXT_KEY;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;
import org.flexdock.docking.DockingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jose
 */
@SuppressWarnings("serial")
public class ProfileInfoPanel extends JPanel
{

    private SidePanel sp;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileInfoPanel.class);

    private Person person;
    private final MainWindow mw;

    /**
     * Creates new form ProfileInfoPanel
     *
     * @param p
     */
    public ProfileInfoPanel(Person p, MainWindow mw)
    {
        initComponents();
        this.sp = null;
        this.mw = mw;
        this.person = p;

        nameText.setText(p.getName());
        apellidosText.setText(p.getLastName());
        paisText.setText(p.getCountry());
        profesionTextField.setText(p.getProfession());

        if (p.getPhoto() != null)
        {
            Image pic = p.getPhoto().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
            jPicLabel.setIcon(new ImageIcon(pic));
        }

        GraphIterator<Person> gI = ApplicationController.getInstance().getsocialNetWork().
            iterator(ApplicationController.getInstance().getLabelofPerson(p));
        gI.next(ApplicationController.getInstance().getLabelofPerson(p));

        Tree<Person> treeC = ApplicationController.getInstance().getConexionOfAPerson(p);

        Tree<Person> treeA = ApplicationController.getInstance().getCollaborationExpansionTree(gI);

        DefaultTreeModel modelC = (DefaultTreeModel) jTreeC.getModel();
        DefaultTreeModel modelA = (DefaultTreeModel) jTreeA.getModel();

        modelC.setRoot(TreeUtils.makeTree(treeC));
        modelA.setRoot(TreeUtils.makeTree(treeA));

        JMenuItem m1 = new JMenuItem("Modificar Perfil");
        JMenuItem m8 = new JMenuItem("Cambiar contraseña");
        JMenuItem m2 = new JMenuItem("Eliminar Perfil");
        JMenuItem m3 = new JMenuItem("Cerrar");
        JMenuItem m4 = new JMenuItem("Enviar solicitud");
        JMenuItem m6 = new JMenuItem("Cambiar foto del perfil");
        JMenuItem m7 = new JMenuItem("Amigos");

        jPopupMenu1.add(m1);
        jPopupMenu1.add(m8);
        jPopupMenu1.add(m6);
        jPopupMenu1.add(m7);
        jPopupMenu1.add(m4);
        jPopupMenu1.add(m2);
        jPopupMenu1.add(m3);

        m1.addActionListener((ActionEvent e) ->        {
            LOGGER.info("Nombre: {}", person.getName());
            apellidosText.setEditable(true);
            nameText.setEditable(true);
            paisText.setEditable(true);
            profesionTextField.setEditable(true);
        });
        m2.addActionListener((ActionEvent e) ->        {
            if (JOptionPane.
                showConfirmDialog(null, "¿Estás seguro que desea eliminar su perfil?", "Eliminar perfil",
                                  JOptionPane.YES_NO_OPTION) == 0)
            {
                String id = person.getID();
                ApplicationController.getInstance().deletePerson(person);
                try
                {
                    mw.getFileManager().deletePicsDirectory(id);
                }
                catch (IOException ex)
                {
                    java.util.logging.Logger.getLogger(ProfileInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                mw.getCv().getViewport().undock(mw.getCv());
                mw.setIsOpened(false);
                mw.getSidePanel().deletePerson(person);
            }
        });

        m3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mw.setIsOpened(false);
                if (mw.getSa() != null && mw.isIsOSE())
                {
                    mw.getSa().getViewport().undock(mw.getSa());
                }
                mw.revalidatePhoto();
                mw.getCv().getViewport().undock(mw.getCv());
                mw.revalidatePhoto();

            }
        });

        m4.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (mw.isIsOSE())
                {
                    mw.getSa().getViewport().undock(mw.getSa());
                    mw.setIsOSE(false);
                }
                SidePanel sp = new SidePanel();
                System.out.println("Si");
                GraphIterator<Person> gi = (GraphIterator<Person>) ApplicationController.getInstance().
                    getsocialNetWork().breadthFirstSearchIterator(true);
                int label = ApplicationController.getInstance().getLabelofPerson(person);
                while (gi.hasNext())
                {
                    Person p = gi.next();
                    System.out.println(p.getName());
                    if (!p.equals(person) && !gi.isAdjacent(label))
                    {
                        sp.addPerson(p);
                    }
                }
                sp.setVisible(true);
                mw.setIsOSE(true);
                mw.setSa(mw.dockSP(sp, DockingConstants.CENTER_REGION, 1f));
                sp.addSelectionListener((var per) -> 
                {
                    EnviarSolicitudAmistad es = new EnviarSolicitudAmistad(per, person, mw);
                    es.setVisible(true);
                });
            }
        });

        /*JMenuItem m5 = new JMenuItem("Modificar cantidad de trabajos");
        jPopupMenu1.add(m5);
        m5.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (mw.isIsOSE())
                {
                    mw.getSa().getViewport().undock(mw.getSa());
                    mw.setIsOSE(false);
                }
                SidePanel sp = new SidePanel();
                System.out.println("Si");
                GraphIterator<Person> gi = (GraphIterator<Person>) ApplicationController.getInstance().
                    getsocialNetWork().iterator(ApplicationController.getInstance().getLabelofPerson(person));
                gi.next(ApplicationController.getInstance().getLabelofPerson(person));
                for (Integer i : gi.getAllAdjacentVertices())
                {
                    sp.addPerson(ApplicationController.getInstance().getPerson(i));
                }
                sp.setVisible(true);
                mw.setIsOSE(true);
                mw.setSa(mw.dockSP(sp, DockingConstants.CENTER_REGION, 1f));
                sp.addSelectionListener((var per) -> 
                {
                    EnviarModificarCantidad ec = new EnviarModificarCantidad(per, person, mw);
                    ec.setVisible(true);
                });
            }
        });*/

        m6.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Fotos", "jpg");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    try
                    {
                        File selectedPicture = chooser.getSelectedFile();
                        String fileName = FileManager.PPIC_NAME;
                        Files.delete(Paths.get(mw.getFileManager().getProfileDir(person.getID()).getAbsolutePath(),
                                               fileName));

                        Path path = Paths.get(mw.getFileManager().getProfileDir(person.getID()).getAbsolutePath(),
                                              fileName);
                        Files.copy(selectedPicture.toPath(), path);

                        FileManager fm = mw.getFileManager();
                        person.setPhoto(fm.getPictureInProfile(person.getID(), fileName));

                        ImageIcon icon = new ImageIcon(selectedPicture.getAbsolutePath());
                        Image img = icon.getImage();
                        Image scaled = img.getScaledInstance(128, 128, Image.SCALE_SMOOTH);

                        jPicLabel.setIcon(new ImageIcon(scaled));
                        mw.revalidatePhoto();
                    }
                    catch (IOException ex)
                    {
                        java.util.logging.Logger.getLogger(ProfileInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        m7.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (mw.isIsOSE())
                {
                    mw.getSa().getViewport().undock(mw.getSa());
                    mw.setIsOSE(false);
                }
                SidePanel sp = new SidePanel();
                System.out.println("Si");
                GraphIterator<Person> gi = ApplicationController.getInstance().
                    getsocialNetWork().iterator(ApplicationController.getInstance().getLabelofPerson(person));
                gi.next(ApplicationController.getInstance().getLabelofPerson(person));
                for (Integer i : gi.getAllAdjacentVertices())
                {
                    sp.addFriend(ApplicationController.getInstance().getPerson(i), person, mw);
                }
                sp.setVisible(true);
                mw.setIsOSE(true);
                mw.setSa(mw.dockSP(sp, DockingConstants.CENTER_REGION, 1f));
            }
        });

        m8.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CambiarContrasena cc = new CambiarContrasena(null, true, person);
                cc.setVisible(true);
            }
        });

        LinkedList<Notification> listN = (LinkedList<Notification>) ApplicationController.getInstance().
            getPendantNotifications().get(person);
        for (Notification n : listN)
        {
            String title;
            switch (n.getType())
            {
                case FRINDSHIP_REQUEST:
                    title = "Solicitud de amistad";
                    break;
                case WORKLOAD_MODIFICATION:
                    title = "Modificar trabajos";
                    break;
                case CONFIRMATION:
                    title = "Confirmación";
                    break;
                case NEGATION:
                    title = "Negación";
                    break;
                default:
                    title = "Información";
                    break;
            }

            JMenuItem j = new JMenuItem(title);

            j.addActionListener(new AbstractAction()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    switch (n.getType())
                    {
                        case FRINDSHIP_REQUEST:
                            @SuppressWarnings("unchecked") SolicitudAmistad sa = new SolicitudAmistad(n, person);
                            sa.setVisible(true);
                            jPopupMenu2.remove(j);
                            break;
                        case WORKLOAD_MODIFICATION:
                            @SuppressWarnings("unchecked") ModificarCantidadTrabajo mt = new ModificarCantidadTrabajo(n,
                                                                                                                      person);
                            mt.setVisible(true);
                            jPopupMenu2.remove(j);
                            break;
                        case CONFIRMATION:
                            JOptionPane.showMessageDialog(null, n.getData().toString(), "Información",
                                                          JOptionPane.INFORMATION_MESSAGE);
                            ApplicationController.getInstance().
                                getPendantNotifications().get(person)
                                .remove(n);
                            jPopupMenu2.remove(j);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, n.getData().toString(), "Información",
                                                          JOptionPane.INFORMATION_MESSAGE);
                            ApplicationController.getInstance().
                                getPendantNotifications().get(person)
                                .remove(n);
                            jPopupMenu2.remove(j);
                            break;
                    }
                }
            });
            jPopupMenu2.add(j);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        notificationButton = new javax.swing.JToggleButton();
        optionsMenuButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nameText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        apellidosText = new javax.swing.JTextField();
        paisText = new javax.swing.JTextField();
        profesionTextField = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeA = new javax.swing.JTree();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeC = new javax.swing.JTree();
        jPanel4 = new javax.swing.JPanel();
        jPicLabel = new javax.swing.JLabel();

        setName("Perfil"); // NOI18N
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 16));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        notificationButton.setIcon(UIManager.getIcon("Tree.expandedIcon")
        );
        notificationButton.setMaximumSize(new java.awt.Dimension(22, 22));
        notificationButton.setMinimumSize(new java.awt.Dimension(22, 22));
        notificationButton.setPreferredSize(new java.awt.Dimension(22, 22));
        notificationButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                notificationButtonActionPerformed(evt);
            }
        });
        jPanel1.add(notificationButton);

        optionsMenuButton.setText("...");
        optionsMenuButton.setComponentPopupMenu(jPopupMenu1);
        optionsMenuButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                optionsMenuButtonActionPerformed(evt);
            }
        });
        jPanel1.add(optionsMenuButton);

        add(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 256));
        jPanel3.setMinimumSize(new java.awt.Dimension(10, 256));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jLabel2.setText("Nombre:");

        nameText.setEditable(false);
        nameText.setText("jTextField1");
        nameText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                nameTextFocusLost(evt);
            }
        });
        nameText.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nameTextActionPerformed(evt);
            }
        });

        jLabel3.setText("Apellidos");

        jLabel4.setText("País:");

        jLabel5.setText("Profesión:");

        apellidosText.setEditable(false);
        apellidosText.setText("jTextField2");
        apellidosText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                apellidosTextFocusLost(evt);
            }
        });

        paisText.setEditable(false);
        paisText.setText("jTextField3");
        paisText.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                paisTextFocusLost(evt);
            }
        });

        profesionTextField.setEditable(false);
        profesionTextField.setText("jTextField4");
        profesionTextField.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                profesionTextFieldFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameText, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                    .addComponent(paisText)
                    .addComponent(apellidosText)
                    .addComponent(profesionTextField))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(apellidosText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(paisText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(profesionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Información General", jPanel5);

        jPanel6.setLayout(new java.awt.BorderLayout());

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTreeA.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeA.setRootVisible(false);
        jTreeA.setShowsRootHandles(true);
        jScrollPane1.setViewportView(jTreeA);

        jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Amigos", jPanel6);

        jPanel7.setLayout(new java.awt.BorderLayout());

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTreeC.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeC.setRootVisible(false);
        jTreeC.setShowsRootHandles(true);
        jScrollPane2.setViewportView(jTreeC);

        jPanel7.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Conexiones", jPanel7);

        jPanel3.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(32767, 128));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPicLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPicLabel.setMaximumSize(new java.awt.Dimension(128, 128));
        jPicLabel.setMinimumSize(new java.awt.Dimension(128, 128));
        jPicLabel.setPreferredSize(new java.awt.Dimension(128, 128));
        jPanel4.add(jPicLabel, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void optionsMenuButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_optionsMenuButtonActionPerformed
    {//GEN-HEADEREND:event_optionsMenuButtonActionPerformed
        if (mw.isIsOSE())
        {
            mw.getSa().getViewport().undock(mw.getSa());
            mw.setIsOSE(false);
        }
        jPopupMenu1.show(optionsMenuButton, optionsMenuButton.getHorizontalAlignment(), optionsMenuButton.
                         getVerticalAlignment());
    }//GEN-LAST:event_optionsMenuButtonActionPerformed

    private void notificationButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_notificationButtonActionPerformed
    {//GEN-HEADEREND:event_notificationButtonActionPerformed
        if (mw.isIsOSE())
        {
            mw.getSa().getViewport().undock(mw.getSa());
            mw.setIsOSE(false);
        }
        jPopupMenu2.show(notificationButton, notificationButton.getHorizontalAlignment(), notificationButton.
                         getVerticalAlignment());
    }//GEN-LAST:event_notificationButtonActionPerformed

    private void nameTextActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameTextActionPerformed
    {//GEN-HEADEREND:event_nameTextActionPerformed

    }//GEN-LAST:event_nameTextActionPerformed

    private void nameTextFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_nameTextFocusLost
    {//GEN-HEADEREND:event_nameTextFocusLost
        person.setName(nameText.getText());
    }//GEN-LAST:event_nameTextFocusLost

    private void apellidosTextFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_apellidosTextFocusLost
    {//GEN-HEADEREND:event_apellidosTextFocusLost
        person.setLastName(apellidosText.getText());
    }//GEN-LAST:event_apellidosTextFocusLost

    private void paisTextFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_paisTextFocusLost
    {//GEN-HEADEREND:event_paisTextFocusLost
        person.setCountry(paisText.getText());
    }//GEN-LAST:event_paisTextFocusLost

    private void profesionTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_profesionTextFieldFocusLost
    {//GEN-HEADEREND:event_profesionTextFieldFocusLost
        person.setProfession(profesionTextField.getText());
    }//GEN-LAST:event_profesionTextFieldFocusLost

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTabbedPane1MouseClicked
    {//GEN-HEADEREND:event_jTabbedPane1MouseClicked
        jTreeA.revalidate();
    }//GEN-LAST:event_jTabbedPane1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellidosText;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel jPicLabel;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTree jTreeA;
    private javax.swing.JTree jTreeC;
    private javax.swing.JTextField nameText;
    private javax.swing.JToggleButton notificationButton;
    private javax.swing.JButton optionsMenuButton;
    private javax.swing.JTextField paisText;
    private javax.swing.JTextField profesionTextField;
    // End of variables declaration//GEN-END:variables
}
