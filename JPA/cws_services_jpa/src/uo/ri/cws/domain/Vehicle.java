package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TVEHICLES")
public class Vehicle extends BaseEntity {
    @Column(unique = true)
    private String plateNumber;
    private String make;
    private String model;

    @ManyToOne
    private Client client;
    @ManyToOne
    @JoinColumn(name = "VEHICLETYPE_ID")
    private VehicleType type;
    @OneToMany(mappedBy = "Vehicle")
    private Set<WorkOrder> workOrders = new HashSet<>();

    Vehicle() {
    }

    public Vehicle(String plateNumber, String make, String model) {
        ArgumentChecks.isNotBlank(plateNumber);
        ArgumentChecks.isNotBlank(make);
        ArgumentChecks.isNotBlank(model);

        this.plateNumber = plateNumber;
        this.make = make;
        this.model = model;
    }

    public Vehicle(String plateNumber) {
        this(plateNumber, "no-make", "no-model");
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    @Override
    public int hashCode() {
        return Objects.hash(plateNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vehicle other = (Vehicle) obj;
        return Objects.equals(plateNumber, other.plateNumber);
    }

    @Override
    public String toString() {
        return "Vehicle [plateNumber=" + plateNumber + ", make=" + make
            + ", model=" + model + "]";
    }

    public Client getClient() {
        return client;
    }

    void _setClient(Client client) {
        this.client = client;

    }

    public VehicleType getVehicleType() {
        return type;
    }

    void _setVehicleType(VehicleType vt) {
        this.type = vt;
    }

    public Set<WorkOrder> getWorkOrders() {
        return new HashSet<>(workOrders);
    }

    Set<WorkOrder> _getWorkOrders() {
        return workOrders;
    }

}
