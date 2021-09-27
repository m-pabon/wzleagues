package gg.wzleagues.wzleagues;

import org.springframework.data.mongodb.repository.MongoRepository;

interface PlayerRepository extends MongoRepository<Player, Long> {
    Player findByName(String name);
    Player findById(String id);
    void deleteById(String id);
}
