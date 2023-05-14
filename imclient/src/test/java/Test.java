import com.linseven.imclient.service.IMServerInfoService;
import com.linseven.imclient.service.UserService;

import java.io.IOException;

public class Test {


    public static void main(String[] args) throws IOException {

        IMServerInfoService imServerInfoService = new IMServerInfoService();
        UserService userService = new UserService();
        String username = "test";
        String password = "123456";
        String token = userService.login(username,password);
        imServerInfoService.getIMServerInfo(token);
    }

}
