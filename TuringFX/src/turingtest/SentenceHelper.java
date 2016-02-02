package turingtest;

public class SentenceHelper {
	public static String fixSentence(String sentence){
		int pos = 0;
		boolean capitalize = true;
		StringBuilder sb = new StringBuilder(sentence);
		while (pos < sb.length()) {
			if (".?!".indexOf(sb.charAt(pos)) != -1) {
				capitalize = true;
			} else if (capitalize && !Character.isWhitespace(sb.charAt(pos))) {
				if(pos > 0 && !Character.isWhitespace(sb.charAt(pos-1))){
					sb.insert(pos, ' ');
					pos++;
				}
				sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
				capitalize = false;
			}
			pos++;
		}
		if(".?!".indexOf(sb.charAt(sb.length()-1)) == -1){
			sb.append('.');
		}

		return  sb.toString();
	}

	public static int countWords(String sentence){
		String trim = sentence.trim();
	    if (trim.isEmpty())
	        return 0;
	    return trim.split("\\s+").length;
	}
}
