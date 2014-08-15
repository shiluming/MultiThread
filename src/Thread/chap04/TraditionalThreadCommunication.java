package Thread.chap04;

public class TraditionalThreadCommunication {

	/**子线程循环10次，接着主线程循环100次，接着又回到子线程循环10次，接着再回到主线程循环100，如此循环50次
	 * @param args
	 */
	public static void main(String[] args) {
		final Business business = new Business();
		new Thread(
				new Runnable(){
					@Override
					public void run() {
						for(int i=1;i<=50;i++){
							/*synchronized(TraditionalThreadCommunication.class){
								
								for(int j=1;j<=10;j++){
									System.out.println("sub thread sequece of :"+ j + ",loop of "+i);
								}							
							}*/
							business.sub(i);
						}
					}
				}
				
		).start();
		
		for(int i=1;i<=50;i++){
			/*synchronized(TraditionalThreadCommunication.class){
				
				for(int j=1;j<=10;j++){
					System.out.println("main thread sequece of :"+ j + ",loop of "+i);
				}							
			}*/
			business.main(i);
		}		
	}
}
/**
 * 实现业务逻辑的类，把相关的资源，线程结合在一起，使代码更容易维护
 * 主线程直接调用即可
 * @author USER
 *
 */
class Business{
	private boolean bShouldSub = true;
	public synchronized void sub(int i){
		while(!bShouldSub){			//while比if好，能够多检查一遍，防止伪唤醒
		//if(!bShouldSub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int j=1;j<=10;j++){
			System.out.println("sub thread sequece of :"+ j + ",loop of "+i);
		}		
		bShouldSub = false;
		this.notify();//唤醒线程
	}
	public synchronized void main(int i){
		if(bShouldSub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(int j=1;j<=100;j++){
			System.out.println("main thread sequece of :"+ j + ",loop of "+i);
		}		
		bShouldSub = true;
		this.notify();
	}
}
