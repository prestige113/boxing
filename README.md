# boxing
Тестовое задание

На вход в виде параметра командной строки java-приложению передаётся
имя XML-файла, в котором задано взаимное положение предметов(Item) и ящиков(Box).
Пример такого файла:
<?xml version="1.0" encoding="UTF-8"?>
<Storage>
 <Box id="1">
   <Item id="1"/>
   <Item color="red" id="2"/>
   <Box id="3">
       <Item id="3" color="red" />
       <Item id="4" color="black" />    
   </Box>
   <Box id="6"/>
   <Item id="5"/>
 </Box>
 <Item id="6"/>
</Storage>
- ящики могут быть пустыми или содержать предметы или другие ящики;
- у каждого ящика и предмета есть id;
- id какого-либо предмета и какого-либо ящика могут совпадать,
 но в совокупности предметов они уникальны (как и в совокупности ящиков);
- вложенность ящиков может быть любой;
- предметы могут не иметь цвета;
- предметы могут быть не в ящике
Требуется написать приложение, которое:
1. заполняет при старте SQL-БД приведённой ниже структуры в соответствии с переданным XML-файлом

CREATE TABLE box
(
  id integer NOT NULL DEFAULT nextval('box_id_seq'::regclass),
  contained_in integer,
  CONSTRAINT box_pk PRIMARY KEY (id),
  CONSTRAINT box_fk FOREIGN KEY (contained_in)
      REFERENCES public.box (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)

CREATE TABLE item
(
  id integer NOT NULL DEFAULT nextval('item_id_seq'::regclass),
  contained_in integer,
  color character varying(100),
  CONSTRAINT item_pk PRIMARY KEY (id),
  CONSTRAINT item_fk FOREIGN KEY (contained_in)
      REFERENCES public.box (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
Примечание: выбор СУБД на усмотрение кандидата (как варианты embedded DBMS можно, например, взять: H2, sqlite и т.п.)
В случае использования embedded/in-memory СУБД нужно залогировать в файл содержимое таблиц после загрузки.
2. После загрузки файла приложение должно работать, как REST-сервис, который возвращает id предметов
заданного цвета(color) содержащиеся в ящике c заданным идентификатором (box)
с учётом того, что в ящике может быть ещё ящик с предметами требуемого цвета.
Например, на POST-запрос с телом запроса в JSON вида:
POST /test HTTP/1.1
Host: localhost
Accept: application/json
Content-Type:application/json
Content-Length: 25
{"box":"1","color":"red"}
для вышеприведённого XML должен быть ответ вида:
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Mon, 01 Sep 2019 12:00:26 GMT
[2,3]
