package co.kr.pcninc.data.marketcore.repository;

import co.kr.pcninc.data.marketcore.domain.PurchaseList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseListRepository  extends JpaRepository<PurchaseList, Integer> {
    @Query(value = "SELECT * FROM DM_PURCHASE_LIST WHERE CUSTOMER = ?1 " +
            "AND set_id IN (SELECT dm_dataset_infos.set_id FROM dm_dataset_infos WHERE PUBLIC_YN='Y')", nativeQuery = true)
    Page<PurchaseList> findByUserY(String user, Pageable pageable);

    @Query(value = "SELECT * FROM DM_PURCHASE_LIST WHERE " +
            "set_id IN (SELECT dm_dataset_infos.set_id FROM dm_dataset_infos WHERE SELLER=?1 AND PUBLIC_YN='N')", nativeQuery = true)
    Page<PurchaseList> findByUserN(String user, Pageable pageable);


    @Query(value = "SELECT COUNT(*) FROM DM_PURCHASE_LIST WHERE able_yn = 'N'", nativeQuery = true)
    int unableDataset();

    @Query(value = "SELECT COUNT(*) FROM DM_PURCHASE_LIST WHERE able_yn = 'N' AND CUSTOMER = ?1", nativeQuery = true)
    int unableDatasetByUser(String userId);


}
