package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TPROVIDERS")
public class Provider extends BaseEntity {

    @Column(unique = true)
    private String nif;

    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "provider")
    private Set<Supply> supplies = new HashSet<>();

    @OneToMany(mappedBy = "provider")
    private Set<Order> orders = new HashSet<>();

    Provider() {

    }

    public Provider(String nif, String name, String email, String phone) {

        this.nif = nif;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Provider(String nif) {
        this.nif = nif;
    }

    public String getNif() {
        return nif;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    protected void _setSupplies(Set<Supply> supplies) {
        this.supplies = supplies;
    }

    public Set<Supply> getSupplies() {
        return new HashSet<>(supplies);
    }

    protected Set<Supply> _getSupplies() {
        return supplies;
    }

    public Set<Order> getOrders() {
        return new HashSet<>(orders);
    }

    protected Set<Order> _getOrders() {
        return orders;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
