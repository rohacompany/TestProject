package color.test.roha.com.colortestproject.datas;

/**
 * Created by dev on 2016-11-25.
 */

public class SMSAuthVO {
    private String method;
    private int result;
    private String auth_code;
    private String request_data;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    public String getRequest_data() {
        return request_data;
    }

    public void setRequest_data(String request_data) {
        this.request_data = request_data;
    }
}
