package sample;

import java.io.*;
import java.util.ArrayList;
public class LZ77Compressor {

    private String filename;

    ArrayList<String> compress(String filename) {
        this.filename = filename;
        String word = "";
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader in = new BufferedReader(fileReader);
            String line;
            while ((line = in.readLine()) != null) {
                word += line;
            }
            fileReader.close(); // sa3at bydrb lma ast5dm cat lazm 22flo 34an ast5do f el mv w el cp
        } catch (FileNotFoundException ex) {
            System.out.println(filename + ", file not found.");
        } catch (IOException ex) {
            System.out.println(filename + ", input/output error.");
        }
        StringBuilder dictionary = new StringBuilder();
        StringBuilder lastMatched = new StringBuilder();
        String nextChar = null;
        int length = 0, pointer = 0;
        ArrayList<String> result = new ArrayList<>();
        int size = word.length();
        for (int i = 0; i < size; i++) {
            lastMatched.append(word.charAt(i));
            if (dictionary.lastIndexOf(lastMatched.toString()) != -1) {
                length = lastMatched.length();
                pointer = i - dictionary.lastIndexOf(lastMatched.toString()) - lastMatched.length() + 1;
                if (i == size - 1) {
                    nextChar = null;
                    result.add("<" + pointer + "," + length + "," + null + ">");
                }
            } else {
                dictionary.append(lastMatched);
                lastMatched.delete(0, lastMatched.length());
                nextChar = String.valueOf(word.charAt(i));
                result.add("<" + pointer + "," + length + "," + nextChar + ">");
                length = 0;
                pointer = 0;
                nextChar = null;
            }
        }
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            for (String child : result) {
                writer.println(child);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    String decompress(ArrayList<String> s) {
        ArrayList<Integer> pointers = new ArrayList<>();
        StringBuilder pointer = new StringBuilder();
        ArrayList<Integer> lengths = new ArrayList<>();
        StringBuilder length = new StringBuilder();
        int pointerLength = 0;
        ArrayList<String> nextChars = new ArrayList<>();
        StringBuilder nextChar = new StringBuilder();
        int lengthsLength = 0;
        StringBuilder originalWord = new StringBuilder();

        for (String value : s) {
            for (int j = 1; j < value.length(); j++) {
                if (!String.valueOf(value.charAt(j)).equals(",")) {
                    pointer.append(value.charAt(1));
                } else {
                    pointers.add(Integer.valueOf(pointer.toString()));
                    pointerLength = pointer.length();
                    pointer.delete(0, pointer.length());
                    break;
                }
            }
            for (int j = pointerLength + 2; j < value.length(); j++) {
                if (!String.valueOf(value.charAt(j)).equals(",")) {
                    length.append(value.charAt(j));
                } else {
                    lengths.add(Integer.valueOf(length.toString()));
                    lengthsLength = length.length();
                    length.delete(0, length.length());
                    break;
                }
            }
            for (int j = lengthsLength + pointerLength + 3; j < value.length(); j++) {
                if (!String.valueOf(value.charAt(j)).equals(">")) {
                    nextChar.append(value.charAt(j));
                } else {
                    nextChars.add(nextChar.toString());
                    nextChar.delete(0, nextChar.length());
                    break;
                }
            }
        }
        for (int i = 0; i < pointers.size(); i++) {
            for (int j = 0; j < lengths.get(i); j++) {
                if (originalWord.length() - pointers.get(i) > -1) {
                    originalWord.append(originalWord.charAt(originalWord.length() - pointers.get(i)));
                } else
                    break;
            }
            if (!nextChars.get(i).equals("null")) {
                originalWord.append(nextChars.get(i));
            }
        }
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            writer.println(originalWord);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return originalWord.toString();
    }

}
