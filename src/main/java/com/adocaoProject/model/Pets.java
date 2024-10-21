package com.adocaoProject.model;

import jakarta.persistence.*;
@Entity
public class Pets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "tipo_pet")
    private String tipoPet;
    @Column(name = "idade")
    private String idade;
    @Column(name = "peso")
    private String peso;
    @Column(name = "tamanho")
    private String tamanho;
    @Column(name = "pelagem")
    private String pelagem;
    @Column(name = "cor")
    private String cor;
    @Column(name = "url_imagem_pet")
    private String urlImagemPet;
    @Column(name = "caracteristicas")
    private String caracteristicas;
    @ManyToOne
    @JoinColumn(name = "ong_id")
    private Ong ong;

    public Ong getOng() {
        return ong;
    }

    public void setOng(Ong ong) {
        this.ong = ong;
    }


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

    public String getTipoPet() {
        return tipoPet;
    }

    public void setTipoPet(String tipoPet) {
        this.tipoPet = tipoPet;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getPelagem() {
        return pelagem;
    }

    public void setPelagem(String pelagem) {
        this.pelagem = pelagem;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getUrlImagemPet() {
        return urlImagemPet;
    }

    public void setUrlImagemPet(String urlImagemPet) {
        this.urlImagemPet = urlImagemPet;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }




}
