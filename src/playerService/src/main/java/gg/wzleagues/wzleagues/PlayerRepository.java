package gg.wzleagues.wzleagues;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

interface PlayerRepository extends MongoRepository<Player, Long> {
    Page<Player> findAll(Pageable pageable);
    Player findByName(String name);
    Player findById(String id);
    void deleteById(String id);
}
