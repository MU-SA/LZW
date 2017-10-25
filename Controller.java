package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;


/**
 *
 */
public class Controller {
    public ListView<String> sd;

    LZ77Compressor lz = new LZ77Compressor();
    File file;
    String decompressed = "";
    ArrayList<String> compressed = new ArrayList<>();


    public void btnChoose(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("D:\\"));
        // fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files",".txt"));
        file = fc.showOpenDialog(null);


    }


    public void btnTwo(ActionEvent actionEvent) {
        if(!decompressed.equals("")){
            sd.getItems().removeAll(decompressed);
        }
        compressed = lz.compress(file.getAbsolutePath());
        sd.getItems().addAll(compressed);
    }

    public void decompress(ActionEvent actionEvent) {
        decompressed = lz.decompress(compressed);
        sd.getItems().removeAll(compressed);
        sd.getItems().addAll(decompressed);
    }
}
