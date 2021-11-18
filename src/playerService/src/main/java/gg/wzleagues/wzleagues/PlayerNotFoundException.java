package gg.wzleagues.wzleagues;

class PlayerNotFoundException  extends RuntimeException{
    PlayerNotFoundException(String id){
        super("Could not find player " + id);
    }
}
