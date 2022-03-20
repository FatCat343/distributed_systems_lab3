package ru.distributed_systems.lab3.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nodes")
public class NodeEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String user;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(mappedBy = "node", cascade = CascadeType.ALL)
    private List<TagEntity> tags;

    public NodeEntity(Long id, String user, Double longitude, Double latitude) {
        this.id = id;
        this.user = user;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}