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
@Table(name = "TCLIENTS")
public class Client extends BaseEntity {
    @Column(unique = true)
    private String nif;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Address address;

    @OneToMany(mappedBy = "client")
    private Set<Vehicle> vehicles = new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<PaymentMean> paymentMeans = new HashSet<>();

    Client() {
    }

    public Client(String nif, String name, String surname) {
        this(nif, name, surname, "no-email", "no-phone",
            new Address("no-street", "no-city", "no-zip"));
    }

    public Client(String nif, String name, String surname, String email,
        String phone, Address address) {
        ArgumentChecks.isNotBlank(nif);
        ArgumentChecks.isNotBlank(name);
        ArgumentChecks.isNotBlank(surname);
        ArgumentChecks.isNotBlank(email);
        ArgumentChecks.isNotBlank(phone);
        ArgumentChecks.isNotNull(address);

        this.nif = nif;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Client(String nif) {
        this(nif, "name", "surname", "email", "phone",
            new Address("Street", "City", "ZipCode"));
    }

    public String getNif() {
        return nif;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Client [nif=" + nif + ", name=" + name + ", surname=" + surname
            + ", email=" + email + ", phone=" + phone + ", address=" + address
            + "]";
    }

    public Set<Vehicle> getVehicles() {
        return new HashSet<>(vehicles);
    }

    Set<Vehicle> _getVehicles() {
        return vehicles;
    }

    public Set<PaymentMean> getPaymentMeans() {
        return new HashSet<>(paymentMeans);
    }

    Set<PaymentMean> _getPaymentMeans() {
        return paymentMeans;
    }

    public void setAddress(Address address) {
        ArgumentChecks.isNotNull(address, "Invalid address");
        this.address = address;

    }

}