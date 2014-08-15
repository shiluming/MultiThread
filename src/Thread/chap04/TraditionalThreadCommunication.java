package Thread.chap04;

public class TraditionalThreadCommunication {

	/**���߳�ѭ��10�Σ��������߳�ѭ��100�Σ������ֻص����߳�ѭ��10�Σ������ٻص����߳�ѭ��100�����ѭ��50��
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
 * ʵ��ҵ���߼����࣬����ص���Դ���߳̽����һ��ʹ���������ά��
 * ���߳�ֱ�ӵ��ü���
 * @author USER
 *
 */
class Business{
	private boolean bShouldSub = true;
	public synchronized void sub(int i){
		while(!bShouldSub){			//while��if�ã��ܹ�����һ�飬��ֹα����
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
		this.notify();//�����߳�
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
