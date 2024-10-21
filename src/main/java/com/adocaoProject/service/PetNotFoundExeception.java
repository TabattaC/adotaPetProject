package com.adocaoProject.service;

public class PetNotFoundExeception extends Throwable {
    public PetNotFoundExeception(String message){
        super(message);
    }
}
