package org.km.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ReadRepository<T, ID> extends JpaRepository<T, ID> {
}
