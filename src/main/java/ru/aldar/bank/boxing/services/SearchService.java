package ru.aldar.bank.boxing.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.aldar.bank.boxing.domain.Box;
import ru.aldar.bank.boxing.repository.BoxRepository;
import ru.aldar.bank.boxing.repository.ItemRepository;

@Service
public class SearchService {

    private
    BoxRepository boxRepository;
    @Autowired
    public SearchService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    public List<String> search(Integer id, String color) {
        return getId(boxRepository.getOne(id), color);
    }

    private List<String> getId(Box box, String color) {
        List<String> list = box.getItemsList().stream().filter(i -> i.getColor() != null).filter(i -> i.getColor().equals(color)).map(i -> i.getId().toString()).collect(Collectors.toList());
        if (box.getBoxList().isEmpty()) {
            return list;
        } else {
            box.getBoxList().forEach(b -> list.addAll(getId(b, color)));
            return list;
        }
    }

    /*
    select i.id from item i where (i.contained_in in (WITH RECURSIVE  r AS (

select id, contained_in from box where contained_in = 1

UNION all

select box.id, box.contained_in from box join r on box.contained_in = r.id
)
SELECT r.id FROM r) or i.contained_in = 1)  and color = 'red'

     */
}
