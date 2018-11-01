import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;


public class Main extends Application {
    public static void main(String[] args) { launch(args); }
    public void start(Stage primaryStage){



        //VARIABLES
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Exam2");

        Menu image = new Menu("Image");
        Menu graphique = new Menu("Graphique");
        MenuItem load = new MenuItem("Charger une image");
        MenuItem add = new MenuItem("Ajouter une résultat");
        image.getItems().addAll(load);
        graphique.getItems().addAll(add);
        MenuBar menuBar = new MenuBar(image,graphique);

        BorderPane section = new BorderPane();
        ImageView imageView=new ImageView();


        //defining the axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Kilométres");
        //creating the chart
        final LineChart<String,Number> graph =
                new LineChart<String,Number>(xAxis,yAxis);
        graph.setTitle("Progression de la forme physique");
        //adding the data
        XYChart.Series series = new XYChart.Series();
        series.setName("Ma progression");
        graph.getData().addAll(series);



        Tab tab = new Tab("Image",imageView );
        tab.setClosable(false);
        Tab tab2 = new Tab("Graphique",graph);
        tab2.setClosable(false);
        TabPane root = new TabPane(tab, tab2);


        section.setTop(menuBar);
        section.setCenter(root);

        Scene sc1=new Scene(section);


        //ACTIONS

        load.setOnAction((event) ->{
            Image ima=new Image(filechoosing(primaryStage).toURI().toString());
            imageView.setImage(ima);
        });
        add.setOnAction((event) ->{
            TextInputDialog alerte = new TextInputDialog();
            alerte.setTitle("Date");
            alerte.setHeaderText("Entrez la date au format JJ/MM");
            String resultat = alerte.showAndWait().get();

            TextInputDialog alerte2 = new TextInputDialog();
            alerte2.setTitle("Kilomètres");
            alerte2.setHeaderText("Entrez le nombre de kilomètres (utilisez le point comme séparateur)");
            String resultat2 = alerte2.showAndWait().get();

            series.getData().add(new XYChart.Data(resultat, Integer.parseInt(resultat2)));
        });

        sc1.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.G) {
                add.fire();
            }
            else if (event.getCode() == KeyCode.I){
               load.fire();
            }
        });

        //SHOW
        primaryStage.setScene(sc1);
        primaryStage.show();
    }

    public static File filechoosing(Stage stage){

        FileChooser fc = new FileChooser();
        fc.setTitle("Veuillez sélectionner une image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Image", "*.jpg","*.png")
        );
        File fichier = fc.showOpenDialog(stage);
        return fichier;
    }



}