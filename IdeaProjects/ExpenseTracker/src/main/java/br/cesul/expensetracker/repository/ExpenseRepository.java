package br.cesul.expensetracker.repository;

import br.cesul.expensetracker.config.MongoConfig;
import br.cesul.expensetracker.model.Expense;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static com.mongodb.client.model.Sorts.descending;
public class ExpenseRepository {
    private final MongoCollection<Expense> col = MongoConfig.db.getCollection("expenses", Expense.class);

    public ObservableList<Expense> fundAll(){
        var list = FXCollections.<Expense>observableArrayList();
        col.find().sort(descending("date")).forEach(list::add);
        return list;
    }

    public void inser(Expense e){
        col.insertOne(e);
    }

    public void delete(Expense e){
        col.deleteOne(Filters.eq("_id", e.getId()));
    }
}
