package OS.Controllers;

/**
 * Created by Kaheem on 10/19/2017.
 */

import OS.Code.*;
import javafx.event.Event;
import javafx.fxml.FXML;
//import OS.Code.ProcessControlBlock;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.InputMismatchException;
//import java.util.ResourceBundle;
//import java.net.URL;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;
import static java.lang.Integer.*;
import static javafx.scene.input.DataFormat.URL;

public class MainmenuController {
    MemoryManager allocate =new MemoryManager();
    ProcessControlBlock process = new ProcessControlBlock();
    FileChecker file= new FileChecker();

    @FXML
    private ImageView mainview;

    @FXML
    private AnchorPane appdraw;
    @FXML
    private AnchorPane main;
    @FXML
    private TextField inputCmd;

    @FXML
    private ImageView btntictac;

    @FXML
    private ImageView btnmem;

    @FXML
 private ImageView btnfile;

    @FXML
    private ImageView btnappdraw;

    @FXML
    private ImageView btnprocess;

    @FXML

    private void btnappdraw(MouseEvent event) throws IOException, InterruptedException {

    }
    @FXML
    private void btnfile(MouseEvent event) throws IOException, InterruptedException {
     file.ShowManagerGui();
    }

    public void btntictac(MouseEvent event)throws IOException, InterruptedException {
        if (!(allocate.CheckMemory()<50)) {
            int processId = process.CreateProcess("TICTAC", "Application");
            allocate.AllocateMemory(50, "TICTAC", processId, "Application");
            file.allocate(processId, 50, "TT-Running");
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Insufficient Memory!!!");
            alert.setHeaderText("You Have Insufficient Memory to start this process");
            alert.setContentText("Please try at a later time  Current Memory: "+Integer.toString(allocate.CheckMemory())+"mb");

            alert.showAndWait();
        }
    }
    public void btnprocess(MouseEvent event)throws IOException, InterruptedException{

        process.ProcessManager();
        ProcessControlBlock show=new ProcessControlBlock();
        show.ShowManagerGui();
    }
    public void btnmem(MouseEvent event)throws IOException, InterruptedException {
        allocate.ShowManager();
        MemoryManager show =new MemoryManager();
        show.ShowManagerGui();
    }

    public void goback(MouseEvent event) {

        //process.KillAll();

    }

    public void inputCmd(ActionEvent ae) throws Exception {

        String cmd = inputCmd.getText().toString().toUpperCase();
        String[] command = cmd.split(" ");

        if (command[0].equals("Kill".toUpperCase()) && command[1].equals("All")) {

            process.KillAll();

        }

        if (command[0].equals("Start".toUpperCase()) && command[1].equals("TICTAC")) {
            if (!(allocate.CheckMemory()<50)) {
                int processId = (process.CreateProcess(command[1], "Application"));

                allocate.AllocateMemory(50, "TICTAC", processId, "Application");
                file.allocate(processId, 50, "TT-Running");
            }
            else{
                JOptionPane.showMessageDialog(null, "Cannot Start Process: No Memory", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }


        if ((command[0].equals("Get".toUpperCase()) && command[1].equals("PM"))) {

            process.ProcessManager();
            ProcessControlBlock show=new ProcessControlBlock();
            show.ShowManagerGui();



        }
        if ((command[0].equals("Get".toUpperCase()) && command[1].equals("MM"))) {
               allocate.ShowManager();
            MemoryManager show =new MemoryManager();
            show.ShowManagerGui();


        }
        if (command[0].equals("pkill".toUpperCase()) && command[1] != null) {
            try {
                if (allocate.DeAllocateMemory(Integer.parseInt(command[1]))==1) {
                    ProcessControlBlock processRecieved = process.KillProcess(parseInt(command[1]));
                    allocate.DeAllocateMemory(parseInt(command[1]));
                    process.Save(processRecieved);
                    file.DeAllocateMemory(parseInt(command[1]));


                }
                else{
                    JOptionPane.showMessageDialog(null, "This is a Utility Process and It cannot be stopped", "Error", JOptionPane.INFORMATION_MESSAGE);
                }


            } catch (InputMismatchException exception)
            //Add import java.util.InputMismatchException; at the top
            {

                System.out.println("Error - Enter a integer");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NumberFormatException ex) { // handle your exception

                JOptionPane.showMessageDialog(null, "Error - Please enter a valid process ID", "Error", JOptionPane.INFORMATION_MESSAGE);

            } catch (NullPointerException ex) { // handle your exception

                JOptionPane.showMessageDialog(null, "Error - That process ID is not Valid", "Error", JOptionPane.INFORMATION_MESSAGE);
            } catch (ArrayIndexOutOfBoundsException ex) { // handle your exception

                JOptionPane.showMessageDialog(null, "Error - That process ID is not Valid", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            inputCmd.clear();

        }
        if (command[0].equals("pause".toUpperCase()) && command[1] != null) {
            try {
                process.SaveCurrentGame(parseInt(command[1]));


            } catch (InputMismatchException exception)
            //Add import java.util.InputMismatchException; at the top
            {

                System.out.println("Error - Enter a integer");
            } catch (NumberFormatException ex) { // handle your exception

                JOptionPane.showMessageDialog(null, "Error - Please enter a valid process ID", "Error", JOptionPane.INFORMATION_MESSAGE);

            } catch (NullPointerException ex) { // handle your exception

                JOptionPane.showMessageDialog(null, "Error - That process ID is not Valid", "Error", JOptionPane.INFORMATION_MESSAGE);
            } catch (ArrayIndexOutOfBoundsException ex) { // handle your exception

                JOptionPane.showMessageDialog(null, "Error - That process ID is not Valid", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

        }

     if (command[0].equals("resume".toUpperCase()) && command[1] != null) {
        try {
            process.ResumeGame(parseInt(command[1]));


        } catch (InputMismatchException exception)
        //Add import java.util.InputMismatchException; at the top
        {

            System.out.println("Error - Enter a integer");
        } catch (NumberFormatException ex) { // handle your exception

            JOptionPane.showMessageDialog(null, "Error - Please enter a valid process ID", "Error", JOptionPane.INFORMATION_MESSAGE);

        } catch (NullPointerException ex) { // handle your exception

            JOptionPane.showMessageDialog(null, "Error - That process ID is not Valid", "Error", JOptionPane.INFORMATION_MESSAGE);
        } catch (ArrayIndexOutOfBoundsException ex) { // handle your exception

            JOptionPane.showMessageDialog(null, "Error - That process ID is not Valid", "Error", JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
}






