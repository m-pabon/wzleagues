package gg.wzleagues.wzleagues;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerController {

    private final PlayerRepository repository;

    private final PlayerModelAssembler assembler;

    PlayerController(PlayerRepository repository, PlayerModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/")
    @ResponseBody
    @Hidden
    public String home() {
        return "Welcome to the WZ Leagues Player API";
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @Operation(summary = "Get all players")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Player.class)) })
    })
    @GetMapping("/players")
    CollectionModel<EntityModel<Player>> all() {

        List<EntityModel<Player>> players = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(players, linkTo(methodOn(PlayerController.class).all()).withSelfRel());
    }

    @Operation(summary = "Create a new player")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Player.class)) })
    })
    @PostMapping("/players")
    ResponseEntity<?> newPlayer(@RequestBody Player newPlayer) {

        newPlayer.setRank(Rank.BRONZE);
        EntityModel<Player> entityModel = assembler.toModel(repository.save(newPlayer));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // Single player
    @Operation(summary = "Get a player by their id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found player",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Player.class)) }),
        @ApiResponse(responseCode = "404", description = "Player not found",
                content = @Content) })
    @GetMapping("/players/{id}")
    EntityModel<Player> one(@PathVariable String id) {
        Player player = repository.findById(id);
        if (player == null)
            throw new PlayerNotFoundException(id);
        return assembler.toModel(player);
    }

    @Operation(summary = "Update a player using their id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player updated",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Player.class)) }),
        @ApiResponse(responseCode = "404", description = "Player not found",
                content = @Content) })
    @PutMapping("/players/{id}")
    ResponseEntity<?> replacePlayer(@RequestBody Player newPlayer, @PathVariable String id) {

        Player updatedPlayer = repository.findById(id);
        if(updatedPlayer == null)
            throw new PlayerNotFoundException(id);
        else{
            updatedPlayer.setName(newPlayer.getName());
            updatedPlayer.setEmail(newPlayer.getEmail());
            updatedPlayer.setActivisionId(newPlayer.getActivisionId());
            updatedPlayer.setRank(newPlayer.getRank());
            repository.save(updatedPlayer);
        }
        EntityModel<Player> entityModel = assembler.toModel(updatedPlayer);
        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @Operation(summary = "Delete a player by their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Player.class)) }),
            @ApiResponse(responseCode = "404", description = "Player not found",
                    content = @Content) })
    @DeleteMapping("/players/{id}")
    ResponseEntity<?> deletePlayer(@PathVariable String id) {

        if(repository.findById(id) == null)
            throw new PlayerNotFoundException(id);
        else
            repository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Promote a player's rank by one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Player.class)) }),
            @ApiResponse(responseCode = "404", description = "Player not found",
                    content = @Content) })
    @PutMapping("/players/{id}/promote")
    ResponseEntity<?> promote(@PathVariable String id) {

        Player player = repository.findById(id);
        if(player == null)
            throw new PlayerNotFoundException(id);

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

    @Operation(summary = "Demote a player's rank by one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Player.class)) }),
            @ApiResponse(responseCode = "404", description = "Player not found",
                    content = @Content) })
    @PutMapping("/players/{id}/demote")
    ResponseEntity<?> demote(@PathVariable String id) {

        Player player = repository.findById(id);
        if(player == null)
            throw new PlayerNotFoundException(id);

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
