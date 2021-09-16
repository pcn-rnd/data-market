package co.kr.pcninc.data.marketcore.service;

import co.kr.pcninc.data.marketcore.domain.Dataset;
import co.kr.pcninc.data.marketcore.domain.DatasetMapping;
import co.kr.pcninc.data.marketcore.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DatasetService {

    @Autowired
    DatasetRepository datasetRepository;

    public Page<Dataset> getDataSetList(Pageable page, String keyword) { return datasetRepository.findAllBy(page, keyword); }

    public Dataset getDataSet(int setId) { return datasetRepository.findById(setId).orElse(null); }

    @Transactional
    public void createDataset(Dataset dataset) {
        datasetRepository.save(dataset);
    }

    public void deleteDataset(int setId) { datasetRepository.deleteById(setId); }

    public List<Dataset> getPopulars() { return datasetRepository.getPopulars(); }

    public List<Dataset> getNewest() { return datasetRepository.getNewest(); }
}
