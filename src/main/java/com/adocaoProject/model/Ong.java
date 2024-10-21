package com.adocaoProject.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Ong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "responsavel")
    private String responsavel;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "contato")
    private String contato;
    @Column(name = "email")
    private String email;
    @Column(name = "url_image_ong")
    private String urlImageOng;
    @Column(name = "animais")
    @OneToMany(mappedBy = "ong", cascade = CascadeType.ALL)
    private List<Pets> animais = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlImageOng() {
        return urlImageOng;
    }

    public void setUrlImageOng(String urlImageOng) {
        this.urlImageOng = urlImageOng;
    }

    public List<Pets> getAnimais() {
        return animais;
    }

    public void setAnimais(List<Pets> animais) {
        this.animais = animais;
    }





}
