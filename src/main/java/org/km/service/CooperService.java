package org.km.service;

import org.km.db.entity.Cooper;

import org.km.db.repository.CooperRepository;

import org.km.dto.CooperDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CooperService extends AbstractCrudService <Cooper, CooperRepository>{

    private static final Logger log = LoggerFactory.getLogger(CooperService.class);

    @Autowired
    public CooperService(CooperRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Производитель бочек";
    }

    /**
     * Получение списка производителей
     *
     * @return список производителей
     *
     */
    public List<CooperDTO> getCoopers() {
        return repository.findAllWithBarrelCount();
    }

    /**
     * Получение производителя по id
     *
     * @param cooperId
     * @return
     */
//    public Optional<Cooper> getCooper(int cooperId) {
//        return super.getById(cooperId);
//    }

    /**
     * Добавление производителя
     *
     * @param cooper
     * @return
     */
    public Cooper addCooper(Cooper cooper) {
        return repository.save(cooper);
    }
}
