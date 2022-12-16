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
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static javax.swing.JComponent.TOOL_TIP_TEXT_KEY;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Jose
 */
public class SidePanel extends JPanel
{

    private final List<SelectionListener<Person>> listeners;
    private final List<SideBarButton> buttonList;

    /**
     * Creates new form SidePanel
     */
    public SidePanel()
    {
        initComponents();
        this.buttonList = new LinkedList<>();
        this.listeners = new ArrayList<>();
    }

    public void addPerson(Person p)
    {
        SideBarButton sdb = new SideBarButton(p);
        buttonList.add(sdb);
        GridLayout gl = (GridLayout) sideBarContainer.getLayout();
        gl.setRows(gl.getRows() + 1);
        sideBarContainer.add(sdb);

        sdb.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mousePressed(MouseEvent e)
            {
                for (SideBarButton button : buttonList)
                {
                    if (button != sdb && button.isSelected())
                    {
                        button.setSelected(false);
                        button.setOriginalColor();
                    }
                }

                for (SelectionListener<Person> listener : listeners)
                {
                    listener.selectionChanged(sdb.getPerson());
                }
            }
        });
        this.revalidate();
    }

    public void addFriend(Person p, Person actual, MainWindow mw)
    {
        SideBarButton sdb = new SideBarButton(p);
        buttonList.add(sdb);
        GridLayout gl = (GridLayout) sideBarContainer.getLayout();
        gl.setRows(gl.getRows() + 1);
        sideBarContainer.add(sdb);
        JPopupMenu pop = new javax.swing.JPopupMenu();
        JMenuItem m1 = new JMenuItem("Modificar cantidad de trabajos");
        JMenuItem m2 = new JMenuItem("Eliminar Amistad");

        m1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                EnviarModificarCantidad ec = new EnviarModificarCantidad(p, actual, mw);
                ec.setVisible(true);
            }
        });

        m2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (JOptionPane.
                    showConfirmDialog(null, "¿Está seguro que desea cancelar la amistad?", TOOL_TIP_TEXT_KEY,
                                      JOptionPane.YES_NO_OPTION) == 0)
                {
                    if (ApplicationController.getInstance().deleteFriendship(actual, p))
                    {
                        JOptionPane.showMessageDialog(null,
                                                      p.getName() + " " + p.getLastName() + " y tú ya no son amigos",
                                                      TOOL_TIP_TEXT_KEY,
                                                      JOptionPane.INFORMATION_MESSAGE, null);
                        deletePerson(p);
                        sdb.revalidate();
                    }
                }

            }
        });

        pop.add(m1);
        pop.add(m2);

        sdb.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mousePressed(MouseEvent e)
            {
                for (SideBarButton button : buttonList)
                {
                    if (button != sdb && button.isSelected())
                    {
                        button.setSelected(false);
                        button.setOriginalColor();
                    }
                    else
                    {
                        pop.show(sdb, (int) sdb.getAlignmentX(), (int) sdb.getAlignmentY());
                    }
                }

                for (SelectionListener<Person> listener : listeners)
                {
                    listener.selectionChanged(sdb.getPerson());
                }

            }
        });
        this.revalidate();

    }
    public Person getSelectedPerson()
    {
        Person person = null;
        for (SideBarButton button : buttonList)
        {
            if (button.isSelected())
            {
                person = button.getPerson();
                break;
            }
        }
        return person;
    }

    public void deletePerson(Person p)
    {
        SideBarButton sdb = null;
        for (SideBarButton button : buttonList)
        {
            if (button.getPerson().equals(p))
            {
                sdb = button;
                break;
            }
        }
        if (sdb != null)
        {
            buttonList.remove(sdb);
            sideBarContainer.remove(sdb);
        }
        this.revalidate();
    }

    public List<SideBarButton> getButtonList()
    {
        return buttonList;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        sideBarContainer = new javax.swing.JPanel();

        setName("Usuarios"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        sideBarContainer.setName("Usuarios"); // NOI18N
        sideBarContainer.setLayout(new java.awt.GridLayout(1, 0));
        jScrollPane1.setViewportView(sideBarContainer);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel sideBarContainer;
    // End of variables declaration//GEN-END:variables

    public void addSelectionListener(SelectionListener<Person> sl)
    {
        listeners.add(sl);
    }

    public void deleteSelectionListener(SelectionListener<Person> sl)
    {
        listeners.remove(sl);
    }
}
