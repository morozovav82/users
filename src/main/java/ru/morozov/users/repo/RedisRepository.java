package ru.morozov.users.repo;

public interface RedisRepository {
    void add(String key, Object value);
    Object find(String key);
}