package br.cesul.planejadorViagens.config;

// Classe utilitaria (Singletion) que expõe um DB
// Mantem um unica conexão durante o processo

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

// Faremos suporte ao mapeamento dos POJO's
public class MongoConfig {
    private static final String URI = "mongodb://localhost:27017";

    // Cliente que abre sessaõ com o banco
    private static final MongoClient client;

    // REPRESNTA A ENTIDADE 'tripPlanner'
    public static final MongoDatabase db;

    // BLoco estatico de inicialização (executa so uma vez)
    static {
        // 1 - Criar um "codec Provider" que converte POJO's
        // Para documentos BSON automaticamente (e ao contrario tambem)
        var pojoCodec = PojoCodecProvider.builder().automatic(true).build();

        // 2 - Criar um codec registry que é uma lista de codesc que o mongo vai usar
        // - Primiero: Inclui o codec padrao do mongo(String, INT... )
        // - Depois: Incliu o codec para POJO's que criamos acima
        var settings = MongoClientSettings.builder().codecRegistry(
                CodecRegistries.fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(), // CODECS BASICOS
                        CodecRegistries.fromProviders(pojoCodec)    // NOSSO CODEC POJO CUSTMIZADO
                )
        ).applyConnectionString(new com.mongodb.ConnectionString(URI)).build();

        // Criar o cliente mongo com as configurações personalizadas
        client = MongoClients.create(settings);

        // Obter a referencia do Banco de dados que seja usado em toda a aplicação
        db = client.getDatabase("tripPlanner");
    }

    // Construtor nao instanciavel (private)
    // Impede que a classe seja instanciada
    private MongoConfig(){}
}

// Como você pode obter so dados do DB:
// MongoConfig.db.getCollection("tripPlanner", Viagem.class);
// Alem disso... Para manipular não precisa passar por serialização.
// viagens.insertOne(new Viagem));

// ASSIM PEGA TODOS: var pojoCodec = PojoCodecProvider.builder().automatic(true).build();
// Se você quiser limitar o mapeamento de atributos a um pacote especifico(por exempo)
// var pojoCodec = PojoCodecProvider.builder().register("br.cesul.planejadorViagem.model").build();