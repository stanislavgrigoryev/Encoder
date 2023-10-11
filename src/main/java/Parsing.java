import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Parsing {
    private final Map<Character, Integer> mapEncrypted = new HashMap<>();
    private final Map<Character, Integer> mapStatistic = new HashMap<>();
    private final Map<Character, Character> mapDecrypted = new HashMap<>();

    @SneakyThrows
    public void parse() {
        Util.writeMessage("Введите путь к файлу для его рассшифровки: ");
        String pathEncrypted = Util.readString();

        Util.writeMessage("Введите путь к файлу для набора статистики: ");
        String pathStatistic = Util.readString();

        Path path = Util.buildFileName(pathEncrypted, "_parsing");


        fillMapWithValues(mapEncrypted, pathEncrypted);
        fillMapWithValues(mapStatistic, pathStatistic);
        List<Map.Entry<Character, Integer>> listEncrypted = mapToList(mapEncrypted);
        List<Map.Entry<Character, Integer>> listStatistic = mapToList(mapStatistic);

        if (listEncrypted.size() <= listStatistic.size()) {
            for (int i = 0; i < listEncrypted.size(); i++) {
                mapDecrypted.put(listEncrypted.get(i).getKey(), listStatistic.get(i).getKey());
            }
        } else {
            Util.writeMessage("Размер файла статистики должен быть больше" + System.lineSeparator());
        }


        try(BufferedReader bufferedReader = Files.newBufferedReader(Path.of(pathEncrypted));
            BufferedWriter bufferedWriter = Files.newBufferedWriter(path)){
            while (bufferedReader.ready()){
                String stringLine = bufferedReader.readLine();
                StringBuilder stringBuilder = new StringBuilder();
                char[] chars = stringLine.toCharArray();
                for (char aChar : chars) {
                    Character character = mapDecrypted.get(aChar);
                    stringBuilder.append(character);
                }
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.newLine();
            }
        }
    }


    @SneakyThrows
    private void fillMapWithValues(Map<Character, Integer> map, String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(path))) {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                stringBuilder.append(line);
            }
            char[] chars = stringBuilder.toString().toCharArray();
            for (char aChar : chars) {
                if (!map.containsKey(aChar)) {
                    map.put(aChar, 1);
                } else {
                    map.put(aChar, map.get(aChar) + 1);
                }
            }
        }
        ;
    }

    private List<Map.Entry<Character, Integer>> mapToList(Map<Character, Integer> map) {
        Set<Map.Entry<Character, Integer>> entries = map.entrySet();
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(entries);
        Comparator<Map.Entry<Character, Integer>> comparator = (o1, o2) -> o2.getValue() - o1.getValue();
        Collections.sort(list, comparator);
        return list;
    }
}
