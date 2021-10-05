package co.kr.pcninc.data.marketcore.common.message;

import co.kr.pcninc.data.marketcore.common.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HttpResponse<T> {

    private String result = Constants.SUCCESS;
    private String failure;
    private T body;

    public HttpResponse(T body) {
        this.body = body;
    }

    public HttpResponse(T body, String result) {
        this.result = result;
        this.body = body;
    }

    public HttpResponse(T body, String result, String failure) {
        this.result = result;
        this.failure = failure;
        this.body = body;
    }
}
