

import java.util.ArrayList;

public class Client {
	private int id;
	private String name;
	private ArrayList<OfficialBenchmark> ofcBen;
	
	Client(int id,String name){
		this.id=id;
		this.name=name;
		this.ofcBen=new ArrayList<OfficialBenchmark>();
	}
	
	public void AddBenchmark (String Desc,String name,double exuTime) {
		OfficialBenchmark ben=new OfficialBenchmark(Desc,name,exuTime);
		this.ofcBen.add(ben);
	}
	
	public int  getId() {
		return this.id;
	}
	

	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<OfficialBenchmark> getBenchmarks(){
		return this.ofcBen;
	}
	public String toString() {
		String Name= "Client: "+this.name+"\n"; 
		for(int i=0;i<this.ofcBen.size();++i) {
			Name+=ofcBen.get(i).toString() +"\n";
		}
		return Name;
	}
}
