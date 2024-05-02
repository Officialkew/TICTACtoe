package OS.Code;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.TableView;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import static java.awt.SystemColor.window;

/**
 * Created by 1500411 on 11/2/2017.
 */
/**
 * use array list to organize processes
 * used system array list with the assumption we were not restricted
 *
 * */
public class ProcessControlBlock {

    //declare variables
    private int processID;
    private String processType; //utility or application
    private String applicationName;
    private String CpuTime;
    private String startTime;
    private String endTime;
    private String timeStartCount;
    private int windonum;
    private static int count = 0;

    private static ArrayList<ProcessControlBlock> processList = new ArrayList<>();
    private static ArrayList<TictTacToeFrame> frameList = new ArrayList<>();


    // Default Constructor
    public ProcessControlBlock() {
        this.processID = 0;
        this.CpuTime = "";
        this.startTime = "";
        this.endTime = "";
        this.applicationName = this.processType;
        this.timeStartCount = "";

    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    // Primary Constructor
    public ProcessControlBlock(int processID, String processType, String applicationName, String cpuTime, String startTime, String endTime, String timeStartCount, int windonum) {
        this.processID = processID;
        this.processType = processType;
        this.applicationName = applicationName;
        this.CpuTime = cpuTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeStartCount = timeStartCount;
        this.windonum = windonum;

    }

    // Accessors and Mutators
    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public final int getProcessID() {
        return this.processID;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public final String getProcessType() {
        return this.processType;
    }


    public String getCpuTime() {
        return CpuTime;
    }

    public void setCpuTime(String cpuTime) {
        CpuTime = cpuTime;
    }

    public String getTimeStartCount() {
        return timeStartCount;
    }

    public void setTimeStartCount(String timeStartCount) {
        this.timeStartCount = timeStartCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getWindonum() {
        return windonum;
    }

    public void setWindonum(int windonum) {
        this.windonum = windonum;
    }

    public void AddProcess() {

    }

    public final int CreateProcess(String processName, String processType) throws InterruptedException {
        int processId = GenerateId();/**method created to generate id*/
        ProcessControlBlock process = new ProcessControlBlock(processId, processType, processName, CpuTimeCal(), GetTime(), "", Long.toString(System.currentTimeMillis()), count);
        TictTacToeFrame frame = new TictTacToeFrame(process.getProcessID(), new TicTacToe());
        frameList.add(frame);
        processList.add(process);
        return processId;
    }


    public int GenerateId() {

        return Integer.parseInt(String.format("%05d", new SecureRandom().nextInt(100000)));

    }
/**getting system time to calculate cpu time,start,end time*/
    public final String GetTime() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat timestring = new SimpleDateFormat("H:m:s ");
        return timestring.format(now.getTime());
    }

    public final String CpuTimeCal() {
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        return (Long.toString(TimeUnit.MILLISECONDS.toSeconds(endTime - startTime)));
    }

    public final void ProcessManager() {
        long endTime = System.currentTimeMillis();
        System.out.format("%10s%15s%15s%15s%15s", "Process ID", "Process Type", "Process Name", "Time Started", "End Time\n");

        for (ProcessControlBlock process : processList) {
            System.out.format("%10d%15s%15s%15s%20s", process.getProcessID(), process.getProcessType(), process.getApplicationName(), process.getStartTime(), process.getEndTime() + "\n");
        }

    }
//Kill process by ID
    public final ProcessControlBlock KillProcess(int processID) {
        int endtime = (int) System.currentTimeMillis();
        ProcessControlBlock proc = null;
        for (Iterator<ProcessControlBlock> itr = processList.iterator(); itr.hasNext(); ) {
            ProcessControlBlock process = itr.next();
            if (process.getProcessID() == processID) {
                process.setCpuTime(Long.toString(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - Long.parseLong(process.getTimeStartCount()))) + "s");
                process.setEndTime(GetTime());
                processList.remove(process);
                proc = process;
                break;
            }

        }
//Removes Window based on ID
        for (Iterator<TictTacToeFrame> itr = frameList.iterator(); itr.hasNext(); ) {
            TictTacToeFrame frame = itr.next();
            if (frame.getId() == processID) {
                frame.getFrame().dispose();
                frameList.remove(frame);
                break;
            }


        }
        return proc;
    }
    public final void KillAll() {

        for (Iterator<ProcessControlBlock> itr = processList.iterator(); itr.hasNext(); ) {
            ProcessControlBlock process = itr.next();
            if (process.getProcessID()!=0)
          processList.remove(process);
            break;
        }
        for (Iterator<TictTacToeFrame> itr = frameList.iterator(); itr.hasNext(); ) {
            TictTacToeFrame frame = itr.next();
            if(frame.getId()!=0)
        frame.getFrame().dispose();
        frameList.remove(frame);
        break;

        }
    }

// save process
    public void Save(ProcessControlBlock process) throws FileNotFoundException {
        String fileName = "ProcessHistory.txt";
        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
        pw.format("%10s%15s%15s%15s%20s%20s", "Process ID", "Process Type", "Process Name", "Cpu Time", "Start Time", "End Time\n");
        pw.format("%10d%15s%15s%15s%20s%20s", process.getProcessID(), process.getProcessType(), process.getApplicationName(), process.getCpuTime(), process.getStartTime(), process.getEndTime());
        pw.close();
    }

    public void SaveCurrentGame(int processID) {
        for (Iterator<TictTacToeFrame> itr = frameList.iterator(); itr.hasNext(); ) {
            TictTacToeFrame frame = itr.next();
            if (frame.getId() == processID) {
                frame.getFrame().setVisible(false);
                break;
            }
        }

    }

    public void ResumeGame(int processID) {
        for (Iterator<TictTacToeFrame> itr = frameList.iterator(); itr.hasNext(); ) {
            TictTacToeFrame frame = itr.next();
            if (frame.getId() == processID) {
                frame.getFrame().setVisible(true);
                break;
            }
        }

    }

    public void ShowManagerGui()throws IOException {

        javafx.scene.control.TableView<ProcessControlBlock> processTable;
        ObservableList<ProcessControlBlock> process = FXCollections.observableArrayList(processList);
        TableColumn<ProcessControlBlock, String> processIdColumn = new TableColumn<>("Process ID");
        processIdColumn.setMinWidth(150);
        processIdColumn.setCellValueFactory(new PropertyValueFactory<>("ProcessID"));


        TableColumn<ProcessControlBlock, String> processTypeColumn = new TableColumn<>("Process Type");
       processTypeColumn.setMinWidth(150);
        processTypeColumn.setCellValueFactory(new PropertyValueFactory<>("ProcessType"));

        TableColumn<ProcessControlBlock, String> processAppColumn = new TableColumn<>("Application Name");
       processAppColumn.setMinWidth(150);
        processAppColumn.setCellValueFactory(new PropertyValueFactory<>("ApplicationName"));

        TableColumn<ProcessControlBlock, String> processStartColumn = new TableColumn<>("Start Time");
       processStartColumn.setMinWidth(150);
        processStartColumn.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        TableColumn<ProcessControlBlock, String> processEndColumn = new TableColumn<>("End Time");
      processEndColumn.setMinWidth(150);
       processEndColumn.setCellValueFactory(new PropertyValueFactory<>("EndTime"));




        processTable = new javafx.scene.control.TableView<>();
        processTable.setItems(process);
        processTable.getColumns().addAll(processIdColumn,processTypeColumn,processAppColumn,processStartColumn,processEndColumn);

        VBox vBox = new VBox();
       vBox.getChildren().addAll(processTable);
        Scene scene = new Scene(vBox);
        Stage processes =new Stage();
       processes.setScene(scene);
        processes.setResizable(false);
        processes.show();

    }
}
