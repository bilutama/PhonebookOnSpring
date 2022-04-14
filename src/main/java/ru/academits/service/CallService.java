package ru.academits.service;

import org.springframework.stereotype.Service;
import ru.academits.dao.call.CallDao;
import ru.academits.model.Call;

import java.util.ArrayList;
import java.util.List;

@Service
public class CallService {
    private final CallDao callDao;

    public CallService(CallDao callDao) {
        this.callDao = callDao;
    }

    public void addCall(Call call) {
        callDao.create(call);
    }

    public List<Call> getCalls(){
        return callDao.getCalls();
    }

    public void setCallsAsDeleted(ArrayList<Long> callsIds) {
        callDao.setDeletedByIds(callsIds);
    }
}