package co.kr.pcninc.data.marketcore.service;

import co.kr.pcninc.data.marketcore.domain.Log;
import co.kr.pcninc.data.marketcore.domain.LogSimpleMapping;
import co.kr.pcninc.data.marketcore.domain.LogUrlMapping;
import co.kr.pcninc.data.marketcore.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;

    public List<Log> findAll() { return logRepository.findAll(); }

    public Optional<Log> findLog(int logId) {return logRepository.findById(logId);}

    public List<LogSimpleMapping> getSimple() {return logRepository.findAllBy();}

    public List<Object[]> getUrl() {return logRepository.findUrls();}

    public List<Log> getErrors() {return logRepository.findErrors();}

    public List<Object[]> findSystems() {return logRepository.findSystems(); }


    public List<Integer> getIds() {return logRepository.getIds();}

    public List<String> getLevels() {return logRepository.getLevels();}

    public List<String> getMethods() {return logRepository.getMethods();}

    public List<String> getSnames() {return logRepository.getSnames();}

    public List<String> getHosts() {return logRepository.getHosts();}

    public List<Integer> getPorts() {return logRepository.getPorts();}

    public List<String> getActions() {return logRepository.getActions();}

    public List<String> getCnames() {return logRepository.getCnames();}

    public List<String> getActors() {return logRepository.getActors();}

    public List<Date> getDates() {return logRepository.getDates();}

    public List<String> getRoles() {return logRepository.getRoles();}


    public List<String> getPaths() {return logRepository.getPaths();}

}
