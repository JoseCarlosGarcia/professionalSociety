/*
 * Copyright (C) 2022 Javier Marrero
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

import java.awt.Image;


public class PersonBuilder
{

    private String name;
    private String lastName;
    private String ID;
    private String country;
    private String profession;
    private String pasword;
    private Image photo;

    public PersonBuilder()
    {
    }

    public PersonBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public PersonBuilder withLastName(String lastName)
    {
        this.lastName = lastName;
        return this;
    }

    public PersonBuilder withID(String ID)
    {
        this.ID = ID;
        return this;
    }

    public PersonBuilder withCountry(String country)
    {
        this.country = country;
        return this;
    }

    public PersonBuilder withProfession(String profession)
    {
        this.profession = profession;
        return this;
    }

    public PersonBuilder withPasword(String pasword)
    {
        this.pasword = pasword;
        return this;
    }

    public PersonBuilder withPhoto(Image photo)
    {
        this.photo = photo;
        return this;
    }

    public Person createPerson()
    {
        return new Person(name, lastName, ID, country, profession, pasword, photo);
    }
    
}
