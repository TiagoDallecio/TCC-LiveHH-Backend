package br.com.livehh.livehh_engine.adapter.out.persistence;

import br.com.livehh.livehh_engine.adapter.out.persistence.entity.HandHistoryJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HandHistoryRepository extends JpaRepository<HandHistoryJPAEntity, String> {
}
