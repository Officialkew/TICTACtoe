package OS.Code;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import OS.Code.FileChecker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static javafx.application.Application.launch;

/**
 * Created by Kaheem on 10/19/2017.
 */
public class Driver extends Application {
@Override
    public void start(Stage stage)  {
        try {


            Parent root = FXMLLoader.load(getClass().getResource("../Designs/Home_Screen.fxml"));
              Scene scene = new Scene(root, 287, 479);

            stage.setResizable(false);
              stage.setAlwaysOnTop(false);
            stage.setScene(scene);

            stage.setResizable(false);
            stage.show();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws IOException, InterruptedException {
        PrintWriter fileWriter = new PrintWriter("ProcessHistory.txt");

       FileChecker file = new FileChecker();
       file.initializeFileManager();
       //
        // file.ShowFileBlocks();

        ProcessControlBlock process=new ProcessControlBlock();
        file.allocate(process.GenerateId(),100,"MM-Running");
        file.allocate(process.GenerateId(),200,"PM-Running");
        file.allocate(process.GenerateId(),150,"FM-Running");


    MemoryManager start=new MemoryManager();
       start.initializeMemory();


            launch(args);

        }



    }

