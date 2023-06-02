package com.weride.repository;
import com.weride.model.UserCardRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCardRelationRepository extends JpaRepository<UserCardRelation, Long> {

}
