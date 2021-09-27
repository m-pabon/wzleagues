package gg.wzleagues.wzleagues;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class PlayerModelAssembler implements RepresentationModelAssembler<Player, EntityModel<Player>> {

    @Override
    public EntityModel<Player> toModel(Player player) {

        EntityModel<Player> playerModel = EntityModel.of(player, //
                linkTo(methodOn(PlayerController.class).one(player.getId())).withSelfRel(),
                linkTo(methodOn(PlayerController.class).all()).withRel("players"));

        if(player.getRank() == Rank.BRONZE || player.getRank() == Rank.SILVER || player.getRank() == Rank.GOLD){
            playerModel.add(linkTo(methodOn(PlayerController.class).promote(player.getId())).withRel("promote"));
        }
        if(player.getRank() == Rank.SILVER || player.getRank() == Rank.GOLD || player.getRank() == Rank.DIAMOND){
            playerModel.add(linkTo(methodOn(PlayerController.class).demote(player.getId())).withRel("demote"));
        }
        return playerModel;
    }
}