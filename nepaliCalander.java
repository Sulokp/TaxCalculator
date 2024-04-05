import com.rudra.date.nepali.NepaliDate;
import com.rudra.date.nepali.NepaliDateConverter;

public class EnglishToNepaliDateConverter {
    public static void main(String[] args) {
        NepaliDateConverter converter = new NepaliDateConverter();
        NepaliDate nepaliDate = converter.getNepaliDate(2024, 4, 3);
        System.out.println("Nepali Date: " + nepaliDate.getYear() + "-" + nepaliDate.getMonth() + "-" + nepaliDate.getDay());
    }
}