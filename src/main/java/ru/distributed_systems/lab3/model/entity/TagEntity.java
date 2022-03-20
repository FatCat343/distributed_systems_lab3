package ru.distributed_systems.lab3.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tags")
@IdClass(TagId.class)
public class TagEntity {
    @Id
    private Long nodeId;

    @Id
    private String key;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "node_id", insertable = false, updatable = false)
    private NodeEntity node;

    public TagEntity(long nodeId, String key, String value) {
        this.nodeId = nodeId;
        this.key = key;
        this.value = value;
    }
}