package br.cesul.planejadorViagens.repository;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;

// Camada de persistencia. Interage com o mongo
//  e devolve/recebe objetos do modelo
// Não ha nenhuma manipulação /import de JXF

import br.cesul.planejadorViagens.config.MongoConfig;
import br.cesul.planejadorViagens.model.Viagem;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Sera responsavel por consultar e alteral
// os dados do mongo
// e so trabalha com objetos do tipo viagem
public class ViagemRepository {
    // Conectar com a coleção de viagens do mongo
    // Levando em consideração o CODEC POJO
    private final MongoCollection<Viagem> col =
            MongoConfig.db.getCollection("tripPlanner", Viagem.class);

    // CRUDS
    // CreateReadUpdateDelete's
    public void salvar(Viagem v){
        // v =Objeto convertido automaticamente em BSON
        col.insertOne(v);
    }

    public void atualizar(Viagem v){
        col.replaceOne(Filters.eq("_id", v.getId()), v);
    }

    public boolean conflitaExcluindoId(ObjectId ignorarId, LocalDate ini, LocalDate fim){
        long qtd = col.countDocuments(
                Filters.and(
                        Filters.lt("dataInicio", ini),
                        Filters.gt("dataFim", fim),
                        Filters.ne("_id", ignorarId)
                )
        );

        return qtd > 0;
    }

    public List<Viagem> listarTodas(){
        return col.find().sort(ascending("DataInicio"))
                // Converte o resultado em uma lista java
                .into(new ArrayList<>());
    }

    public double somaCustos(){
        // 1 - Pegar as viagens da coleção
        // 2 - somar o campo 'custo' em memória
        return col.find() // Pega as viagens
                // Converte em lista
                .into(new ArrayList<>())
                .stream() // usa a Stream API do java
                .mapToDouble(Viagem::getCusto) // Extrai o custo mapeando a vaiavel
                .sum(); // Soma tudo;
    }

    // Verificar se inicio/fim se sobrepõe a alguma viagem existentes
    public boolean conflita(LocalDate ini, LocalDate fim){
        long qtd = col.countDocuments(
                Filters.and(
                        // a viagem começa antes ou durane o intervalo
                        // "CAMPO é menor ou igual ao VALOR"
                        Filters.lte("dataInicio", ini),

                        // a viagem termina depois ou durante o inicio
                        // "CAMPO é maior ou igual ao VALOR"
                        Filters.gte("dataFim", fim)
                )
        );

        return qtd > 0;
    }

    public void delete(Viagem v) {
        col.deleteOne(eq("_id", v.getId()));
    }

}
