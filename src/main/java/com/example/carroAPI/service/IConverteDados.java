package com.example.carroAPI.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
