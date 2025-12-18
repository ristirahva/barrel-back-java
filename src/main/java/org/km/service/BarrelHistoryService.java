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
public class BarrelHistoryService  extends AbstractCrudService <BarrelHistoryView, BarrelHistoryReadRepository, BarrelHistory, BarrelHistoryWriteRepository> {

    private static final Logger log = LoggerFactory.getLogger(BarrelHistoryService.class);

    @Autowired
    public BarrelHistoryService(BarrelHistoryReadRepository readRepository, BarrelHistoryWriteRepository writeRepository) {
        super(readRepository, writeRepository);
    }

    public List<BarrelHistoryView> getBarrelHistory() {
        return getAll();
    }

    @Override
    protected String getEntityName() {
        return "История бочек";
    }
}
