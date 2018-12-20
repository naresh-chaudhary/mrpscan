package flipkart.retail.temp;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nareshkumar.v on 21/12/18.
 */
public class Parser {

  public String findMrp(String text) {
    String res = "";
//text="INNOVATION SILK PROTEIN 420 ab 145/-.00.1450 ook more moothman Proven result 10% SMOOTH The re and shine of your hori Your hair is smooth, controlled and Follow the L'Oréal Paris SMOOTH INTENSE progn SHAMPOO CONDITIONER 3 varme Condition Detangle IN CASE OF CONTACT WITH EYES. RINSE IMMEDIATELY OP 10 LEI Mktd. by / Regd. office: L'Oréal India Pvt. Ltd. A-Wing, 8th Floor, Marathon Futurex, N. M. Joshi Mara. Lower Parel, Mumbai - 400013. Any questions/complaints? Call or write: Consumer Advisor, at Regd, office; Tel: 1800-22-3000; E-mail: Advisor.India@loreal.com 175ml+17.5ml FREE = 192.5ml B515791C 16-03/2018 15-02/2021 Rs. 150 10 TOATE LOVE MEBLE 81901526 10135 1> \n ";

    String NUM_DOT_OO = "(\\d+\\.00)";
    String NUM_SLASH_HYPHERN = "(\\d+)\\/-";
    String ANY_CHAR_NUM_OPTIONAL_00 = ".*?(\\d+\\.?0?0?)";
    String ANY_CHAR_NUM_SLASH_HYPHEN = ".*?(\\d+)\\/-";
    String RS = "Rs";
    String MRP = "MRP";
    String MAXIMUM_RETAIL_PRICE = "MAXIMUM RETAIL PRICE";
    String M_DOT_R_DOT_P_DOT = "M.R.P.";

    Pattern p1 = Pattern.compile(NUM_SLASH_HYPHERN);
    Pattern p2 = Pattern.compile(NUM_DOT_OO);
    Pattern p3 = Pattern.compile(RS + ANY_CHAR_NUM_OPTIONAL_00);

    Pattern p4 = Pattern.compile(MRP + ANY_CHAR_NUM_SLASH_HYPHEN, Pattern.CASE_INSENSITIVE);
    Pattern p5 = Pattern
        .compile(MAXIMUM_RETAIL_PRICE + ANY_CHAR_NUM_SLASH_HYPHEN, Pattern.CASE_INSENSITIVE);
    Pattern p6 = Pattern
        .compile(M_DOT_R_DOT_P_DOT + ANY_CHAR_NUM_SLASH_HYPHEN, Pattern.CASE_INSENSITIVE);

    Pattern p7 = Pattern.compile(MRP + ANY_CHAR_NUM_OPTIONAL_00, Pattern.CASE_INSENSITIVE);
    Pattern p8 = Pattern
        .compile(MAXIMUM_RETAIL_PRICE + ANY_CHAR_NUM_OPTIONAL_00, Pattern.CASE_INSENSITIVE);
    Pattern p9 = Pattern
        .compile(M_DOT_R_DOT_P_DOT + ANY_CHAR_NUM_OPTIONAL_00, Pattern.CASE_INSENSITIVE);

    List<Pattern> patterns = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9);

    for (Pattern pattern : patterns) {
      Matcher m = pattern.matcher(text);
      if (m.find()) {
        res = m.group(1);
        break;
      }
    }

    return res;
  }

  public static void main(String[] args) throws Exception {
    String baseFile = "/Users/mohammad.talha/Downloads/fileText_2";
    int c = 0;
    for (int i = 1; i <= 14; i++) {
      String file = baseFile + "/a" + i + ".csv";
      Scanner scanner = new Scanner(new File(file));
      while (scanner.hasNext()) {
        String text = scanner.nextLine();
        Parser parser = new Parser();
        String mrp = parser.findMrp(text);
        if (mrp.length() != 0 && (mrp.charAt(0) == '2' || mrp.charAt(0) == '3')) {
          Double d = Double.parseDouble(mrp);
          if (d > 1999.0) {
            mrp = mrp.substring(1);
          }
        }
        System.out.println("i = " + i + " " + mrp);
      }
    }
  }


}
