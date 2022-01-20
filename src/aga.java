
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.lang.Thread.sleep;

public class aga {
    static Map<Character, Integer> hashMap = new HashMap<>();                // проста мапаа для подсчета символов
    static Map<Character, Integer> hashMapCrypt = new HashMap<>();                // проста мапаа для подсчета символов
    static Map<Integer, Character> hashMapOver = new HashMap<>();            // Упордечная мапа номер и его символов 1 а    2 б
    static Map<Character, Integer> hashMapNotOver = new HashMap<>();         // Упорядочная мапа символ и его номер а 1 , б 2
    static Map<Character, Integer> hashMapProcent = new HashMap<>();         // Мапа в процентах  у тестового файла
    static Map<Integer,Character> hashMapProcentOver = new HashMap<>();      // Обратная мапа в процентах для контроля у тестового файла
    static Map<Character, Integer> hashMapProcentCrypt = new HashMap<>();         // Мапа в процентах  у зашифровоного файла
    static Map<Integer,Character> hashMapProcentOverCrypt = new HashMap<>();      // Обратная мапа в процентах для контроля у зашифровоного файла
    static String nameFile = "test1.txt";                                    // Имя Файла оригинал не зашифрованый
    static String nameFileNew = "notes4.txt";                                // ИмяФайла куда положим зашифрованый
    static String nameFileForDeshefrovka = "notes6.txt";                     // ИмяФайла куда положем расшифрованый текс
    static String nameFileForStatAnalise = "notes8.txt";                     // ИмяФайла который используется для понимания технической части текста
    static int smeshenie = 30;                                               // Смещение если мы типа его знаем


    public static void main(String[] args) throws IOException, InterruptedException {
        baseCharacters();
        //   countCharacters(readFile());                                        // Нужно внутри методов
        //      encrypt(readFile());                                             // Шифруем файл
        //   decryptBruteForce(readFileForDecrypt());                            // Расшифровываем файл не зная смещение
        // decryptWithKnowInt(readFileForDecrypt(),smeshenie);                  // расшифровка зная смешение
        //   decryptWithStatAnalise(readFileForDecrypt());                      // Расшифровка использая Стат Анализ

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ведите что вы хотите сделать");
        System.out.println("1 Зашифровать файла");
        System.out.println("2 Расшифровать файл зная  смешение");
        System.out.println("3 Расшифровать файл брут форсом  ");
        System.out.println("4 Расшифровать файл СтатАналищом испольузая файл для сбора анализа");
        String enter= scanner.next();
        if (Integer.parseInt(enter)==1) {
            System.out.println("Введите имя незашифровоного файла");
            nameFile= scanner.next();
            System.out.println("Введите имя файла куда положем зашифрованую информацию");
            nameFileNew= scanner.next();
            encrypt(readFile());
        }
        if (Integer.parseInt(enter)==2) {
            System.out.println("Введите имя зашефровоного файла");
            nameFileNew= scanner.next();
            System.out.println("Введите имя файла куда положим расшифрованую информацию");
            nameFileForDeshefrovka= scanner.next();
            System.out.println("Введите смещение");
            smeshenie=Integer.parseInt(scanner.next());
            decryptWithKnowInt(readFileForDecrypt(),smeshenie);
        }
        if (Integer.parseInt(enter)==3){
            System.out.println("Введитя имя зашифровоного файла для BruteForce");
            nameFileNew=scanner.next();
            System.out.println("Введите имя файла куда положим расшифрованую информацию");
            nameFileForDeshefrovka= scanner.next();
            decryptBruteForce(readFileForDecrypt());
        }
        if (Integer.parseInt(enter)==4){
            System.out.println("Введите имя файла для анализа");
            nameFileForStatAnalise =scanner.next();
            System.out.println("Введите имя зашифрогоно зашифровоного файла");
            nameFileNew=scanner.next();
            System.out.println("Введите имя файла куда положим расшифрованую информацию");
            nameFileForDeshefrovka= scanner.next();
            decryptWithStatAnalise(readFileForDecrypt());
        }



    }
    public static List<String> readFile() throws IOException {
        // String nameFile = "test.txt";
        //   String nameFile = "notes4.txt";
        //  String nameDirectory = "Q:\\filetest\\lvl31";
        // Path pathDirectory = Path.of(nameDirectory);
        Path filePath = Path.of(nameFile);
        // List<String> strings = Files.readAllLines(pathDirectory.resolve(filePath), Charset.forName("windows-1251"));
        // List<String> strings = Files.readAllLines(filePath, Charset.forName("windows-1251"));
        List<String> strings = Files.readAllLines(filePath);
        return strings;
    }                     // Читаем файл для шифровки
    public static List<String> readFileForDecrypt() throws IOException {
        // String nameFile = "test.txt";
        //   String nameFile = "notes4.txt";
//        String nameDirectory = "Q:\\filetest\\lvl31";
//        Path pathDirectory = Path.of(nameDirectory);

        // List<String> strings = Files.readAllLines(pathDirectory.resolve(filePath), Charset.forName("windows-1251"));
        Path filePath = Path.of(nameFileNew);

        //  List<String> strings = Files.readAllLines(filePath, Charset.forName("windows-1251"));
        List<String> strings = Files.readAllLines(filePath);

        return strings;
    }           // Читаем файл для дешефровки
    public static List<String> readFileForAnalise() throws IOException {
        // String nameFile = "test.txt";
        //   String nameFile = "notes4.txt";
//        String nameDirectory = "Q:\\filetest\\lvl31";
//        Path pathDirectory = Path.of(nameDirectory);

        // List<String> strings = Files.readAllLines(pathDirectory.resolve(filePath), Charset.forName("windows-1251"));
        Path filePath = Path.of(nameFileForStatAnalise);

        //  List<String> strings = Files.readAllLines(filePath, Charset.forName("windows-1251"));
        List<String> strings = Files.readAllLines(filePath);

        return strings;
    }           // Читаем файл для Анализа


    public static void countCharacters(List<String> strings) {
        for (String stroka : strings) {
            char[] strokaChar = stroka.toCharArray();
            for (int i = 0; i < strokaChar.length; i++) {

                if (hashMap.get(strokaChar[i]) == null) {
                    continue;
                } else {
                    hashMap.put(strokaChar[i], hashMap.get(strokaChar[i]) + 1);
                }

            }
        }
        Integer summaVsexSimmvolov = 0;
        for (Map.Entry<Character, Integer> pair : hashMap.entrySet()) {
            Character key = pair.getKey();                      //ключ
            Integer value = pair.getValue();                  //значение
            // System.out.println(key + ":" + value);
            summaVsexSimmvolov = summaVsexSimmvolov + value;
        }
        System.out.println(summaVsexSimmvolov);

        for (Map.Entry<Character, Integer> pair : hashMap.entrySet()) {
            Character key = pair.getKey();                      //ключ
            Integer value = pair.getValue();                  //значение
            //  System.out.println(key + ":" + value);
            hashMapProcent.put(key, (value * 1000) / summaVsexSimmvolov);
        }
        for (Map.Entry<Character, Integer> pair : hashMapProcent.entrySet()) {
            Character key = pair.getKey();                      //ключ
            Integer value = pair.getValue();                  //значение
            if(hashMapProcentOver.get(value)==null) {
                hashMapProcentOver.put(value, key);
            }else{
                while(true){                                        // Делаем цикл и меням Если количесво входов совподает
                    value++;
                 if(hashMapProcentOver.get(value)==null) {
                        hashMapProcentOver.put(value, key);
                        break;
                    }
                }
            }
        }
        hashMapProcent.clear();
        for (Map.Entry<Integer,Character> pair : hashMapProcentOver.entrySet()) {  // Пересобираем Мапу что бы совподала с верхней
            Integer key = pair.getKey();                      //ключ
            Character  value = pair.getValue();
            hashMapProcent.put(value,key);
        }

        for (Map.Entry e : hashMapProcent.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());

        }

    }                     // Считаем символы что бы расшифровать методом стат Анализа
    public static void countCharactersCrypt(List<String> strings) {
        for (String stroka : strings) {
            char[] strokaChar = stroka.toCharArray();
            for (int i = 0; i < strokaChar.length; i++) {

                if (hashMapCrypt.get(strokaChar[i]) == null) {
                    continue;
                } else {
                    hashMapCrypt.put(strokaChar[i], hashMapCrypt.get(strokaChar[i]) + 1);
                }

            }
        }
        Integer summaVsexSimmvolov = 0;
        for (Map.Entry<Character, Integer> pair : hashMapCrypt.entrySet()) {
            Character key = pair.getKey();                      //ключ
            Integer value = pair.getValue();                  //значение
            // System.out.println(key + ":" + value);
            summaVsexSimmvolov = summaVsexSimmvolov + value;
        }
        System.out.println(summaVsexSimmvolov);

        for (Map.Entry<Character, Integer> pair : hashMapCrypt.entrySet()) {
            Character key = pair.getKey();                      //ключ
            Integer value = pair.getValue();                  //значение
            //  System.out.println(key + ":" + value);
            hashMapProcentCrypt.put(key, (value * 1000) / summaVsexSimmvolov);
        }
        for (Map.Entry<Character, Integer> pair : hashMapProcentCrypt.entrySet()) {
            Character key = pair.getKey();                      //ключ
            Integer value = pair.getValue();                  //значение
            if(hashMapProcentOverCrypt.get(value)==null) {
                hashMapProcentOverCrypt.put(value, key);
            }else{
                while(true){                                        // Делаем цикл и меням Если количесво входов совподает
                    value++;
                    if(hashMapProcentOverCrypt.get(value)==null) {
                        hashMapProcentOverCrypt.put(value, key);
                        break;
                    }
                }
            }
        }
        hashMapProcentCrypt.clear();
        for (Map.Entry<Integer,Character> pair : hashMapProcentOverCrypt.entrySet()) {  // Пересобираем Мапу что бы совподала с верхней
            Integer key = pair.getKey();                      //ключ
            Character  value = pair.getValue();
            hashMapProcentCrypt.put(value,key);
        }

        for (Map.Entry e : hashMapProcentCrypt.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());

        }

    }
    public static void encrypt(List<String> spisok) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("На сколько символов сделать смещение");
        String smesh = scanner.nextLine();
        Integer next = Integer.parseInt(smesh);
        if (next > 41) {
            next = next % 41;
        }

        ArrayList<String> outputSpisok = new ArrayList<>();
        String overwrite = "";
        //FileWriter writer = new FileWriter("Q:\\filetest\\lvl31\\notes3.txt", true);
        FileWriter writer = new FileWriter(nameFileNew);
        for (String stroka : spisok) {
            char[] strokaChar = stroka.toLowerCase().toCharArray();
            for (int i = 0; i < strokaChar.length; i++) {

                if (hashMap.get(strokaChar[i]) != null) {
                    if (hashMapNotOver.get(strokaChar[i]) + next < 41) {
                        overwrite = overwrite + hashMapOver.get(hashMapNotOver.get(strokaChar[i]) + next);
                    } else {

                        overwrite = overwrite + hashMapOver.get(hashMapNotOver.get(strokaChar[i]) + next - 41);
                    }
                } else {
                    overwrite = overwrite + strokaChar[i];
                }

            }
            outputSpisok.add(overwrite);
            overwrite = "";
        }
        for (String s : outputSpisok) {
            writer.write(s);
            writer.write('\n');
        }
        writer.flush();
        writer.close();
    }           // Метод защифровывает

    public static void baseCharacters() {
        char[] bazaSim = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '”', ':', '-', '!', '?', ' '};

        for (int i = 0; i < bazaSim.length; i++) {
            hashMap.put(bazaSim[i], 0);
        }
        for (int i = 0; i < bazaSim.length; i++) {
            hashMapCrypt.put(bazaSim[i], 0);
        }
        for (int i = 0; i < bazaSim.length; i++) {
            hashMapOver.put(i, bazaSim[i]);
        }
        for (int i = 0; i < bazaSim.length; i++) {
            hashMapNotOver.put(bazaSim[i], i);
        }
    }          // Всегда надо запускать

    public static void decryptBruteForce(List<String> spisok) throws IOException, InterruptedException {

        FileWriter writer = new FileWriter(nameFileForDeshefrovka);
        ArrayList<String> right = new ArrayList();
        int smechenie = 0;
        String ss = "";
        while (true) {
            smechenie++;
            right.clear();
            for (String s : spisok) {
                char[] mass = s.toCharArray();
                for (int i = 0; i < mass.length; i++) {

                    if (hashMap.get(mass[i]) != null) {
                        if (hashMapNotOver.get(mass[i]) - smechenie < 0) {
                            ss = ss + hashMapOver.get(hashMapNotOver.get(mass[i]) - smechenie + 41);
                        } else {

                            ss = ss + hashMapOver.get(hashMapNotOver.get(mass[i]) - smechenie);
                        }
                    } else {
                        ss = ss + mass[i];
                    }

                }
                right.add(ss);
                ss = "";


            }
            boolean answer = false;
            answer = methodTest(right);

         //   System.out.println("При смещение " + smechenie + " Результат " + answer);
            if (answer) {
                break;
            }
        }
        for (String s : right) {
            writer.write(s);
            writer.write('\n');
        }
        writer.flush();
        writer.close();

    }         // Расшифровка не знаем перебор по размеру слов
    public static void decryptWithKnowInt(List<String> spisok, int smechenie) throws IOException {

        String ss = "";
        FileWriter writer = new FileWriter(nameFileForDeshefrovka);
        ArrayList<String> pavilno = new ArrayList();
        for (String s : spisok) {
            char[] mass = s.toCharArray();
            for (int i = 0; i < mass.length; i++) {

                if (hashMap.get(mass[i]) != null) {
                    if (hashMapNotOver.get(mass[i]) - smechenie < 0) {
                        ss = ss + hashMapOver.get(hashMapNotOver.get(mass[i]) - smechenie + 41);
                    } else {

                        ss = ss + hashMapOver.get(hashMapNotOver.get(mass[i]) - smechenie);
                    }
                } else {
                    ss = ss + mass[i];
                }

            }
            System.out.println(ss);
            pavilno.add(ss);
            ss = "";

        }
        for (String s : pavilno) {
            writer.write(s);
            writer.write('\n');
        }
        writer.flush();
        writer.close();
    }               // Расшифровка знаем смещение
    public static void decryptWithStatAnalise(List<String> spisok) throws IOException {
        countCharacters(readFileForAnalise());
        countCharactersCrypt(spisok);

        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> listCrypt = new ArrayList<>();
        ArrayList<String> right = new ArrayList<>();
        for (Map.Entry<Character,Integer> pair : hashMapProcent.entrySet()) {
            Character   key = pair.getKey();                      //ключ
            Integer  value = pair.getValue();
            list.add(value);
        }
        for (Map.Entry<Character,Integer> pair : hashMapProcentCrypt.entrySet()) {
            Character   key = pair.getKey();                      //ключ
            Integer  value = pair.getValue();
            listCrypt.add(value);
        }
        Collections.sort(list);
        Collections.sort(listCrypt);
        String ss="";
        for (String s : spisok) {
            char[] mass = s.toCharArray();
            for (int i = 0; i < mass.length; i++) {

                if (hashMapCrypt.get(mass[i]) != null) {
                    /* Берем символ                          mass[i]         (Символ)
                        Узнаем его процент вхождения        hashMapProcentCrypt.get(mass[i]       (Число)
                        Падаем в зашифрованы упорядоченый ArrayList(Это отсортированые наши проценты)  и вытаскиваем его индекс по этому числу listCrypt.indexOf( hashMapProcentCrypt.get(mass[i])) (Индекс)
                        Падаем в незашефрованый упорядочный лист сформированый с помощьюе анализа тесово правльного файла list.get(listCrypt.indexOf( hashMapProcentCrypt.get(mass[i]))) Возращает (значение в проентах у такодже незашифроного ArrayList)
                        Вытаскиваем символ по проценту hashMapProcentOver.get(list.get(listCrypt.indexOf( hashMapProcentCrypt.get(mass[i]))))
                     */
                    ss=ss+ hashMapProcentOver.get(list.get(listCrypt.indexOf( hashMapProcentCrypt.get(mass[i]))))                  ;
                } else {
                    ss = ss + mass[i];
                }

            }
            right.add(ss);
            ss = "";


        }
        FileWriter writer = new FileWriter(nameFileForDeshefrovka);
        for (String s : right) {
            writer.write(s);
            writer.write('\n');
        }
        writer.flush();
        writer.close();


    }                                                  // Расшифровка используя стат аналищ

    public static boolean methodTest(ArrayList<String> list) throws InterruptedException {

        // Ищем длинные слова их должно быть мало (ну по факту мы анализируем пробелы)
        boolean answer = false;
        Map<Integer, Integer> mapInt = new HashMap<>();

        for (String s : list) {
            // System.out.println(s.length());
        //    sleep(1000);
            String[] test = s.split(" ");
       //     System.out.println(test.length);
            for (int i = 0; i < test.length; i++) {
               // System.out.println(test.length);
                if (mapInt.get(test[i].length()) != null) {
                    mapInt.put(test[i].length(), mapInt.get(test[i].length()) + 1);
                } else {
                    mapInt.put(test[i].length(), 1);
                }
            }
            // System.out.println(mapInt.size()+"   1");
            //  if (mapInt.size() > 30) break;

        }
        for (Map.Entry<Integer, Integer> pair : mapInt.entrySet()) {
            Integer key = pair.getKey();                      //ключ
            Integer value = pair.getValue();                  //значение
            if (key > 40) {
              //  System.out.println(key);
                answer=false;
                break;

            } else {
                answer = true;

            }
        }

        return answer;
    }
}
