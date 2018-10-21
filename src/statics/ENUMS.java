package statics;

public class ENUMS {

	public static enum REQUEST_STATUS {
		ON_HOLD("ON_HOLD"), ACCEPTED("ACCEPTED"), REFUSED("REFUSED"), REMOVED("REMOVED");

		private String value;

		public String getValue() {
			return this.value;
		}

		private REQUEST_STATUS(String value) {
			this.value = value;
		}

	}

	public static enum DISPONIBILITY_FOR_PROJECT {
		AVAILABLE("AVAILABLE"), BUSY("BUSY");

		private String value;

		private DISPONIBILITY_FOR_PROJECT(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public static enum PROJECT_FRAMEWORK { 
		TO_DO("FAZER "), DOING("FAZENDO"), DONE("FEITO");
		
		private String value;
		
		private PROJECT_FRAMEWORK (String value) { 
			this.value=value;
		}
		public String getValue () { 
			return value;
		}
	}

	public static enum ACCOUNT_STATUS {
		ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

		private String value;

		private ACCOUNT_STATUS(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

}
