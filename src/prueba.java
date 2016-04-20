import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class prueba {

	public static void main(String[] args) {
		LocalDateTime ahora = LocalDateTime.now();
	      DateTimeFormatter formatter =   DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm");
	      String strDateTime = ahora.format(formatter);
	      System.out.println(strDateTime);
	}

}
