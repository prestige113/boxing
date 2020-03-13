package ru.aldar.bank.boxing.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "box")
public class Box {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "contained_in")
    private Box containedIn;

    @OneToMany(mappedBy = "containedIn")
    private List<Item> itemsList;

    @OneToMany(mappedBy = "containedIn")
    private List<Box> boxList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Box getContainedIn() {
        return containedIn;
    }

    public void setContainedIn(Box containedIn) {
        this.containedIn = containedIn;
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
    }

    public List<Box> getBoxList() {
        return boxList;
    }

    public void setBoxList(List<Box> boxList) {
        this.boxList = boxList;
    }
}
