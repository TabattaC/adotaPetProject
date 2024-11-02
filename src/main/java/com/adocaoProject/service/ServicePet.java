package com.adocaoProject.service;

import com.adocaoProject.handler.BusinessException;
import com.adocaoProject.model.Ong;
import com.adocaoProject.model.Pets;
import com.adocaoProject.repository.OngRepository;
import com.adocaoProject.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicePet {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private OngRepository ongRepository;

    @Autowired
    private ServiceOng serviceOng;

    public List<Pets> listarOngs() {
        return petRepository.findAll();
    }


    // Buscar uma ONG pelo ID
    public Pets buscarPetPorId(Long id) {
        return petRepository.findById(id).orElseThrow(() -> new RuntimeException("ONG não encontrada"));
    }

    //Buscar uma ONG por nome
    public List<Pets> buscarPetPorNome(String nome) throws PetNotFoundExeception {
        List<Pets> pets = petRepository.findByNomeContaining(nome);
        if(pets == null || pets.isEmpty()){
            throw new PetNotFoundExeception("Nenhum pet encontrado com o nome: " + nome);
        }
        return pets;
    }


    //Atualizar apenas ONG
    public Pets atualizarPet(Long petId, Pets petAtualizado) {
        Pets petExistente = petRepository.findById(petId)
                .orElseThrow(() -> new BusinessException("Pet não encontrado com o ID: " + petId));
        // Atualizar os campos conforme necessário
        petExistente.setNome(petAtualizado.getNome());
        petExistente.setTipoPet(petAtualizado.getTipoPet());
        petExistente.setIdade(petAtualizado.getIdade());
        petExistente.setPeso(petAtualizado.getPeso());
        petExistente.setTamanho(petAtualizado.getTamanho());
        petExistente.setPelagem(petAtualizado.getPelagem());
        petExistente.setCor(petAtualizado.getCor());
        petExistente.setUrlImagemPet(petAtualizado.getUrlImagemPet());
        petExistente.setCaracteristicas(petExistente.getCaracteristicas());
        return petRepository.save(petExistente);
    }


    public void deletarPet(Long id) {
        Pets ong = buscarPetPorId(id);
        petRepository.delete(ong);
    }


    public List<Pets> listarPetsPorOng(Long ongId) {
        Ong ong  = ongRepository.findById(ongId).orElseThrow(() -> new RuntimeException("ONG não encontrada"));
        return ong.getAnimais();
    }

    public Ong buscarOngPorPet(Long petId) {
        Pets pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        return pet.getOng();
    }
}
