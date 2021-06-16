package calc.service;

import calc.dao.CalcResultDao;
import calc.model.CalcResult;

import java.util.List;

public class CalcService {
    private CalcResultDao calcResultDao = new CalcResultDao();

    public CalcService() {}

    public void saveCalcResult(CalcResult calcResult) {
        calcResultDao.save(calcResult);
    }

    public List<CalcResult> getHistoryCalcResult(int limit) {
        return calcResultDao.getHistory(limit);
    }
}
