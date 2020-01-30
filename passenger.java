import java.util.Random; 
import java.util.Scanner; 
class Queue 
{ 
	int front, rear, size; 
	int capacity; 
	int array[]; 
	
	public Queue(int capacity) { 
		this.capacity = capacity; 
		front = this.size = 0; 
		rear = capacity - 1; 
		array = new int[this.capacity]; 
			
	} 
	
	// Queue is full when size becomes equal to 
	// the capacity 
	boolean isFull(Queue queue) 
	{ return (queue.size == queue.capacity); 
	} 
	
	// Queue is empty when size is 0 
	boolean isEmpty(Queue queue) 
	{ return (queue.size == 0); } 
	
	// Method to add an item to the queue. 
	// It changes rear and size 
	void enqueue( int item) 
	{ 
		if (isFull(this)) 
			return; 
		this.rear = (this.rear + 1)%this.capacity; 
		this.array[this.rear] = item; 
		this.size = this.size + 1; 
	} 
	
	// Method to remove an item from queue. 
	// It changes front and size 
	int dequeue() 
	{ 
		if (isEmpty(this)) 
			return Integer.MIN_VALUE; 
		
		int item = this.array[this.front]; 
		this.front = (this.front + 1)%this.capacity; 
		this.size = this.size - 1; 
		return item; 
	} 
	
	int sum()
	{
		if (isEmpty(this)) 
			return Integer.MIN_VALUE; 
		
		int sum1=0;
		for(int i=front;i<=rear;i++)
		{
			sum1+=array[i];
		}
		return sum1;
	}
	
	// Method to get front of queue 
	int front() 
	{ 
		if (isEmpty(this)) 
			return Integer.MIN_VALUE; 
		
		return this.array[this.front]; 
	} 
		
	// Method to get rear of queue 
	int rear() 
	{ 
		if (isEmpty(this)) 
			return Integer.MIN_VALUE; 
		
		return this.array[this.rear]; 
	} 
} 

// Driver class 
public class passenger
{ 
	//method to return a random number whose average is n
	static int randG(int n)
	{
		Random rand = new Random(); 
		int a = rand.nextInt(2*n)+1;
		return a;
	}
	

	public static void main(String[] args) 
	{ 
		Scanner sc=new Scanner(System.in);
		System.out.println("enter the duration of checking");
		int checkin = sc.nextInt();
		System.out.println("enter the average coach arrival rate");
		int coach_Arrrate= sc.nextInt();
		System.out.println("enter the average coach service rate");
		int coach_Servrate= sc.nextInt();
		System.out.println("enter the average first Class arrival rate");
		int first_Arrrate= sc.nextInt();
		System.out.println("enter the average first Class service rate");
		int first_Servrate= sc.nextInt();
		
		int f1=0,f2=0,c1=0,c2=0,c3=0; // f1,f2-> first class service station && c1,c2,c3->coach service station
		int cf1=0,cf2=0,cc1=0,cc2=0,cc3=0; //count for which stations are busy
		int waitTime1=0,waitTime2=0; //wait time count for coach queue and first class queue
		int maxWaitTime1=0,maxWaitTime2=0; //max wait time for coach queue and first class queue
		int countCoachPassanger=0,countFirstPassanger=0; //count of each kind of passenger
		
		Queue Coachqueue = new Queue(1000);
			
		Queue Firstqueue = new Queue(1000); 

		int arrtimecoach; // arrival time of coach
		int arrtimefirst;
		
		int i; //represents minute
		
		int lastarrt1=0; //the time at which 
		int lastarrt2=0;
		
		int maxlength1=0,maxlength2=0; // max length of each queue

		
		for(i=0;i<=checkin+40;i++) // for the reason checking closes 40 minutes prior
		{
			int ar1,ar2;
			//subtracting the average arrival time so as new passenger gets in on his average time
			if(lastarrt1>0)
				lastarrt1--;
			if(lastarrt2>0)
				lastarrt2--;
			// if any of station is holding a passenger then subtracting the 1 unit time for the service time with each interval of i
			if(c1!=0)
				c1--;
			if(c2!=0)
				c2--;
			if(c3!=0)
				c3--;
			if(f1!=0)
				f1--;
			if(f2!=0)
				f2--;
			//System.out.println("for i="+i+" lastarrt1 "+lastarrt1+" lastarrt2 "+lastarrt2 + " the stations "+c1+" "+c2+" "+c3+" "+f1+" "+f2+" " );
			
			//adding first class passengers to queue and ensuring no new member can come in queue 40min prior to departure
			if(lastarrt2==0 && i<=checkin)
			{
				arrtimefirst=randG(first_Arrrate);
				ar2=randG(first_Servrate);
				Firstqueue.enqueue(ar2);
				lastarrt2=arrtimefirst;
				//System.out.println("element "+arrtimefirst+" adding to First queue with size "+ Firstqueue.size);
				countFirstPassanger+=1; //to count number of first class passengers
			}
			
			//sending first class passengers to service stations
			if(f1==0 && Firstqueue.isEmpty(Firstqueue)==false) // checking if station is empty and queue is not empty
			{
				f1=Firstqueue.dequeue();
				//System.out.println(" 2dequeued1"+" with time "+ f1);
				cf1+=f1;
			}
			if(f2==0 && Firstqueue.isEmpty(Firstqueue)==false)
			{
				f2=Firstqueue.dequeue();;
				//System.out.println(" 2dequeued2"+" with time "+ f2);
				cf2+=f2;
			}
			

			//adding coach passengers to queue and ensuring no new member can come in queue 40min prior to departure
			if(lastarrt1==0  && i<=checkin) //if the lastarr1 time is zero new passenger's arrival time will be calculated
			{
				arrtimecoach=randG(coach_Arrrate);
				ar1=randG(coach_Servrate);
				Coachqueue.enqueue(ar1);
				lastarrt1=arrtimecoach;
				//System.out.println("element "+arrtimecoach+" adding to coach class queue with size "+ Coachqueue.size);
				countCoachPassanger+=1; //to count number of coach passengers
			}
			
			if(c1==0 && Coachqueue.isEmpty(Coachqueue)==false)
			{
				c1=Coachqueue.dequeue();
				//System.out.println(" 1dequeued1"+" with time "+ c1);
				cc1+=c1;
			}
			
			if(c2==0 && Coachqueue.isEmpty(Coachqueue)==false)
			{
				c2=Coachqueue.dequeue();
				//System.out.println(" 1dequeued2"+" with time "+ c2);
				cc2+=c2;
			}
			if(c3==0 && Coachqueue.isEmpty(Coachqueue)==false)
			{
				c3=Coachqueue.dequeue();
				//System.out.println(" 1dequeued3"+" with time "+ c3);
				cc3+=c3;
				
			}
			if(f1==0 && Coachqueue.isEmpty(Coachqueue)==false)
			{
				f1=Coachqueue.dequeue();
				//System.out.println(" 1dequeued4"+" with time "+ f1);
				cf1+=f1;
				
			}
			if(f2==0 && Coachqueue.isEmpty(Coachqueue)==false)
			{
				f2=Coachqueue.dequeue();
				//System.out.println(" 1dequeued5"+" with time "+ f2);
				cf2+=f2;
			}
			//System.out.println("for i="+i+" lastarrt1 "+lastarrt1 + " lastarrt2 "+lastarrt2 + " the stations "+c1+" "+c2+" "+c3+" "+f1+" "+f2+" " );
			
			
			// to break the loop if there are no passengers at the service stations and queues aren't taking admission of new passengers
			if(f1==0 && f2==0 && c1==0 && c2==0 && c3==0 && i>checkin)
				break;
			
			// to calculate the total wait time and max wait time for coach passenger
			if(Coachqueue.isEmpty(Coachqueue)==false)
			{
				waitTime1+=Coachqueue.size;
				if (maxlength1<Coachqueue.size)
					maxlength1=Coachqueue.size;
				if (maxWaitTime1<Coachqueue.sum())
					maxWaitTime1=Coachqueue.sum();
			}
			
			// to calculate the total wait time and max wait time for First class passenger
			if(Firstqueue.isEmpty(Firstqueue)==false)
			{
				waitTime2+=Firstqueue.size;
				if(maxlength2<Firstqueue.size)
					maxlength2=Firstqueue.size; 
				if(maxWaitTime2<Firstqueue.sum())
					maxWaitTime2=Firstqueue.sum();
			}
		}
		
		if  (Firstqueue.isEmpty(Firstqueue)==false || Coachqueue.isEmpty(Coachqueue)==false)
		{
			System.out.println("\npassengers left out even after 40 min of checking time(ie-> after 40min of closing queue)");
		}
		
		float avgwaitTime1= (float)waitTime1/countCoachPassanger; // calculating average wait time for coach passenger
		float avgwaitTime2= (float)waitTime2/countFirstPassanger; // calculating average wait time for first class passenger
        
		System.out.println("\n************************************************************************************");
		System.out.println("The duration of the simulation is "+i+ " minutes");
		System.out.println("Max length of Coach queue is "+maxlength1);
		System.out.println("Max length of First class queue is "+maxlength2);
		System.out.println("The max wait time for Coach queue is "+maxWaitTime1+" minutes");
		System.out.println("The max wait time for First class queue is "+maxWaitTime2+" minutes");
		System.out.printf("The average wait time for Coach queue is %.2f min\n",avgwaitTime1);
		System.out.printf("The average wait time for First class queue is %.2f min\n",avgwaitTime2);
		System.out.println("The percentage of time for which coach service station c1 was busy "+(cc1*100/(i))+"%");
		System.out.println("The percentage of time for which coach service station c2 was busy "+(cc2*100/(i))+"%");
		System.out.println("The percentage of time for which coach service station c3 was busy "+(cc3*100/(i))+"%");
		System.out.println("The percentage of time for which first class service station f1 was busy "+(cf1*100/(i))+"%");
		System.out.println("The percentage of time for which first class service station f2 was busy "+(cf2*100/(i))+"%");
		System.out.println("************************************************************************************");
	} 
} 


