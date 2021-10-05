package co.kr.pcninc.data.marketcore.domain;

import java.util.Date;

public interface DatasetMapping {
    int getSetId();
    Category getCatId();
    String getTitle();
    String getContent();
    User getSeller();
    String getPublicYn();
    int getViews();
    Date getCrtDt();
    String getUrlsInfo();
    String getDatatype();
    Integer getWsId();
}
