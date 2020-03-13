package ru.aldar.bank.boxing.mapper;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ru.aldar.bank.boxing.domain.Box;
import ru.aldar.bank.boxing.domain.Item;
import ru.aldar.bank.boxing.repository.BoxRepository;
import ru.aldar.bank.boxing.repository.ItemRepository;

@Component
public class ReadXMLFile {

    private BoxRepository boxRepository;

    private ItemRepository itemRepository;

    HashMap<Integer, Box> boxMap = new HashMap<>();
    HashMap<Integer, Item> itemsMap = new HashMap<>();

    @Autowired
    public ReadXMLFile(BoxRepository boxRepository, ItemRepository itemRepository) {
        this.boxRepository = boxRepository;
        this.itemRepository = itemRepository;
    }

    public void parse(String path) {
        try {
            File fXmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Box");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    parseNode(nNode);
                }
            }
            nList = doc.getElementsByTagName("Item");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    parseNode(nNode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        boxMap.forEach((key, value) -> boxRepository.saveAndFlush(value));

        itemsMap.forEach((key, value) -> itemRepository.saveAndFlush(value));
    }

    private void parseNode(Node node) {
        Element eElement = (Element) node;
        if (node.getNodeName().equals("Box")) {
            Box box = new Box();
            String id = eElement.getAttribute("id");
            if (id.equals("")) {
                id = null;
            }
            String contained_in = ((Element) node.getParentNode()).getAttribute("id");
            if (contained_in.equals("")) {
                contained_in = null;
            }

            if (id != null) {
                box.setId(Integer.parseInt(id));
            }
            if (contained_in != null) {
                Box parent = boxMap.get(Integer.parseInt(contained_in));
                box.setContainedIn(parent);
            }
            boxMap.put(box.getId(), box);
        } else if (node.getNodeName().equals("Item")) {
            String id = eElement.getAttribute("id");
            Item item = new Item();
            if (id.equals("")) {
                id = null;
            }
            String contained_in = ((Element) node.getParentNode()).getAttribute("id");
            if (contained_in.equals("")) {
                contained_in = null;
            }
            if (contained_in != null) {
                Box parent = boxMap.get(Integer.parseInt(contained_in));
                item.setContainedIn(parent);
            }
            String color = eElement.getAttribute("color");
            if (color.equals("")) {
                color = null;
            }
            if (id != null) {
                item.setId(Integer.parseInt(id));
            }
            item.setColor(color);
            itemsMap.put(item.getId(), item);
        }
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {
                parseNode(nodeList.item(i));
            }
        }
    }
}
