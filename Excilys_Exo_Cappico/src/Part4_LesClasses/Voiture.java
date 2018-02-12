package Part4_LesClasses;

public class Voiture {
	public enum EnumCarburant{ SANS_PLOMB_98, SANS_PLOMB_95,DIESEL,GPL};
	EnumCarburant carburant;
	private float prixSP98;
	private float prixSP95;
	private float prixDiesel;
	private float prixGPL;
	private static int NOMBRE_ROUES= 4;
	/**
	 * @param args
	 */
	
	public Voiture(EnumCarburant c){
		carburant= c;
		switch(c)
		{
			case SANS_PLOMB_98: prixSP98 =1.08f;
			case SANS_PLOMB_95: prixSP95 =1.58f;
			case DIESEL: prixDiesel =0.9f;
			case GPL: prixGPL =0.81f;
			
		}
	}
	
	
	
	
	
	
	  /**  GETTER  AND  SETTERS **/
	
	public EnumCarburant getCarburant() {
		return carburant;
	}
	public void setCarburant(EnumCarburant carburant) {
		this.carburant = carburant;
	}
	public float getPrixSP98() {
		return prixSP98;
	}
	public void setPrixSP98(float prixSP98) {
		this.prixSP98 = prixSP98;
	}
	public float getPrixSP95() {
		return prixSP95;
	}
	public void setPrixSP95(float prixSP95) {
		this.prixSP95 = prixSP95;
	}
	public float getPrixDiesel() {
		return prixDiesel;
	}
	public void setPrixDiesel(float prixDiesel) {
		this.prixDiesel = prixDiesel;
	}
	public float getPrixGPL() {
		return prixGPL;
	}
	public void setPrixGPL(float prixGPL) {
		this.prixGPL = prixGPL;
	}
	public static int getNOMBRE_ROUES() {
		return NOMBRE_ROUES;
	}
	public static void setNOMBRE_ROUES(int nOMBRE_ROUES) {
		NOMBRE_ROUES = nOMBRE_ROUES;
	}
	
	

}
