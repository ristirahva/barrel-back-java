package org.km.service;

import org.km.db.entity.BarrelHistory;
import org.km.db.repository.*;
import org.km.db.view.BarrelHistoryView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarrelHistoryService  extends AbstractCrudService <BarrelHistoryView, BarrelHistoryRepository> {

    private static final Logger log = LoggerFactory.getLogger(BarrelHistoryService.class);

    @Autowired
    public BarrelHistoryService(BarrelHistoryRepository repository) {
        super(repository);
    }

    public List<BarrelHistoryView> getBarrelHistory() {
        return getAll();
    }

    @Override
    protected String getEntityName() {
        return "История бочек";
    }
}
