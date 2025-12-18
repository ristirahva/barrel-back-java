package org.km.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface WriteRepository<T, ID> extends CrudRepository<T, ID> {
}
