/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calc;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import calc.model.CalcResult;
import calc.model.Operation;
import calc.service.CalcService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author july
 */
public class HistoryController extends AnchorPane {
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML
    private ListView<String> historyListView;

    private List<CalcResult> results;
    ObservableList<String> historyList = FXCollections.observableArrayList();

    private Main application;
    
    public void setApp(Main application){
        this.application = application;
    }
    
    @FXML
    public void initialize() {
        CalcService calcService = new CalcService();
        List<CalcResult> calcResults = calcService.getHistoryCalcResult(10);
        calcResults.forEach(calcResult -> {
            String line = "";
            if (calcResult.getOperation().equals("SQUAREROOT"))
                line = Operation.valueOf(calcResult.getOperation()).getOper() + calcResult.getFirstNum();
            else if (calcResult.getOperation().equals("EXPONENTIATION"))
                line = calcResult.getFirstNum() + Operation.valueOf(calcResult.getOperation()).getOper();
            else
                line = calcResult.getFirstNum() + " " + Operation.valueOf(calcResult.getOperation()).getOper()
                    + " " + calcResult.getSecondNum();
            line +=  " = " + calcResult.getResultNum();
            historyList.add(line);
        });
        historyListView.setItems(historyList);
    }


    @FXML
    void pressedCalc(ActionEvent event) {
        application.getMainStage(true);
    }
}
