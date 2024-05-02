package OS.Code;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by Kaheem on 11/16/2017.
 */
public class FileChecker {

    //declare variables
    private int index;
    private int processId;
    private int allocation;
    private int blockSize;
    private String state;
    public static int count;
    public static ArrayList<FileChecker> FileManagerList = new ArrayList<>();

    //default constructor
    public FileChecker() {
    }

    //primary constructor
    public FileChecker(int index, int processId, int allocation, int blockSize, String state) {
        this.index = index;
        this.processId = processId;
        this.allocation = allocation;
        this.blockSize = blockSize;
        this.state = state;

    }
//getters and setters
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getAllocation() {
        return allocation;
    }

    public void setAllocation(int allocation) {
        this.allocation = allocation;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }
//initialize file block
    public void initializeFileManager() {
        for (int i = 1; i <= 100; i++) {
            FileChecker file = new FileChecker(i, processId, 0, 10, "");
            FileManagerList.add(file);
        }

    }
//display
    public void ShowFileBlocks() {
        System.out.format("%10s%15s%15s%18s%15s", "Index", "ProcessID", "Allocation", "block Size", "State\n");

        for (FileChecker fileblock : FileManagerList) {
            System.out.format("%10d%15d%15d%15d%1s%15s", fileblock.getIndex(), fileblock.getProcessId(), fileblock.getAllocation(), fileblock.getBlockSize(), "mb", fileblock.getState() + "\n");
        }


    }
//how much memory used and left
    public int CheckMemory() {
        int memorySum = 0;
        for (Iterator<FileChecker> itr = FileManagerList.iterator(); itr.hasNext(); ) {
            FileChecker files = itr.next();
            memorySum += files.getBlockSize();
        }
        return memorySum;
    }
//
    public boolean AssignFile(ListIterator<FileChecker> itr, int processId, int processSize, String state) {
        int checkVal = processSize / 10;
        for (; itr.hasNext(); )

        {
            FileChecker file = itr.next();


            if (checkVal == count) {
                return true;
            }
            if (file.getAllocation() == 0 && file.getBlockSize() > 0) {
                //System.out.println(processId + "  " + file.getIndex());
                file.setAllocation(1);
                file.setProcessId(processId);
                file.setState(state);
                file.setBlockSize(file.getBlockSize() - 10);
                count++;

            }


        }
        return false;
    }
    public void allocate(int processId, int processSize, String state) throws IllegalStateException {

        if (CheckMemory() < processSize) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Insufficient Memory!!!");
            alert.setHeaderText("You Have Insufficient Memory to start this application");
            alert.setContentText("Please try at a later time  Current Memory: " + Integer.toString(CheckMemory()) + "mb");

            alert.showAndWait();

        } else {
// be able to go back to start of the list when it reaches the end
            while (true) {
                ListIterator<FileChecker> itr = FileManagerList.listIterator(new Random().nextInt(99 - 0) + 0);

                if (AssignFile(itr, processId, processSize, state)) {
                    count = 0;
                    break;
                }
            }
        }
    }
    public void DeAllocateMemory(int processID) {

        for (Iterator<FileChecker> itr = FileManagerList.iterator(); itr.hasNext(); ) {
            FileChecker file = itr.next();

            if (file.getProcessId() == processID) {
                file.setProcessId(0);
                file.setAllocation(0);
                file.setBlockSize(10);
                file.setState("");

            }
        }
    }



    public void ShowManagerGui() throws IOException {
        //fileblock.getIndex(), fileblock.getProcessId(), fileblock.getAllocation(), fileblock.getBlockSize(),"mb",fileblock.getState()+"\n");
        javafx.scene.control.TableView<FileChecker> FileTable;
        ObservableList<FileChecker> Filemem = FXCollections.observableArrayList(FileManagerList);
        TableColumn<FileChecker, String> processIndexColumn = new TableColumn<>("Index");
        processIndexColumn.setMinWidth(150);
        processIndexColumn.setCellValueFactory(new PropertyValueFactory<>("Index"));

        TableColumn<FileChecker, String> processIdColumn = new TableColumn<>("Process ID");
        processIdColumn.setMinWidth(150);
        processIdColumn.setCellValueFactory(new PropertyValueFactory<>("ProcessId"));

        TableColumn<FileChecker, String> processAllocation = new TableColumn<>("Allocation");
        processAllocation.setMinWidth(150);
        processAllocation.setCellValueFactory(new PropertyValueFactory<>("Allocation"));

        TableColumn<FileChecker, String> fileblocksize = new TableColumn<>("Block Size(mb)");
        fileblocksize.setMinWidth(150);
        fileblocksize.setCellValueFactory(new PropertyValueFactory<>("BlockSize"));

        TableColumn<FileChecker, String> filestate = new TableColumn<>("State");
        filestate.setMinWidth(150);
        filestate.setCellValueFactory(new PropertyValueFactory<>("State"));


        Label memoryUsage = new Label("\nMemory Used:" + (1000 - CheckMemory() + "mb") + "\t" + "Memory Available: " + CheckMemory() + "mb");
        Button exit = new Button("Exit");

        FileTable = new javafx.scene.control.TableView<>();
        FileTable.setItems(Filemem);
        FileTable.getColumns().addAll(processIndexColumn, processIdColumn, processAllocation, fileblocksize, filestate);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(FileTable, memoryUsage, exit);
        Scene scene = new Scene(vBox);
        Stage processes = new Stage();
        processes.setScene(scene);
        processes.setResizable(false);
        processes.show();
        exit.setOnAction(event -> processes.close());

    }
}

