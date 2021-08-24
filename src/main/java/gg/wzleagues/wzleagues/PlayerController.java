package gg.wzleagues.wzleagues;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private final PlayerRepository repository;

    private final PlayerModelAssembler assembler;

    PlayerController(PlayerRepository repository, PlayerModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/players")
    CollectionModel<EntityModel<Player>> all() {

        List<EntityModel<Player>> players = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(players, linkTo(methodOn(PlayerController.class).all()).withSelfRel());
    }

    @PostMapping("/players")
    ResponseEntity<?> newPlayer(@RequestBody Player newPlayer) {

        newPlayer.setRank(Rank.BRONZE);
        EntityModel<Player> entityModel = assembler.toModel(repository.save(newPlayer));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single item

    @GetMapping("/players/{id}")
    EntityModel<Player> one(@PathVariable Long id) {

        Player player = repository.findById(id) //
                .orElseThrow(() -> new PlayerNotFoundException(id));

        return assembler.toModel(player);
    }

    @PutMapping("/players/{id}")
    ResponseEntity<?> replacePlayer(@RequestBody Player newPlayer, @PathVariable Long id) {

        Player updatedPlayer = repository.findById(id) //
                .map(player -> {
                    player.setName(newPlayer.getName());
                    player.setEmail(newPlayer.getEmail());
                    player.setActivisionId(newPlayer.getActivisionId());
                    player.setRank(newPlayer.getRank());
                    return repository.save(player);
                }) //
                .orElseGet(() -> {
                    newPlayer.setId(id);
                    return repository.save(newPlayer);
                });

        EntityModel<Player> entityModel = assembler.toModel(updatedPlayer);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/players/{id}")
    ResponseEntity<?> deletePlayer(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/players/{id}/promote")
    ResponseEntity<?> promote(@PathVariable Long id) {

        Player player = repository.findById(id) //
                .orElseThrow(() -> new PlayerNotFoundException(id));

        if (player.getRank() == Rank.BRONZE) {
            player.setRank(Rank.SILVER);
            return ResponseEntity.ok(assembler.toModel(repository.save(player)));
        }
        else if(player.getRank() == Rank.SILVER){
            player.setRank(Rank.GOLD);
            return ResponseEntity.ok(assembler.toModel(repository.save(player)));
        }
        else if(player.getRank() == Rank.GOLD){
            player.setRank(Rank.DIAMOND);
            return ResponseEntity.ok(assembler.toModel(repository.save(player)));
        }
        else{
            return ResponseEntity //
                    .status(HttpStatus.METHOD_NOT_ALLOWED) //
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                    .body(Problem.create() //
                            .withTitle("Method not allowed") //
                            .withDetail("You can't promote a player that is " + player.getRank() + " rank"));
        }
    }

    @PutMapping("/players/{id}/demote")
    ResponseEntity<?> demote(@PathVariable Long id) {

        Player player = repository.findById(id) //
                .orElseThrow(() -> new PlayerNotFoundException(id));

        if (player.getRank() == Rank.SILVER) {
            player.setRank(Rank.BRONZE);
            return ResponseEntity.ok(assembler.toModel(repository.save(player)));
        }
        else if(player.getRank() == Rank.GOLD){
            player.setRank(Rank.SILVER);
            return ResponseEntity.ok(assembler.toModel(repository.save(player)));
        }
        else if(player.getRank() == Rank.DIAMOND){
            player.setRank(Rank.GOLD);
            return ResponseEntity.ok(assembler.toModel(repository.save(player)));
        }
        else{
            return ResponseEntity //
                    .status(HttpStatus.METHOD_NOT_ALLOWED) //
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                    .body(Problem.create() //
                            .withTitle("Method not allowed") //
                            .withDetail("You can't demote a player that is " + player.getRank() + " rank"));
        }
    }
}
