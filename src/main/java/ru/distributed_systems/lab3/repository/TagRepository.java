package ru.distributed_systems.lab3.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.distributed_systems.lab3.model.entity.TagEntity;
import ru.distributed_systems.lab3.model.entity.TagId;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, TagId> {
}
