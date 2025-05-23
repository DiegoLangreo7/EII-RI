package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TMECHANICS")
public class Mechanic extends BaseEntity {

    @Column(unique = true)
    private String nif;
    private String surname;
    private String name;

    @OneToMany(mappedBy = "mechanic")
    private Set<WorkOrder> assigned = new HashSet<>();
    @OneToMany(mappedBy = "mechanic")
    private Set<Intervention> interventions = new HashSet<>();

    Mechanic() {
    }

    /**
     * No hay accidental attributes en el constructor
     * 
     * @param nif
     * @param name
     * @param surname
     */
    public Mechanic(String nif, String name, String surname) {

        ArgumentChecks.isNotBlank(nif);
        ArgumentChecks.isNotBlank(surname);
        ArgumentChecks.isNotBlank(name);
        this.nif = nif;
        this.surname = surname;
        this.name = name;
    }

    public Mechanic(String nif) {
        this(nif, "no-name", "no-surname");
    }

    public Set<WorkOrder> getAssigned() {
        return new HashSet<>(assigned);
    }

    Set<WorkOrder> _getAssigned() {
        return assigned;
    }

    public Set<Intervention> getInterventions() {
        return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
        return interventions;
    }

    public String getNif() {
        return nif;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    /**
     * Solo atributos naturales
     */
    @Override
    public String toString() {
        return "Mechanic [nif=" + nif + ", surname=" + surname + ", name="
            + name + "]";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

}
