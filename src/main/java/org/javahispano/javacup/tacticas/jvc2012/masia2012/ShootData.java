package org.javahispano.javacup.tacticas.jvc2012.masia2012;



class ShootData{
	public double angle;
	public double verticalAngle;
	public double probability;
	public double shootOnGoalProbability;
	final double z; 
	public ShootData(double angle, double verticalAngle, double z, double probability, double shootOnGoalProbability) {
		super();
		this.angle = angle;
		this.verticalAngle = verticalAngle;
		this.z = z;
		this.probability = probability;
		this.shootOnGoalProbability = shootOnGoalProbability;
	}
	
	public ShootData compare(ShootData other) {
		if(shootOnGoalProbability > other.shootOnGoalProbability)
			return this;
		if(shootOnGoalProbability < other.shootOnGoalProbability)
			return other;
		if(probability > other.probability)
			return this;
		if(probability < other.probability)
			return other;
		if(z <= other.z)
			return this;
		return other;
	}
}
