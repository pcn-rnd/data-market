package co.kr.pcninc.data.marketcore.service;

import co.kr.pcninc.data.marketcore.domain.Dataset;
import co.kr.pcninc.data.marketcore.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DatasetService {

    @Autowired
    DatasetRepository datasetRepository;

    public Page<Dataset> getDataSetList(Pageable page, String keyword) { return datasetRepository.findAllBy(page, keyword); }

    public Page<Dataset> getDatasetListByCat(Pageable page, String keyword, int catId) { return datasetRepository.findAllByCat(page, keyword, catId); }

    public Dataset getDataSet(int setId) { return datasetRepository.findById(setId).orElse(null); }

    @Transactional
    public void save(Dataset dataset) {
        datasetRepository.save(dataset);
    }

    public void deleteDataset(int setId) { datasetRepository.deleteById(setId); }

    public List<Dataset> getPopulars(int rows) { return datasetRepository.findPopulars(rows); }

    public List<Dataset> getNewest() { return datasetRepository.findTop5ByOrderByCrtDtDesc(); }

    public List<Dataset> getMostPurchased() { return datasetRepository.findMostPurchased(); }

    public List<Object> getCountCats() { return datasetRepository.getCountCats(); }

    public long getAllCounts() { return datasetRepository.count(); }

    public long getYCnt(String publicYn) { return datasetRepository.countByPublicYn(publicYn); }


    //
    public List<Dataset> getAll() { return datasetRepository.findAll(); }

    public List<String> getAllTitle() {return datasetRepository.getAllTitle();}

    public List<String> getAllContent() { return datasetRepository.getAllContent(); }

    public List<Integer> getAllViews() { return datasetRepository.getAllViews(); }

    public List<String> getAllDataType() { return datasetRepository.getAllDataType(); }

    public List<String> getPathList() { return datasetRepository.getPathList(); }

    public List<Double> getAllPrice() { return datasetRepository.getAllPrice(); }


}
