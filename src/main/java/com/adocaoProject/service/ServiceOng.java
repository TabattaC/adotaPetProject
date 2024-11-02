package com.adocaoProject.service;

import com.adocaoProject.handler.BusinessException;
import com.adocaoProject.handler.CampoObrigatorioException;
import com.adocaoProject.model.Ong;
import com.adocaoProject.model.Pets;
import com.adocaoProject.repository.OngRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ServiceOng {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ServiceOng.class);

    @Autowired
    private OngRepository ongRepository;

    //Buscar ongs
    public List<Ong> listarOngs() {
        return ongRepository.findAll();
    }


    // Buscar uma ONG pelo ID
    public Ong buscarOngPorId(Long id) {
        return ongRepository.findById(id).orElseThrow(() -> new RuntimeException("ONG não encontrada"));
    }

    //Buscar uma ONG por nome
    public List<Ong> buscarOngPorNome(String nome) {
        return ongRepository.findByNomeContaining(nome);
    }

    //Salvar uma ONG
    public Ong salvarOng(Ong ong) {
        if(ong.getNome()==null){
            throw new CampoObrigatorioException("Nome");
        }
        logger.info("SAVE - Recebendo ong na camada de repositorio");
        return ongRepository.save(ong);
    }

    //Atualizar apenas ONG
    public Ong atualizarOng(Long id, Ong ongAtualizada) {
        Ong ongExistente = buscarOngPorId(id);
        // Atualizar os campos conforme necessário
        ongExistente.setNome(ongAtualizada.getNome());
        ongExistente.setResponsavel(ongAtualizada.getResponsavel());
        ongExistente.setEndereco(ongAtualizada.getEndereco());
        ongExistente.setContato(ongAtualizada.getContato());
        ongExistente.setEmail(ongAtualizada.getEmail());
        ongExistente.setUrlImageOng(ongAtualizada.getUrlImageOng());
            return ongRepository.save(ongExistente);
    }

    public Ong atualizarAnimaisDaOng(Long idOng, List<Pets> animais) {
        Ong ongExistente = buscarOngPorId(idOng);
        // Atualizar a lista de animais associados à ONG
        ongExistente.setAnimais(animais);

        return ongRepository.save(ongExistente);
    }
    public void deletarOng(Long id) {
        Ong ong = buscarOngPorId(id);
        ongRepository.delete(ong);
    }
    public Ong salvarPet(Long ongId, List<Pets> pets) {
        Ong ong = buscarOngPorId(ongId);
        for (Pets pet : pets) {
            pet.setOng(ong);
        }
        ong.getAnimais().addAll(pets);
        return ongRepository.save(ong);
    }

}
