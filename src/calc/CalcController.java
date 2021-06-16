/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import calc.model.CalcResult;
import calc.model.Operation;
import calc.service.CalcService;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 *
 * @author july
 */
public class CalcController extends AnchorPane {
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private TextField txtResult;

    private String tempNum = "";
    private CalcResult result;
    private static final Integer FONT_DEFAULT = 36;
    private CalcService calcService;

    private Main application;
    
    public void setApp(Main application){
        this.application = application;
    }
    
    @FXML
    public void initialize() {
        calcService = new CalcService();
        result = new CalcResult();
    }

    @FXML
    void pressedNum(ActionEvent event) {
        //:todo проверка на количество знаков после запятой?
        tempNum += ((Button)event.getSource()).getText();
        if (tempNum.equals("00")) tempNum = "0";
        setText(tempNum);
    }

    @FXML
    void pressedDelimiter(ActionEvent event) {
        tempNum += tempNum.length() == 0 ? "0." : ".";
        setText(tempNum);
    }

    @FXML
    void pressedClear(ActionEvent event) {
        result = new CalcResult();
        tempNum = "";
        setText(tempNum);
    }

    @FXML
    void pressedBackspace(ActionEvent event) {
        if (tempNum == null || tempNum.length() == 0) return;
        tempNum = tempNum.substring(0, tempNum.length()-1);
        setText(tempNum);
    }

    @FXML
    void pressedOperation(ActionEvent event) {
        if (((Button)event.getSource()).getId().equals("SUBTRACTION") && tempNum.equals("")) {
            tempNum = "-";
            setText(tempNum);
        } else {
            result.setOperation(((Button) event.getSource()).getId());
            fillResultCalc();
        }
    }

    @FXML
    void pressedHistory(ActionEvent event) {
        application.getMainStage(false);
    }

    @FXML
    void pressedCalculate(ActionEvent event) {
        fillResultCalc();
        if (result.getSecondNum() != null || result.getOperation().equals(Operation.SQUAREROOT.name())) {
            execResult();
            saveResult();
        }
    }

    private void setText(String text) {
        text = text.replaceFirst("0*$", "");
        text = text.replaceFirst("\\.$", "");
        txtResult.setText(text);
        setNewFont(text);
    }

    private void fillResultCalc() {
        //:todo порверка на большое число
        if (tempNum == null || tempNum.length() == 0) return;
        if (result.getOperation().equals(Operation.DIVISION.name()) && result.getFirstNum() != null
            && Double.parseDouble(tempNum) == 0.0) {
            setTooltip("На 0 делить нельзя!");
            return;
        }
        if (result.getOperation().equals(Operation.SQUAREROOT.name())
                && Double.parseDouble(tempNum) < 0.0) {
            setTooltip("Нельзя извлечь корень из отрицотельного числа!");
            return;
        }
        if (result.getFirstNum() == null) {
            result.setFirstNum(new BigDecimal(tempNum));
            if (result.getOperation().equals(Operation.EXPONENTIATION.name())) {
                result.setSecondNum(new BigDecimal(tempNum));
            }
        } else {
            if (!result.getOperation().equals(Operation.SQUAREROOT.name())) {
                result.setSecondNum(new BigDecimal(tempNum));
            }
        }
        if (result.getResultNum() != null) {
            result.setFirstNum(result.getResultNum());
        }
        tempNum = "";
    }

    private void execResult() {
        //:todo порверка на большое число - отдельный метод
        switch (Operation.valueOf(result.getOperation())) {
            case ADDITION:
                result.setResultNum(result.getFirstNum().add(result.getSecondNum()));
                break;
            case SUBTRACTION:
                result.setResultNum(result.getFirstNum().subtract(result.getSecondNum()));
                break;
            case MULTIPLICATION:
            case EXPONENTIATION:
                result.setResultNum(result.getFirstNum().multiply(result.getSecondNum()));
                break;
            case DIVISION:
                result.setResultNum(result.getFirstNum().divide(result.getSecondNum(), 20, RoundingMode.HALF_UP));
                break;
            case SQUAREROOT:
                result.setResultNum(sqrt(result.getFirstNum()));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Operation.valueOf(result.getOperation()));
        }
        if (result.getResultNum() != null) {
            setText(result.getResultNum().toString());
        } else {
            result = new CalcResult();
            tempNum = "";
            setText(tempNum);
            setTooltip("Ошибка");
        }
    }

    private void saveResult() {
        result.setResultDate(Calendar.getInstance().getTime());
        calcService.saveCalcResult(result);
        result = new CalcResult();
    }

    private BigDecimal sqrt(BigDecimal value) {
        BigDecimal x = new BigDecimal(Math.sqrt(value.doubleValue()));
        return x.add(new BigDecimal(value.subtract(x.multiply(x)).doubleValue() / (x.doubleValue() * 2.0)));
    }

    private void setTooltip(String message) {
        Tooltip tooltip = new Tooltip(message);
        tooltip.setFont(Font.font(14));
        tooltip.setTextAlignment(TextAlignment.CENTER);
        tooltip.show(txtResult, txtResult.getScene().getWindow().getX(),
                txtResult.getScene().getWindow().getY() + (txtResult.getHeight() / 1.5));
        PauseTransition pt = new PauseTransition(Duration.millis(1500));
        pt.setOnFinished(e->{
            tooltip.hide();
        });
        pt.play();
    }

    private void setNewFont(String text) {
        if (text.length() > 11) {
            int font = FONT_DEFAULT - (((text.length() - 11) / 2) * 4) - 1;
            txtResult.setFont(Font.font(Math.max(font, 16)));
        } else
            txtResult.setFont(Font.font(FONT_DEFAULT));
    }
}
