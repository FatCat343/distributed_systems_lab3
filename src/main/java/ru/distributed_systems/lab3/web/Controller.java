package ru.distributed_systems.lab3.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.distributed_systems.lab3.model.Converter;
import ru.distributed_systems.lab3.model.dto.NodeDto;
import ru.distributed_systems.lab3.service.NodeService;
import ru.distributed_systems.lab3.service.OsmService;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class Controller {
    private final NodeService nodeService;

    private final OsmService service;

    @GetMapping("/{id}")
    public NodeDto getNode(@PathVariable("id") Long id) {
        return Converter.convertNodeEntityToNodeDto(nodeService.getNodeEntityById(id));
    }

    @PostMapping
    public NodeDto createNode(@RequestBody NodeDto node) {
        return Converter.convertNodeEntityToNodeDto(
                nodeService.saveNodeEntity(Converter.convertNodeDtoToNodeEntity(node)));
    }

    @PutMapping("/{id}")
    public NodeDto updateNode(@PathVariable("id") Long id,
                              @RequestBody NodeDto node) {
        return Converter.convertNodeEntityToNodeDto(
                nodeService.updateNodeEntity(
                        id, Converter.convertNodeDtoToNodeEntity(node))
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        nodeService.deleteNodeEntity(id);
    }

    @GetMapping("/search")
    public List<NodeDto> search(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("radius") Double radius
    ) {
        return nodeService.searchNearNodes(latitude, longitude, radius)
                .stream()
                .map(Converter::convertNodeEntityToNodeDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/init")
    public void init() {
        try {
            service.handleOsm();
        } catch (IOException | XMLStreamException | JAXBException e) {
            e.printStackTrace();
        }
    }

}
