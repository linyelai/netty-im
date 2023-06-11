package com.linseven;

import lombok.Data;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/10 14:05
 */
@Data
public class IMServerInfo {

    private String imserverIp;
    private Integer imPort;
    private String imWebServerIP;
    private Integer imWebPort;
    private String username;

}
