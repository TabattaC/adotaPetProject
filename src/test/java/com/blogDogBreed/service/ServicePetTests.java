package com.blogDogBreed.service;

import com.adocaoProject.model.Ong;
import com.adocaoProject.model.Pets;
import com.adocaoProject.repository.OngRepository;
import com.adocaoProject.repository.PetRepository;
import com.adocaoProject.service.PetNotFoundExeception;
import com.adocaoProject.service.ServicePet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ServicePetTests {
    @InjectMocks
    private ServicePet servicePet;

    @Mock
    private PetRepository petRepository;

    @Mock
    private OngRepository ongRepository;
    private Ong ong;
    private List<Pets> pets;
    private Pets pet1;
    private Pets pet2;


    @Test
    public void testSearchPetForNameShouldReturnListPets() throws PetNotFoundExeception {
        // Simula a chamada do repositório
        when(petRepository.findByNomeContaining("Cachorro")).thenReturn(pets);
        List<Pets> result = servicePet.buscarPetPorNome("Cachorro");
        assertEquals(2, result.size());
        assertEquals("Cachorro_teste1", result.get(0).getNome());
        assertEquals("Cachorro_teste2", result.get(1).getNome());
    }

    @Test
    public void testSearchPetForNameShouldReturnAllPetsWhenNameWasEmpty() throws PetNotFoundExeception {
        // Pode variar, se você quer que o comportamento ao passar "" retorne todos os pets
        when(petRepository.findByNomeContaining("")).thenReturn(pets);
        List<Pets> result = servicePet.buscarPetPorNome("");
        assertEquals(2, result.size());
    }

    @Test
    public void testSearchPetForNameMaiusculoShouldReturnListPets() throws PetNotFoundExeception {
        // Simula a chamada do repositório
        when(petRepository.findByNomeContaining("TESTE")).thenReturn(pets);
        List<Pets> result = servicePet.buscarPetPorNome("TESTE");
        assertEquals(2, result.size());
        assertEquals("Cachorro_teste1", result.get(0).getNome());
        assertEquals("Cachorro_teste2", result.get(1).getNome());
    }

    @Test
    public void testSearchPetForNameMinusculoShouldReturnListPets() throws PetNotFoundExeception {
        // Simula a chamada do repositório
        when(petRepository.findByNomeContaining("cachorro")).thenReturn(pets);
        List<Pets> result = servicePet.buscarPetPorNome("cachorro");
        assertEquals(2, result.size());
        assertEquals("Cachorro_teste1", result.get(0).getNome());
        assertEquals("Cachorro_teste2", result.get(1).getNome());
    }

    @Test
    public void testSearchPetForCaracteresEspeciaisShouldReturnListPets() throws PetNotFoundExeception {
        // Simula a chamada do repositório
        when(petRepository.findByNomeContaining("_")).thenReturn(pets);
        List<Pets> result = servicePet.buscarPetPorNome("_");
        assertEquals(2, result.size());
        assertEquals("Cachorro_teste1", result.get(0).getNome());
        assertEquals("Cachorro_teste2", result.get(1).getNome());
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Simula uma ONG com pets
        ong = new Ong();
        ong.setId(1L);
        ong.setNome("Adota Manhumirim");

        // Cria uma lista de pets
        pets = new ArrayList<>();
        Pets pet1 = new Pets();
        pet1.setId(1L);
        pet1.setNome("Cachorro_teste1");
        pet1.setOng(ong);

        Pets pet2 = new Pets();
        pet2.setId(2L);
        pet2.setNome("Cachorro_teste2");
        pet2.setOng(ong);

        pets.add(pet1);
        pets.add(pet2);

        // Associa pets à ONG
        ong.setAnimais(pets);
    }

}
