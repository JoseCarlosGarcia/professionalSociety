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

import cu.edu.cujae.ed.snetwork.logic.Person;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Jose
 */
public class SideBarButton extends JPanel
{

    private boolean selected;
    public final static float DIM_SCALAR = 0.85f;
    private final Person person;
    private Color originalColor;

    /**
     * Creates new form SideBarButton
     *
     * @param person
     */
    public SideBarButton(Person person)
    {
        initComponents();
        this.selected = false;
        this.person = person;
        originalColor = this.getBackground();
        nameLabel.setText(person.getName() + " " + person.getLastName());
        infoLabel.setText(person.getProfession() + " - " + person.getCountry());

        if (person.getPhoto() != null)
        {
            photoLabel.setIcon(new ImageIcon(person.getPhoto().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
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

        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        photoLabel = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
        infoPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        infoLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaption));
        setMaximumSize(new java.awt.Dimension(200, 32));
        setMinimumSize(new java.awt.Dimension(200, 32));
        setPreferredSize(new java.awt.Dimension(200, 32));
        addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                formMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                formMouseExited(evt);
            }
        });
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));
        add(filler2);

        photoLabel.setMaximumSize(new java.awt.Dimension(32, 32));
        photoLabel.setMinimumSize(new java.awt.Dimension(32, 32));
        photoLabel.setName(""); // NOI18N
        photoLabel.setPreferredSize(new java.awt.Dimension(32, 32));
        add(photoLabel);
        add(filler1);

        infoPanel.setLayout(new javax.swing.BoxLayout(infoPanel, javax.swing.BoxLayout.PAGE_AXIS));

        nameLabel.setText("Nombre y apellidos");
        infoPanel.add(nameLabel);

        infoLabel.setText("Info");
        infoPanel.add(infoLabel);

        add(infoPanel);
    }// </editor-fold>//GEN-END:initComponents


    private void formMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseEntered
    {//GEN-HEADEREND:event_formMouseEntered
        if (!selected)
        {
            setDimColor();
        }
    }//GEN-LAST:event_formMouseEntered

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }


    private void formMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseClicked
    {//GEN-HEADEREND:event_formMouseClicked
        if (!selected)
        {
            setSelectedColor();
            this.selected = true;
        }
    }//GEN-LAST:event_formMouseClicked

    private void formMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_formMouseExited
    {//GEN-HEADEREND:event_formMouseExited
        if (!selected)
        {
            setOriginalColor();
        }
    }//GEN-LAST:event_formMouseExited

    private void setSelectedColor()
    {
        Color darker = originalColor.darker();
        this.setBackground(darker);
        for (Component component : this.getComponents())
        {
            component.setBackground(darker);
        }
    }

    private void setDimColor()
    {
        Color dim
                  = new Color((int) (originalColor.getRed() * DIM_SCALAR), (int) (originalColor.getGreen() * DIM_SCALAR),
                              (int) (originalColor.getBlue() * DIM_SCALAR));

        this.setBackground(dim);
        for (Component component : this.getComponents())
        {
            component.setBackground(dim);
        }
    }

    public void setOriginalColor()
    {
        this.setBackground(originalColor);
        for (Component component : this.getComponents())
        {
            component.setBackground(originalColor);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel photoLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the person
     */
    public Person getPerson()
    {
        return person;
    }

    @Override
    public void revalidate()
    {
        super.revalidate();
        
        if (person != null && person.getPhoto() != null)
        {
            photoLabel.setIcon(new ImageIcon(person.getPhoto().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
            nameLabel.setText(person.getName() + " " + person.getLastName());
            infoLabel.setText(person.getProfession() + " - " + person.getCountry());
        }
    }
    
}
