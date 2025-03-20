package com.example.profile_service.repository;

import com.example.profile_service.entity.Profile;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends Neo4jRepository<Profile, String> {

    @Query("MATCH (p:Profile) WHERE p.user_id = $user_id RETURN p")
    Optional<Profile> findByUser_id(String user_id);
}
