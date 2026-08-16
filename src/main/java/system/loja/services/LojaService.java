package system.loja.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import system.loja.model.Loja;
import system.loja.repositories.LojaRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final LojaRepository lojaRepository;

    @Transactional
    public Loja salvarLoja(Loja loja) {
        if (loja.getNome() == null || loja.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome da loja não pode ser vazio");
        }
        if (loja.getCnpj() == null || loja.getCnpj().isBlank()) {
            throw new IllegalArgumentException("CNPJ da loja não pode ser vazio");
        }
        return lojaRepository.save(loja);
    }

    public Loja buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da loja não pode ser nulo");
        }
        return lojaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loja não encontrada"));
    }

    public List<Loja> buscarTodas() {
        return lojaRepository.findAll();
    }

    @Transactional
    public void deletarLoja(Long id) {
        Loja loja = buscarPorId(id);
        lojaRepository.delete(loja);
    }
}
