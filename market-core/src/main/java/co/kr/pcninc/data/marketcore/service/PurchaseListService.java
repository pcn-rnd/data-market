package co.kr.pcninc.data.marketcore.service;

import co.kr.pcninc.data.marketcore.domain.PurchaseList;
import co.kr.pcninc.data.marketcore.repository.PurchaseListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseListService {

    @Autowired
    PurchaseListRepository purchaseListRepository;

    public Page<PurchaseList> getListPageableY(String user, Pageable pageable) { return purchaseListRepository.findByUserY(user, pageable);}

    public Page<PurchaseList> getListPageableN(String user, Pageable pageable) { return purchaseListRepository.findByUserN(user, pageable);}

    public void createPurchase(PurchaseList purchaseList) { purchaseListRepository.save(purchaseList); }

    public void deletePurchase(int listId) { purchaseListRepository.deleteById(listId); }


    //
    public int unableDataset() { return purchaseListRepository.unableDataset(); }

    public int unableDatasetByUser(String userId) { return purchaseListRepository.unableDatasetByUser(userId); }

    public List<PurchaseList> getAll() {return purchaseListRepository.findAll();}
}
