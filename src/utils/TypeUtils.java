package utils;

public class TypeUtils {

  private static final String[] VALID_TYPES = {
      "normal", "fire", "water", "electric", "grass", "ice",
      "fighting", "poison", "ground", "flying", "psychic", "bug",
      "rock", "ghost", "dragon", "dark", "steel", "fairy"
  };

  public static boolean isValidType(String type) {
    boolean found = false;
    for (String validType : VALID_TYPES) {
      if (validType.equals(type)) {
        found = true;
        break;
      }
    }
    return found;
  }


  public static String capitalize(String s) {
    String result;
    if (s == null || s.isEmpty()) {
      result = s;
    } else {
      result = s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    return result;
  }
}
