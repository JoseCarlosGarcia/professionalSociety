/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cu.edu.cujae.ed.snetwork.logic;

import cu.edu.cujae.ed.snetwork.utils.Friendship;
import cu.edu.cujae.ed.snetwork.utils.Notification;
import cu.edu.cujae.ed.snetwork.utils.NotificationType;
import cu.edu.cujae.graphy.utils.Pair;
import java.awt.Image;
import java.util.UUID;
import javax.swing.JOptionPane;

/**
 *
 * @author Jose
 */
public final class Person
{

    private String name;
    private String lastName;
    private String ID;
    private String country;
    private String profession;
    private String password;
    private Image photo;

    public Person(String name, String lastName, String ID, String country, String profession, String pasword, Image photo)
    {
        setName(name);
        setLastName(lastName);
        setID(ID);
        setCountry(country);
        setProfession(profession);
        this.password = pasword;
        this.photo = photo;

    }

    public Person(String name, String lastName, String ID, String country, String profession, String pasword)
    {
        this(name, lastName, ID, country, profession, pasword, null);
    }

    //Getters
    public String getName()
    {
        return this.name;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public String getID()
    {
        return this.ID;
    }

    public String getCountry()
    {
        return this.country;
    }

    public String getProfession()
    {
        return this.profession;
    }

    public String getPassword()
    {
        return this.password;
    }

    //Setters
    public void setPassword(String password)
    {
        if (password != null)
        {
            this.password = password;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setName(String name) throws IllegalArgumentException
    {
        if (name != null && validarNombre(name))
        {
            this.name = name;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setLastName(String lastName) throws IllegalArgumentException
    {
        if (lastName != null && validarApellidos(lastName))
        {
            this.lastName = lastName;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setCountry(String country) throws IllegalArgumentException
    {
        if (country != null && validarPais(country))
        {
            this.country = country;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setProfession(String profession) throws IllegalArgumentException
    {
        if (profession != null && validarProfesion(profession))
        {
            this.profession = profession;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public boolean validarNombre(String nombre)
    {
        boolean result = true;
        boolean flag;
        for (int i = 0; i < nombre.length() && result; i++)
        {
            flag = Character.isUpperCase(nombre.charAt(0)) && nombre.charAt(0) != ' ' && (Character.isLetter(nombre.
                                                                                          charAt(i)) || nombre.charAt(i) == ' ');
            if (!flag)
            {
                result = false;
                JOptionPane.showMessageDialog(null, "El nombre debe iniciar con letra mayúscula", "Nombre incorrecto",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;

    }

    public boolean validarApellidos(String apellidos)
    {
        boolean result = true;
        boolean flag;
        for (int i = 0; i < apellidos.length() && result; i++)
        {
            flag = Character.isUpperCase(apellidos.charAt(0)) && apellidos.charAt(0) != ' ' && (Character.isLetter(
                                                                                                apellidos.charAt(i)) || apellidos.
                                                                                                charAt(i) == ' ');
            if (!flag)
            {
                result = false;
                JOptionPane.showMessageDialog(null, "Los apellidos deben iniciar con letra mayúscula",
                                              "Apellidos incorrectos", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    public boolean validarPais(String apellidos)
    {
        boolean result = true;
        boolean flag;
        for (int i = 0; i < apellidos.length() && result; i++)
        {
            flag = Character.isUpperCase(apellidos.charAt(0)) && apellidos.charAt(0) != ' ' && (Character.isLetter(
                                                                                                apellidos.charAt(i)) || apellidos.
                                                                                                charAt(i) == ' ');
            if (!flag)
            {
                result = false;
                JOptionPane.showMessageDialog(null, "El país debe iniciar con letra mayúscula", "País incorrecto",
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }

    public boolean validarProfesion(String apellidos)
    {
        boolean result = true;
        boolean flag;
        for (int i = 0; i < apellidos.length() && result; i++)
        {
            flag = Character.isUpperCase(apellidos.charAt(0)) && apellidos.charAt(0) != ' ' && (Character.isLetter(
                                                                                                apellidos.charAt(i)) || apellidos.
                                                                                                charAt(i) == ' ');
            if (!flag)
            {
                result = false;
                JOptionPane.showMessageDialog(null, "La profesión debe iniciar con letra mayúscula",
                                              "Profesión incorrecta", JOptionPane.ERROR_MESSAGE);
            }
        }
        return result;
    }
    public void setID(String ID)
    {
        boolean flag = true;
        String dayA, monthA, yearA;
        int dayB, monthB, yearB;
        if (ID.length() == 11)
        {
            for (int i = 0; i < ID.length(); i++)
            {
                flag = Character.isDigit(ID.charAt(i));
            }
            if (flag)
            {
                dayA = ID.substring(4, 6);
                monthA = ID.substring(2, 4);
                yearA = ID.substring(0, 2);
                dayB = Integer.parseInt(dayA);
                monthB = Integer.parseInt(monthA);
                yearB = Integer.parseInt(yearA);

                if ((yearB > 50 && yearB < 100) || (yearB >= 0 && yearB < 5))
                {
                    if (monthB > 0 && monthB <= 12)
                    {
                        switch (monthB)
                        {
                            case 1:
                            case 3:
                            case 5:
                            case 7:
                            case 8:
                            case 10:
                            case 12:
                                if (dayB > 31)
                                {
                                    throw new IllegalArgumentException("Fecha no valida");
                                }   break;
                            case 4:
                            case 6:
                            case 9:
                            case 11:
                                if (dayB > 30)
                                {
                                    throw new IllegalArgumentException("Fecha no valida");
                                }   break;
                            case 2:
                                if (yearB % 4 != 0 && dayB > 28)
                                {
                                    throw new IllegalArgumentException("Fecha no valida");
                                }
                                else if (yearB % 4 == 0 && dayB > 29)
                                {
                                    throw new IllegalArgumentException("Fecha no valida");
                                }   break;
                            default:
                                break;
                        }
                    }
                    else
                    {
                        throw new IllegalArgumentException("Mes no valido");
                    }
                }
                else
                {
                    throw new IllegalArgumentException("Ano no valido");
                }
            }

            else
            {
                throw new IllegalArgumentException("El CI solo puede contener digitos");
            }

            this.ID = ID;
        }
        else
        {
            throw new IllegalArgumentException("El CI debe tener 11 digitos" + ID);
        }
    }

    public boolean equals(Person p)
    {
        return ID.equalsIgnoreCase(p.getID());
    }

    /**
     *
     * @param amountOfWork cantidad de trabajo a compartir
     * @param p persona a la que se le envia la solicitud de amistad
     * @return Pair<Notification<Friendship>,Person> donde la notificacion
     * contiene la persona solicitante y la cantidad de trabajo y Person es la
     * solicitada
     */
    public Pair<Notification, Person> sendFriendRequest(int amountOfWork, Person p)
    {
        return new Pair<>(new Notification(NotificationType.FRINDSHIP_REQUEST, "Desearía trabajar con usted",
                                           new Friendship(this, amountOfWork), UUID.randomUUID()), p);
    }

    /**
     *
     * @param amountOfWork nueva cantidad de trabajo a compartir
     * @param p persona a la que se le envia la solicitud
     * @return Pair<Notification<Friendship>,Person> donde la notificacion
     * contiene la persona solicitante y la cantidad de trabajo y Person es la
     * solicitada
     */
    public Pair<Notification, Person> sendWorkLoadModification(int amountOfWork, Person p)
    {
        return new Pair<>(new Notification(NotificationType.WORKLOAD_MODIFICATION,
                                           "Desearia modificar la cantidad de trabajo que compartimos", new Friendship(
                                               this, amountOfWork), UUID.randomUUID()), p);
    }

    /**
     * @return the photo
     */
    public Image getPhoto()
    {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(Image photo)
    {
        this.photo = photo;
    }

    @Override
    public String toString()
    {
        return name + " " + lastName;
    }
}
