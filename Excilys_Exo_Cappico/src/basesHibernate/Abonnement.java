package basesHibernate;

public class Abonnement {
		private int id;
		private Zone zone;
		private int periode;
		
		
		public Abonnement(){
			
		}
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public Zone getZone() {
			return zone;
		}
		public void setZone(Zone zone) {
			this.zone = zone;
		}
		public int getPeriode() {
			return periode;
		}
		public void setPeriode(int periode) {
			this.periode = periode;
		}
		
		
}
