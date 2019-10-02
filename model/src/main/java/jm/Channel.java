package jm;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "channels")
public class Channel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "channels_users", joinColumns = @JoinColumn(name = "channel_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @Column(name = "owner_id")
    private Integer ownerId;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    public Channel() {
    }

    public Channel(String name, List<User> users, Integer ownerId, Boolean isPrivate, LocalDate createdDate) {
        this.name = name;
        this.users = users;
        this.ownerId = ownerId;
        this.isPrivate = isPrivate;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivacy(Boolean privacy) {
        this.isPrivate = privacy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return isPrivate == channel.isPrivate &&
                id.equals(channel.id) &&
                name.equals(channel.name) &&
                Objects.equals(users, channel.users) &&
                ownerId.equals(channel.ownerId) &&
                Objects.equals(createdDate, channel.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, users, ownerId, isPrivate, createdDate);
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", ownerId=" + ownerId +
                ", privacy=" + isPrivate +
                ", createdDate=" + createdDate +
                '}';
    }
}
