package gg.wzleagues.wzleagues;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PlayerRepository repository) {

        return args -> {

            repository.deleteAll();

            log.info("Preloading " + repository.save(new Player("Michael Pabon", "matricy@outlook.com", "Matricy#1286579", Rank.BRONZE)));
            log.info("Preloading " + repository.save(new Player("Matt Lino", "vector@gmail.com", "Vector#12750", Rank.BRONZE)));
            log.info("Preloading " + repository.save(new Player("Ryan Mulhearn", "strgr@hotmail.com", "STRNGR3737", Rank.BRONZE)));

            // fetch all players
            System.out.println("Players found with findAll():");
            System.out.println("-------------------------------");
            for (Player player : repository.findAll()) {
                System.out.println(player);
            }
            System.out.println();

            // fetch an individual player
            System.out.println("Players found with findByFirstName('Michael Pabon'):");
            System.out.println("--------------------------------");
            System.out.println(repository.findByName("Michael Pabon"));
        };
    }
}
