package co.kr.pcninc.data.marketcore.repository;

import co.kr.pcninc.data.marketcore.domain.Dataset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DatasetRepository extends JpaRepository<Dataset, Integer> {


    @Query(value = "SELECT * FROM DM_DATASET_INFOS WHERE (TITLE || CONTENT) LIKE CONCAT('%', :k, '%') ORDER BY CRT_DT DESC", nativeQuery = true)
    Page<Dataset> findAllBy(Pageable page, @Param("k") String keyword);

    @Query(value = "SELECT * FROM DM_DATASET_INFOS WHERE cat_id = :catId AND (TITLE || CONTENT) LIKE CONCAT('%', :k, '%') ORDER BY CRT_DT DESC", nativeQuery = true)
    Page<Dataset> findAllByCat(Pageable page, @Param("k") String keyword, @Param("catId") int catId);


    //List<Dataset> findTop5ByOrderByViewsDesc();

    @Query(value = "SELECT * FROM DM_DATASET_INFOS ORDER BY VIEWS DESC LIMIT ?1", nativeQuery = true)
    List<Dataset> findPopulars(int rows);


    List<Dataset> findTop5ByOrderByCrtDtDesc();

    @Query(value = "SELECT * FROM DM_DATASET_INFOS WHERE set_id IN (SELECT set_id FROM DM_PURCHASE_LIST GROUP BY set_id ORDER BY count(*) desc) LIMIT 10", nativeQuery = true)
    List<Dataset> findMostPurchased();

    @Query(
            value = "SELECT dci.cat_name, COUNT(*) " +
                    "FROM DM_DATASET_INFOS ddi " +
                    "JOIN DM_CAT_INFOS dci ON ddi.cat_id = dci.cat_id " +
                    "GROUP BY cat_name", nativeQuery = true)
    List<Object> getCountCats();

    int countByPublicYn(String publicYn);
}
