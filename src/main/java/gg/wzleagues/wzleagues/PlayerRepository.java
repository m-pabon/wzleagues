package gg.wzleagues.wzleagues;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

interface PlayerRepository extends MongoRepository<Player, Long> {
    Player findByName(String name);
    Player findById(String id);
    void deleteById(String id);
}
