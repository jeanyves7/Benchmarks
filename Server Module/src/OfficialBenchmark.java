

public class OfficialBenchmark {
	private String Description;
	private String name;
	private double ExecutionTime;
	
	OfficialBenchmark(String Desc,String name,double exuTime){
		this.Description=Desc;
		this.name=name;
		this.ExecutionTime=exuTime;
	}
	
	public double getExuTime() {
		return this.ExecutionTime;
	}
	
	public String getName() {
		return this.name;
	}
	public String toString() {
		return "Description: "+this.Description + " Name: "+ this.name + "ExuTime: "+this.ExecutionTime;
	}
}
