package br.cesul.expensetracker.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

// Classe Singleton que expõe um link para a data base (Faz a conexao do codigo com o mongo DB) com mapeamento automatico de pojo's
public class MongoConfig {
    // Declaracao de variaveis clientes
    private static final String URI = "mongodb://localhost:27017";
    private static final MongoClient client;
    public static final MongoDatabase db;

    // Codec de convesão dos pojo's
    // Seta as variaveis DB e CLIENT
    static {
        var pojoCodec = PojoCodecProvider.builder().automatic(true).build();

        var settings = MongoClientSettings.builder().codecRegistry(CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodec)
        )).applyConnectionString(new ConnectionString(URI)).build();

        client = MongoClients.create(settings);

        db = client.getDatabase("expensetracker");
    }

    // Construtor vazio
    private MongoConfig(){}

}
