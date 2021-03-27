package com.avejava.springlibrary.alternativelibrary.dao;

import java.util.List;

public interface GeneralDao<T> {
    List<T> getAll();               // получить все объекты (например, всех авторов для класса Author)
    T get(long id);                 // плолучить объект по id (например, автора по его id для класса Author)
    T save (T obj);                 // сохранить объект в БД (и для добавления и для редактирования)
    void delete(T object);          // удалить объект

    List<T> search(String ... searchString);    // производит поиск объектов класса по ключевым словам
}
