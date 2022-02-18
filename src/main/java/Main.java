package com.exercises;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;

import org.apache.spark.sql.catalyst.catalog.ExternalCatalogUtils;

public class Main {

	private static final String DEFAULT_PARTITION_NAME = "__HIVE_DEFAULT_PARTITION__";

	public static void main(String[] args) throws UnsupportedEncodingException {

		String path1 = "https://mvnrepository.com/artifact/org.apache.hadoop/hadoop-common/3.3.1";
		String col = "URL";
		
		prints(col, path1);
	}

	private static void prints(String col, String path) {
		System.out.println("URL IS : " + path);
		System.out.println("RESULT OF CONVERTED CODE IS : " + getPartitionPathString(col, path));
		System.out.println("RESULT OF URL ENCODER IS : " + col + "=" + URLEncoder.encode(path, StandardCharsets.UTF_8));
		System.out.println("RESULT OF SCALA ENCODER IS : " + ExternalCatalogUtils.getPartitionPathString(col, path));
		System.out.println("RESULT FOR NULL VALUE IS : " + getPartitionPathString(col, null));
	}

	private static String getPartitionPathString(String column, String value) {
		String partitionString = getPartitionValueString(value);
		String result = escapePathName(column) + "=" + partitionString;

		return result;
	}

	private static String getPartitionValueString(String value) {
		if (value == null || value.isEmpty()) {
			return DEFAULT_PARTITION_NAME;
		} else {
			return escapePathName(value);
		}
	}

	private static String escapePathName(String path) {
		StringBuilder builder = new StringBuilder();

		char[] chars = path.toCharArray();
		for (int i = 0; i < chars.length; i++) {

			char c = chars[i];
			String hex = String.format("%2X", (int) c);
			if (needsEscaping(c)) {
				builder.append("%");
				builder.append(hex);
			} else {
				builder.append(c);
			}
		}

		return builder.toString();
	}

	private static boolean needsEscaping(char c) {
		boolean flag = (c >= 0 && c < charToEscape().size() && charToEscape().get(c));
		return flag;
	}

	private static BitSet charToEscape() {
		BitSet bitSet = new BitSet();

		/**
		 * ASCII 01-1F are HTTP control characters that need to be escaped. \u000A and
		 * \u000D are \n and \r, respectively.
		 */
//		Object[] clist = new Object[] { "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007",
//				"\u0008", "\u0009", "\n", "\u000B", "\u000C", "\r", "\u000E", "\u000F", "\u0010", "\u0011", "\u0012",
//				"\u0013", "\u0014", "\u0015", "\u0016", "\u0017", "\u0018", "\u0019", "\u001A", "\u001B", "\u001C",
//				"\u001D", "\u001E", "\u001F", "\"", "#", "%", "\"", "*", "/", ":", "=", "?", "\\", "\u007F", "{", "[",
//				"]", "^" };

		int[] clist = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
				25, 26, 27, 28, 29, 30, 31, 32, 34, 35, 37, 39, 42, 47, 58, 60, 61, 62, 63, 91, 92, 93, 94, 123, 124,
				127 };

		for (int i = 0; i < clist.length; i++) {

//			String temp = StringEscapeUtils.unescapeJava(clist[i]);
//			bitSet.set(Integer.parseInt(StringEscapeUtils.unescapeJava(clist[i])));

			bitSet.set(clist[i]);
		}

//		if (Shell.WINDOWS) {
//			System.out.println("In SHELL");
//			String[] clistShell = new String[] { " ", "<", ">", "|" };
//			for (int i = 0; i < clistShell.length; i++) {
//
//				bitSet.set(Integer.parseInt(StringEscapeUtils.unescapeJava(clistShell[i])));
//			}
//		}

//		System.out.println("BITSET IS : " + bitSet);
		return bitSet;
	}

}
