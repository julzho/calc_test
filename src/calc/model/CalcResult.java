package calc.model;

import calc.service.CalcService;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "calc_result", schema = "public", catalog = "calc")
public class CalcResult {
    private BigDecimal firstNum;
    private BigDecimal secondNum;
    private String operation;
    private BigDecimal resultNum;
    private Date resultDate;

    public CalcResult(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "first_num", nullable = false, precision = 0)
    public BigDecimal getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(BigDecimal firstNum) {
        this.firstNum = firstNum;
    }

    @Basic
    @Column(name = "second_num", nullable = true, precision = 0)
    public BigDecimal getSecondNum() {
        return secondNum;
    }

    public void setSecondNum(BigDecimal secondNum) {
        this.secondNum = secondNum;
    }

    @Basic
    @Column(name = "operation", nullable = false, length = 2)
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Basic
    @Column(name = "result_num", nullable = false, precision = 0)
    public BigDecimal getResultNum() {
        return resultNum;
    }

    public void setResultNum(BigDecimal resultNum) {
        this.resultNum = resultNum;
    }

    @Basic
    @Column(name = "result_date", nullable = false)
    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalcResult that = (CalcResult) o;
        return id == that.id && Objects.equals(firstNum, that.firstNum) && Objects.equals(secondNum, that.secondNum) && Objects.equals(operation, that.operation) && Objects.equals(resultNum, that.resultNum) && Objects.equals(resultDate, that.resultDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstNum, secondNum, operation, resultNum, resultDate);
    }
}
