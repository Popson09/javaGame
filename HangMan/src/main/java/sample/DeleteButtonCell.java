package sample;

import database.SQLCommands;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

//klasa dziedzicząca po komórce tabeli zawierająca przycisk do usuwania rekordu
public class DeleteButtonCell <S> extends TableCell<S, Void> {
    private final Button deleteButton;
    public DeleteButtonCell() {
        deleteButton = new Button("Delete");
        deleteButton.getStyleClass().add("deleteButton");
        deleteButton.setOnAction(event -> {
            // Pobierz indeks wiersza znany z fortableColumn
            int index = getIndex();
            // Pobierz obiekt z danymi z tego wiersza
            S item = getTableView().getItems().get(index);
            SQLCommands.deleteWord(item.toString());
            getTableView().getItems().remove(index);
        });
    }

    //ustawienie wyswietlanej grafiki przycisku
    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        //ustaw jeżeli wiersz nie jest pusty
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(deleteButton);
        }
    }

    // Fabryka dla kolumny z przyciskiem usuwania tworzy instancje przycisku dla każdego rekordu
    public static <S> Callback<TableColumn<S, Void>, TableCell<S, Void>> forTableColumn() {
        return param -> new DeleteButtonCell<>();
    }
}