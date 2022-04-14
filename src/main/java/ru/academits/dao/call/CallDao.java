package ru.academits.dao.call;

import ru.academits.model.Call;

import java.util.ArrayList;
import java.util.List;

public interface CallDao extends GenericCallDao<Call, Long> {

    List<Call> getCalls();

    void setDeletedByIds(ArrayList<Long> contactsIds);
}