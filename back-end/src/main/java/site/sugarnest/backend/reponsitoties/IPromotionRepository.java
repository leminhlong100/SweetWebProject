package site.sugarnest.backend.reponsitoties;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.sugarnest.backend.entities.PromotionEntity;

@Repository
public interface IPromotionRepository extends JpaRepository<PromotionEntity, Long> {
    public PromotionEntity findByCode(String code);
}
