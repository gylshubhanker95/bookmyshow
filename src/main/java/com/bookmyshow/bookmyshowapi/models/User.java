package com.bookmyshow.bookmyshowapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User extends Auditable{

    @Column(unique = true,nullable = false)
    private String mobile;

    @Column(name = "name",nullable = false)
    private String name;

}
