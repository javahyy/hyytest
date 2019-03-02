package review;

public class ThreadShare {

//多线程共享数据
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        for (int i = 0; i <4 ; i++) {
            if(i%2==0){
                new Thread(new RunnableCusToInc(shareData),"Thread "+i).start();
            }else{
                new Thread(new RunnableCusToDec(shareData),"Thread "+ i).start();
            }
        }
    }
}

//多个线程行为一致共同操作一个数据
//如果每个线程执行的代码相同，可以使用同一个 Runnable 对象，这个 Runnable 对象中有那个共享数据，
// 例如，买票系统就可以这么做
class RunnableCusToInc implements Runnable{
    private ShareData shareData;
    public RunnableCusToInc(ShareData data){
        this.shareData = data;
    }
    @Override
    public void run(){
        for (int i = 0; i <5 ; i++) {
            shareData.inc();
        }
    }
}
class RunnableCusToDec implements Runnable{
    private ShareData shareData;
    public RunnableCusToDec(ShareData data){
        this.shareData = data;
    }
    @Override
    public void run(){
        for (int i = 0; i <5 ; i++) {
            shareData.dec();
        }
    }
}


class ShareData{
    private int num = 10;
    public synchronized void inc(){
        num ++;
        System.out.println(Thread.currentThread().getName()+" : invoke inc method num = "+num);
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public synchronized void dec(){
        num --;
        System.out.println(Thread.currentThread().getName()+" : invoke inc method num = "+num);
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}