package ru.distributed_systems.lab3.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@AllArgsConstructor
public class NodeDto {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

    @NotNull
    private Map<String, String> tags;
}
