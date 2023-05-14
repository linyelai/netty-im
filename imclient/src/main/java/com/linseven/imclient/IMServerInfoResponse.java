package com.linseven.imclient;

import com.linseven.IMServerInfo;
import lombok.Data;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/10 15:21
 */
@Data
public class IMServerInfoResponse<T> {
    private IMServerInfo data;
    private String errorMsg;
    private int errorCode =0;
}
