package ru.distributed_systems.lab3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.distributed_systems.lab3.model.entity.NodeEntity;
import ru.distributed_systems.lab3.repository.NodeRepository;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class NodeService {
    private final List<NodeEntity> nodesBatch = new ArrayList<>();
    private final NodeRepository nodeRepository;

    public NodeEntity saveNodeEntity(NodeEntity node) {
        return nodeRepository.save(node);
    }

    public void saveNodeEntityBatched(NodeEntity node) {
        nodesBatch.add(node);
        if (nodesBatch.size() == 500) {
            nodeRepository.saveAll(nodesBatch);
            nodesBatch.clear();
        }
    }

    public NodeEntity getNodeEntityById(Long id) {
        return nodeRepository
                .findById(id)
                .orElseThrow(NullPointerException::new);
    }

    public NodeEntity updateNodeEntity(Long id, NodeEntity node) {
        NodeEntity nodeFromDb = nodeRepository
                .findById(id)
                .orElseThrow(NullPointerException::new);

        node.setId(nodeFromDb.getId());
        return nodeRepository.save(node);
    }

    public void deleteNodeEntity(long id) {
        NodeEntity node = nodeRepository
                .findById(id)
                .orElseThrow(NullPointerException::new);
        nodeRepository.delete(node);
    }

    public List<NodeEntity> searchNearNodes(Double latitude, Double longitude, Double radius) {
        return nodeRepository.getNearNodes(latitude, longitude, radius);
    }

}
