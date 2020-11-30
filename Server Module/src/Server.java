import java.util.ArrayList;

public class Server {
	private String name;
	private int id;
	private ArrayList<OfficialBenchmark> ofcBen;
	
	
	
	public Server(int id2, String name2) {
		this.id=id2;
		this.name=name2;
		this.ofcBen=new ArrayList<OfficialBenchmark>();
	}

	public int getId() {
		return this.id;
	}
	
	public void AddBenchmark (String Desc,String name,double exuTime) {
		OfficialBenchmark ben=new OfficialBenchmark(Desc,name,exuTime);
		this.ofcBen.add(ben);
	}
	
	//This method is to check the position of a specific benchmark so if an error happened and one benchmark was saved before the other 
	// And it's to make sure we are comparing the same two benchmarks
	private int findIndexOfClientBenchmark(String name,ArrayList<OfficialBenchmark> c) {
		for(int i=0;i<c.size();i++) {
			if(c.get(i).getName().equals(name)) {
								return i;}
		}
		return 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	//Method to get the list of the specRatio of each Benchmark 
	private ArrayList<Double> getListSpecRatio(Client c){
		ArrayList<Double> SpecList=new ArrayList<Double>();
		ArrayList<OfficialBenchmark> ClienBen=c.getBenchmarks();
		for(int i=0;i<ofcBen.size();++i) {
			
			int index=this.findIndexOfClientBenchmark(ofcBen.get(i).getName(), ClienBen);
			double ClientTime=ClienBen.get(index).getExuTime();
			double ratio=(ofcBen.get(i).getExuTime()*1.0)/ClientTime;
			
			SpecList.add(ratio);
		}
		return SpecList;
	}
	
	// Method to return the Geometric Mean of the specRatio 
	public double getSPECratio(Client c) {
		ArrayList<Double> SpecList=this.getListSpecRatio(c);
		double  product=1;
		for(int i=0;i<SpecList.size();i++) {
			product*=SpecList.get(i);
		}
		
		
		double a= (double)Math.pow(product,(1.0/SpecList.size()));
		return (double) Math.round(a * 1000) / 1000;
	}
	
	// Method to print the server on the screen 
	public String toString() {
		String Name= "Server: "+this.name+"\n"; 
		for(int i=0;i<this.ofcBen.size();++i) {
			Name+=ofcBen.get(i).toString() +"\n";
		}
		return Name;
	}
}
