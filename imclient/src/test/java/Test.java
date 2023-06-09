import com.linseven.imclient.service.IMServerInfoService;
import com.linseven.imclient.service.UserService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.*;

public class Test {


    public static void main(String[] args) throws IOException {

//        IMServerInfoService imServerInfoService = new IMServerInfoService();
//        UserService userService = new UserService();
//        String username = "test";
//        String password = "123456";
//        String token = userService.login(username,password);
//        imServerInfoService.getIMServerInfo(token);
//        new ArrayBlockingQueue(1);
        test1();

    }


    private CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

    private static DelayQueue  queue = new DelayQueue();

    private static ExecutorService executorService = new ThreadPoolExecutor(5,10,1000, TimeUnit.MILLISECONDS,queue);

    public static void test1(){


        int batch = 0;

        for(int i=0;i<1000;i++){

            if(i%10==0){
                batch++;
            }
            executorService.execute(new Task(i,2000*batch));
        }


    }



}


class Task implements  Delayed ,Runnable{


     private Integer id;
      long start ;
    Task(Integer id,long delay){
       this.id = id;
       this.start= System.currentTimeMillis()+delay;
    }
    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        long diff =  this.start-System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NotNull Delayed o) {
        return (int) (this.start-((Task) o).start);
    }

    @Override
    public void run() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("running "+this.id+","+ simpleDateFormat.format(new Date(System.currentTimeMillis())));
    }
}