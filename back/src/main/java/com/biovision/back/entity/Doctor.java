package com.biovision.back.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("DOCTOR")
public class Doctor extends JwtUserDetails {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "doctor_patient",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    @JsonIgnoreProperties("doctors") // Patient içindeki doctors alanı ignore edilir
    private List<Patient> patients = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("doctor") // Examination içindeki doctor alanı ignore edilir
    private List<Examination> examinations = new ArrayList<>();

    public String toString(){
        return "Doctor{" +
                "id=" + getId() +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", role='" + getRole() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                '}';
    }
}
