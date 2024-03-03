package com.justbelieveinmyself.library.dto;

public interface Dto<T> {
    Dto<T> fromEntity(T entity);
    T toEntity();
}
