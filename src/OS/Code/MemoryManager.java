package OS.Code;
import OS.Code.ProcessControlBlock;
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

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
//import java.util.Scanner;
//
//import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;
//import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.registerDefaultResolvers;

/**
 * Created by Kaheem on 11/14/2017.
 */
public class MemoryManager {
    private String Check;
    private int memorySize;
    private String memoryName;
    private int memoryIndex;
    private static ArrayList<MemoryManager> memoryManagerArrayList = new ArrayList<>();
    private static ArrayList<processMemManager> processMemManagerArrayList = new ArrayList<>();
  public static int lastIndex = 1;

    public MemoryManager(int memorySize, String memoryName, int memoryIndex) {
        this.memorySize = memorySize;
        this.memoryName = memoryName;
        this.memoryIndex = memoryIndex;

    }

    public MemoryManager() {

    }

    public int getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    public String getMemoryName() {
        return memoryName;
    }

    public void setMemoryName(String memoryName) {
        this.memoryName = memoryName;
    }

    public int getMemoryIndex() {
        return memoryIndex;
    }

    public void setMemoryIndex(int memoryIndex) {
        this.memoryIndex = memoryIndex;


    }

    public class processMemManager {
        private String processName;
        private String blockName;
        private int processID;
        private int processSize;
        private String type;

        public processMemManager(String processName, String blockName, int processID,int processSize,String type) {
            this.processName = processName;
            this.blockName = blockName;
            this.processID = processID;
            this.processSize=processSize;
            this.type=type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getProcessSize() {
            return processSize;
        }

        public void setProcessSize(int processSize) {
            this.processSize = processSize;
        }

        public int getProcessID() {
            return processID;
        }

        public void setProcessID(int processID) {
            this.processID = processID;
        }

        public processMemManager() {
        }

        public String getProcessName() {
            return processName;
        }

        public void setProcessName(String processName) {
            this.processName = processName;
        }

        public String getBlockName() {
            return blockName;
        }

        public void setBlockName(String blockName) {
            this.blockName = blockName;
        }
    }

    public void initializeMemory() {
        ProcessControlBlock process=new ProcessControlBlock();
        MemoryManager Block_A = new MemoryManager(350, "A", 1);
        MemoryManager Block_B = new MemoryManager(400, "B", 2);
        MemoryManager Block_C = new MemoryManager(250, "C", 3);
        memoryManagerArrayList.add(Block_A);
        memoryManagerArrayList.add(Block_B);
        memoryManagerArrayList.add(Block_C);

        //start.ShowManager();
        AllocateMemory(245,"Memory Manager",process.GenerateId(),"Utility");
        AllocateMemory(150,"Process Manager",process.GenerateId(),"Utility");
        AllocateMemory(200,"File Manager",process.GenerateId(),"Utility");

    }

    public int CheckMemory() {
        int memorySum = 0;
        for (Iterator<MemoryManager> itr = memoryManagerArrayList.iterator(); itr.hasNext(); ) {
            MemoryManager memory = itr.next();
            memorySum += memory.getMemorySize();
        }
        return memorySum;
    }

    public void AllocateMemory(int processSize, String processName, int processID,String type) throws IllegalStateException {

        if (CheckMemory() < processSize) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Insufficient Memory!!!");
            alert.setHeaderText("You Have Insufficient Memory to start this application");
            alert.setContentText("Please try at a later time  Current Memory: "+Integer.toString(CheckMemory())+"mb");

            alert.showAndWait();

        } else {

            while (true) {
                ListIterator<MemoryManager> itr = memoryManagerArrayList.listIterator();

                if (assign(itr, processSize, processName, processID, type)) {
                    break;
                }
            }
        }
    }

    private boolean assign(ListIterator<MemoryManager> itr, int processSize, String processName, int processID, String type) {
        for (; itr.hasNext();) {
            MemoryManager memory = itr.next();

            if (lastIndex == 3 && memory.getMemoryName().equals("C") && memory.getMemorySize() == 0) {
                lastIndex = 1;
            }

            if (lastIndex == memory.getMemoryIndex() && memory.getMemorySize() < processSize) {
                lastIndex = memory.getMemoryIndex() + 1;
                continue;
            }

            if (memory.getMemorySize() >= processSize && lastIndex == memory.getMemoryIndex()) {
                processMemManagerArrayList.add(new processMemManager(processName, memory.getMemoryName(), processID, processSize, type));
                memory.setMemorySize(memory.getMemorySize() - processSize);

                lastIndex = memory.getMemoryIndex();
                return true;
            }
        }

        return false;
    }



    public int DeAllocateMemory(int processID) {
       String deleteID="no";
        for (Iterator<processMemManager> itr = processMemManagerArrayList.iterator(); itr.hasNext(); ) {
            processMemManager process = itr.next();

            if (process.getProcessID() == processID && process.getType().equals("Application")) {
                System.out.println(process.getType());
                deleteID = process.getBlockName();
                processMemManagerArrayList.remove(process);
                break;
            }


        }
        for (Iterator<MemoryManager> itr = memoryManagerArrayList.iterator(); itr.hasNext(); ) {
            MemoryManager memory = itr.next();
            if (memory.getMemoryName().equals(deleteID))
            {
             memory.setMemorySize(memory.memorySize+ 50);
            }

        }
       if (!deleteID.equals("no"))
        {
        return 1;}
        else
        {
            return 0;
        }
    }
    public void ShowManager()
    {
        for (int i=1; i<=20; i++)
            System.out.println("\n");
            System.out.println("\f");

        System.out.format("%10s%15s%15s%18s%15s", "Process ID", "Process Name", "Block","Memory Used","Type\n");

        for (processMemManager process : processMemManagerArrayList) {
            System.out.format("%10d%15s%15s%15d%1s%15s", process.getProcessID(), process.getProcessName(),process.getBlockName(),process.getProcessSize(),"mb",process.getType()+ "\n");
        }

        System.out.println("\nMemory Used:"+(1000-CheckMemory()+"mb")+"\t"+"Memory Available: "+CheckMemory()+"mb");

    }

    public void ShowManagerGui()throws IOException {

        javafx.scene.control.TableView<processMemManager> MemoryTable;
        ObservableList<processMemManager> processMem = FXCollections.observableArrayList(processMemManagerArrayList);
        TableColumn<processMemManager, String> processIdColumn = new TableColumn<>("Process ID");
        processIdColumn.setMinWidth(150);
        processIdColumn.setCellValueFactory(new PropertyValueFactory<>("ProcessID"));


        TableColumn<processMemManager, String> processNameColumn = new TableColumn<>("Process Name");
       processNameColumn.setMinWidth(150);
       processNameColumn.setCellValueFactory(new PropertyValueFactory<>("ProcessName"));

        TableColumn<processMemManager, String> processBlockColumn = new TableColumn<>("Block Name");
        processBlockColumn.setMinWidth(150);
      processBlockColumn.setCellValueFactory(new PropertyValueFactory<>("BlockName"));

        TableColumn<processMemManager, String> processMemColumn = new TableColumn<>("Memory Used(MB)");
        processMemColumn.setMinWidth(150);
        processMemColumn.setCellValueFactory(new PropertyValueFactory<>("ProcessSize"));

        TableColumn<processMemManager, String> processtypeColumn = new TableColumn<>("Type");
        processtypeColumn.setMinWidth(150);
        processtypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));



Label memoryUsage= new Label("\nMemory Used:"+(1000-CheckMemory()+"mb")+"\t"+"Memory Available: "+CheckMemory()+"mb");
Button exit= new Button("Exit");

       MemoryTable = new javafx.scene.control.TableView<>();
       MemoryTable.setItems(processMem);
        MemoryTable.getColumns().addAll(processIdColumn,processNameColumn,processBlockColumn,processMemColumn,processtypeColumn);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(MemoryTable,memoryUsage,exit);
        Scene scene = new Scene(vBox);
        Stage processes =new Stage();
        processes.setScene(scene);
        processes.setResizable(false);
        processes.show();
        exit.setOnAction(event ->processes.close() );

    }

    }


