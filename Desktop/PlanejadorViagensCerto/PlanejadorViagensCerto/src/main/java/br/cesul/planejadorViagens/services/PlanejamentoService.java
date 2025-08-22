package br.cesul.planejadorViagens.services;

// Contem validações e calculos, ISOLANDO a lógica do controller
// 0 referencias do Mongo
// 0 referencias da tela


// Serve para organizar a lógica do negócio da aplicação
// Separar responsabilidades
// Validar dados ex: Impedir datas invertidas, Custo negativo...

// Se não tivesse o service o controller teria que fazer todas as validaçõe por si
// A tela teria que chamar o banco direto (violação do MVC)
// Regras ficariam espalhadas...

import br.cesul.planejadorViagens.model.Viagem;
import br.cesul.planejadorViagens.repository.ViagemRepository;
import org.bson.internal.BsonUtil;

import java.time.LocalDate;
import java.util.List;

public class PlanejamentoService {
    private final ViagemRepository repo = new ViagemRepository();

    // Receber os dados da viagem (como se fosse da tela)
    // Adicionar uma viagem se as regras estiverem OK
    public void adicionar(String destino, LocalDate inicio, LocalDate fim, double custo){

        // Se algo estiver errado lança excessao
        // Que sera capturada pelo controller ou pela interface
        validar(destino, inicio, fim, custo);

        if(repo.conflita(inicio, fim))
            throw new IllegalArgumentException("Conflita com outra viagem");

        repo.salvar(new Viagem(null, destino, custo, inicio, fim));
    }

    public void atualizar(Viagem v, String destino, LocalDate ini, LocalDate fim, double custo){
        if(v == null || v.getId() == null){
            throw new IllegalArgumentException("Viagem Invalida");
        }

        validar(destino, ini, fim, custo);
        if(repo.conflitaExcluindoId(v.getId(), ini, fim)){
            throw new IllegalArgumentException("Conflita com outra viagem");
        }

        v.setDestino(destino);
        v.setDataInicio(ini);
        v.setDataFim(fim);
        v.setCusto(custo);
        repo.atualizar(v);
    }

    private static void validar(String destino, LocalDate inicio, LocalDate fim, double custo) {
        if(destino == null || destino.isBlank())
            throw new IllegalArgumentException("Destino Vazio");
        if(inicio == null || fim == null)
            throw new IllegalArgumentException("As datas são obrigatorias");
        if (inicio.isAfter(fim))
            throw new IllegalArgumentException("Inicio posterior ao fim");
        if (custo < 0)
            throw new IllegalArgumentException("Custo negativo");
    }

    // Metodos de consulta
    // No momento apenas encapsula o acesso ao repositorio
    // Listar

    public List<Viagem> listar(){
        return repo.listarTodas();
    }

    // Somar custos
    public double totalGasto(){
        return repo.somaCustos();
    }

    public void remover(Viagem viagemSelecionada) {
        repo.delete(viagemSelecionada);


    }
}

