package Trees;

import java.util.Scanner;

public class TreeDictionary {

	static Node Root;
	static int[] Max = new int[1001];
	static String[] printWord = new String[1000];
	static int fp = 0;

	public TreeDictionary() {
		Root = new Node('*');
		for (int i = 0; i < 1001; i++) {
			Max[i] = 0;
		}
	}

	static void insertNode(String str) {
		Node temp = Root;
		char x;
		for (int i = 0; i < str.length(); i++) {
			x = str.charAt(i);
			if (i == 0) {
				if (temp.children[x - 'A'] == null)
					temp.children[x - 'A'] = new Node(x);
				temp = temp.children[x - 'A'];
			} else {
				if (temp.children[x - 'a'] == null)
					temp.children[x - 'a'] = new Node(x);
				temp = temp.children[x - 'a'];
			}

		}
		if (temp.children[26] == null)
			temp.children[26] = new Node('$');
		Max[str.length()]++;
	}

	static boolean searchWord(String str) {
		Node flag = checkExist(str);
		if (flag == null)
			return false;
		else if (flag.children[26] == null) {
			return false;
		}

		return true;
	}

	static void startWith(String word) {
		Node temp = checkExist(word);
		if (temp == null)
			return;
		char[] str = new char[1000];
		for (int i = 0; i < word.length(); i++) {
			str[i] = word.charAt(i);
		}
		display(temp, str, word.length(), word);
	}

	public static void display(Node curr, char str[], int level, String word) {

		if (curr.children[26] != null) {
			String s = new String(str, 0, level);
			printWord[fp] = s;
			fp++;

			level = word.length();
		}

		int i;
		for (i = 0; i < 26; i++) {
			if (curr.children[i] != null) {
				if (i == 0) {
					str[level] = ((char) (i + 'A'));
				} else
					str[level] = ((char) (i + 'a'));
				display(curr.children[i], str, level + 1, word);
			}
		}
	}

	public static void LongestPath() {
		Node curr = Root;
		char[] str = new char[1000];
		displayLongestPath(curr, str, 0);
	}

	public static void displayLongestPath(Node curr, char str[], int level) {

		if (curr.children[26] != null) {
			int longest = 0;
			for (int i = 1000; i >= 0; i--) {
				if (Max[i] != 0) {
					longest = i;
					break;
				}
			}
			String s = new String(str, 0, level);
			if (longest == level) {
				printWord[fp] = s;
				fp++;
			}
		}

		int i;
		for (i = 0; i < 26; i++) {
			if (curr.children[i] != null) {
				if (level == 0)
					str[level] = (char) (i + 'A');
				else
					str[level] = (char) (i + 'a');
				displayLongestPath(curr.children[i], str, level + 1);
			}
		}
	}

	public static Node checkExist(String str) {
		Node temp = Root;
		for (int i = 0; i < str.length(); i++) {
			char x = str.charAt(i);
			if (i == 0) {
				if (temp.children[x - 'A'] == null)
					return null;
				temp = temp.children[x - 'A'];
			} else {
				if (temp.children[x - 'a'] == null)
					return null;
				temp = temp.children[x - 'a'];
			}
		}
		return temp;
	}

	static boolean checkEmpty(Node curr) {
		for (int i = 0; i < 26; i++) {
			if (curr.children[i] != null)
				return false;
		}
		return true;
	}

	static void deleteWord(String word) {
		if (!searchWord(word))
			return;
		Node curr = Root;
		delete(curr, word, 0);
		Max[word.length()]--;
	}

	static Node delete(Node curr, String word, int level) {
		if (curr == null)
			return null;
		if (word.length() == level) {
			if (curr.children[26] != null)
				curr.children[26] = null;
			if (checkEmpty(curr)) {
				curr = null;
			}
			return curr;
		}
		int index;
		if (level == 0)
			index = word.charAt(level) - 'A';
		else
			index = word.charAt(level) - 'a';
		curr.children[index] = delete(curr.children[index], word, level + 1);

		if (checkEmpty(curr) && (curr.children[26] == null)) {
			curr = null;
		}

		return curr;

	}

	static class Node {
		public char cr;
		public Node[] children;

		public Node(char cr) {
			this.cr = cr;
			children = new Node[27];
		}
	}

	public static void main(String[] args) {
		TreeDictionary ob = new TreeDictionary();
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		String str;
		N++;
		while (N != 0) {
			str = sc.nextLine();
			String[] arrOfStr = str.split(" ");

			if (arrOfStr[0].matches("insert")) {
				insertNode(arrOfStr[1]);
			} else if (arrOfStr[0].matches("search")) {

				if (searchWord(arrOfStr[1]) == true)
					printWord[fp] = "YES";
				else
					printWord[fp] = "NO";
				fp++;

			} else if (arrOfStr[0].matches("delete")) {
				deleteWord(arrOfStr[1]);

			} else if (arrOfStr[0].matches("startwith")) {
				startWith(arrOfStr[1]);
			} else if (arrOfStr[0].matches("longest")) {
				LongestPath();
			}
			N--;

		}
		for (int u = 0; u < fp; u++) {
			System.out.print(printWord[u]);
			if (u != fp - 1)
				System.out.println();

		}

	}

}
