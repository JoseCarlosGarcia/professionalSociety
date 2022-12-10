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
import cu.edu.cujae.ed.snetwork.utils.Friendship;
import cu.edu.cujae.ed.snetwork.utils.Notification;
import cu.edu.cujae.ed.snetwork.utils.NotificationType;
import cu.edu.cujae.graphy.core.iterators.GraphIterator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import org.flexdock.docking.DockingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jose
 */
public class ProfileInfoPanel extends JPanel
{
    private SidePanel sp;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileInfoPanel.class);
    
    private Person person;
    private MainWindow mw;
    /**
     * Creates new form ProfileInfoPanel
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
        JMenuItem m1 = new JMenuItem("Modificar Perfil");
        JMenuItem m2 = new JMenuItem("Eliminar Perfil");
        JMenuItem m3 = new JMenuItem("Cerrar");
        JMenuItem m4 = new JMenuItem("Enviar solicitud");
        JMenuItem m5 = new JMenuItem("Modificar cantidad de trabajos");

        jPopupMenu1.add(m1);
        jPopupMenu1.add(m2);
        jPopupMenu1.add(m3);
        jPopupMenu1.add(m4);
        jPopupMenu1.add(m5);
        m1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                LOGGER.info("Nombre: {}", person.getName());
                apellidosText.setEditable(true);
                nameText.setEditable(true);
                paisText.setEditable(true);
                profesionTextField.setEditable(true);
            }
        });
        m2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (JOptionPane.
                    showConfirmDialog(null, "¿Estás seguro que desea eliminar su perfil?", TOOL_TIP_TEXT_KEY,
                                      JOptionPane.YES_NO_OPTION) == 0)
                {
                ApplicationController.getInstance().deletePerson(person);
                mw.getCv().getViewport().undock(mw.getCv());
                mw.setIsOpened(false);
                    mw.getSidePanel().deletePerson(person);
                }

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
                mw.getCv().getViewport().undock(mw.getCv());
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
                sp.addSelectionListener((var per) ->                {
                    EnviarSolicitudAmistad es = new EnviarSolicitudAmistad(per, person, mw);
                    es.setVisible(true);
                });
            }
        });

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
        });

        LinkedList<Notification<?>> listN = (LinkedList<Notification<?>>) ApplicationController.getInstance().
            getPendantNotifications().get(person);
        for (Notification<?> n : listN)
        {
            JMenuItem j = new JMenuItem(n.getType().toString());
            j.addActionListener(new AbstractAction()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (n.getType().equals(NotificationType.FRINDSHIP_REQUEST))
                    {
                        @SuppressWarnings("unchecked")
                        SolicitudAmistad sa = new SolicitudAmistad((Notification<Friendship>) n, person);
                        sa.setVisible(true);
                        jPopupMenu2.remove(j);
                    }
                    else if (n.getType().equals(NotificationType.WORKLOAD_MODIFICATION))
                    {
                        @SuppressWarnings("unchecked")
                        ModificarCantidadTrabajo mt = new ModificarCantidadTrabajo((Notification<Friendship>) n, person);
                        mt.setVisible(true);
                        jPopupMenu2.remove(j);
                    }
                    else if (n.getType().equals(NotificationType.CONFIRMATION))
                    {
                        JOptionPane.showMessageDialog(null, n.getData().toString(), "Información",
                                                      JOptionPane.INFORMATION_MESSAGE);
                        jPopupMenu2.remove(j);

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, n.getMessage(), "Información",
                                                      JOptionPane.INFORMATION_MESSAGE);
                        jPopupMenu2.remove(j);
                    }
                }
            });
            jPopupMenu2.add(j);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
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
        jTree1 = new javax.swing.JTree();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setName("Perfil"); // NOI18N
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 16));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        notificationButton.setText("Notificaciones");
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
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Información General", jPanel5);

        jPanel6.setLayout(new java.awt.BorderLayout());

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(jTree1);

        jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Amigos", jPanel6);

        jPanel7.setLayout(new java.awt.BorderLayout());

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree2.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane2.setViewportView(jTree2);

        jPanel7.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Conexiones", jPanel7);

        jPanel3.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(32767, 100));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Foto");
        jPanel4.add(jLabel1, java.awt.BorderLayout.CENTER);

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellidosText;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JTree jTree2;
    private javax.swing.JTextField nameText;
    private javax.swing.JToggleButton notificationButton;
    private javax.swing.JButton optionsMenuButton;
    private javax.swing.JTextField paisText;
    private javax.swing.JTextField profesionTextField;
    // End of variables declaration//GEN-END:variables
}
