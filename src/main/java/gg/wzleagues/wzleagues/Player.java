package gg.wzleagues.wzleagues;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PLAYER")
class Player {
    private @Id @GeneratedValue Long id;
    private String name;
    private String email;
    private String activisionId;
    private Rank rank;

    Player() {}

    Player(String name, String email, String activisionId, Rank rank) {
        this.name = name;
        this.email = email;
        this.activisionId = activisionId;
        this.rank = rank;
    }

    public Long getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getActivisionId() {
        return this.activisionId;
    }

    public Rank getRank(){
        return this.rank;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) { this.email = email; }

    public void setActivisionId(String activisionId) {
        this.activisionId = activisionId;
    }

    public void setRank(Rank rank) { this.rank = rank; }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Player))
            return false;
        Player player = (Player) o;
        return Objects.equals(this.id, player.id) && Objects.equals(this.name, player.name)
                && Objects.equals(this.email, player.email)
                && Objects.equals(this.activisionId, player.activisionId)
                && Objects.equals(this.rank, player.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.email, this.activisionId, this.rank);
    }

    @Override
    public String toString() {
        return "Player{" + "id=" + this.id + ", name='" + this.name + '\'' + ", email='" + this.email + '\'' + ", activisionId='" + this.activisionId + '\'' + ", rank='" + this.rank + '\'' + '}';
    }
}
