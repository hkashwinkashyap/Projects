package stacs.travel.cs5031p2code.utils;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * A class for transmission the result between front-end and back-end.
 *
 * @author 220032952
 * @version 0.0.1
 */
@Data
@Builder
public class ResponseResult<T> implements Serializable {
    /**
     * Serial Version UID.
     */
    @Serial
    private static final long serialVersionUID = -6223513048455681939L;

    /**
     * code.
     */
    private Integer code;

    /**
     * message.
     */
    private String message;

    /**
     * data.
     */
    private T data;

    /**
     * The constructor method.
     */
    public ResponseResult() {
        super();
    }

    /**
     * Overloaded constructor with imputed code.
     *
     * @param code HTTP response code.
     */
    public ResponseResult(Integer code) {
        super();
        this.code = code;
    }

    /**
     * Overloaded constructor with imputed code and message.
     *
     * @param code    HTTP response code.
     * @param message response message for HTTP code.
     */
    public ResponseResult(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    /**
     * Overloaded constructor with imputed code and throwable.
     *
     * @param code      HTTP response code.
     * @param throwable thrown error.
     */
    public ResponseResult(Integer code, Throwable throwable) {
        super();
        this.code = code;
        this.message = throwable.getMessage();
    }

    /**
     * Overloaded constructor with imputed code.
     *
     * @param code HTTP response code.
     * @param data data sent in response.
     */
    public ResponseResult(Integer code, T data) {
        super();
        this.code = code;
        this.data = data;
    }

    /**
     * Overloaded constructor with imputed code, message and data.
     *
     * @param code    HTTP response code.
     * @param message message sent in response.
     * @param data    data sent in response.
     */
    public ResponseResult(Integer code, String message, T data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * Class getter.
     *
     * @return Data.
     */
    public T getData() {
        return data;
    }

    /**
     * Data setter.
     *
     * @param data data.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Hashcode fetcher.
     *
     * @return the hashcode.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        // Modifying resulting hashcode depending on data.
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    /**
     * Overriding equals. Runs through attributes for equality.
     *
     * @param obj object to be equated.
     * @return boolean of if attributes that are valid.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ResponseResult<?> other = (ResponseResult<?>) obj;
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        if (code == null) {
            return other.code == null;
        } else return code.equals(other.code);
    }
}

