
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class aga {
    static Map<Character, Integer> hashMap = new HashMap<>(); // проста мапаа для подсчета символов
    static Map<Integer, Character> hashMapOver = new HashMap<>();// Упордечная мапа номер и его символов 1 а    2 б
    static Map<Character,Integer> hashMapNotOver = new HashMap<>(); // Упорядочная мапа символ и его номер а 1 , б 2
    static Map<Character,Integer> hashMapProcent = new HashMap<>(); // Мапа в проентах ищем частвый символ это пробел
    static String nameFile = "test.txt";   // Имя Файла оригинал не зашифрованый
    static String nameFileNew = "notes4.txt"; // ИмяФайла который будет зашифрованый уже
    static String nameFileForDeshefrovka = "notes6.txt"; // ИмяФайла который будет расшифрован
    static int smeshenie=1; // Смещение если мы типа его знаем


    public static void main(String[] args) throws IOException {
        napolnenieMap()    ;
         // podchetSimvolov(readFile());     // Нужно внутри методов
      //    shifr(readFile());              // Шифруем файл
         reshifrofka(readFile());              // Расшифровываем файл не зная смещение
     //   rashifrovka(readFileForDeshefrofka(),smeshenie);// расшифровка зная смешение
    }

    public static void shifr(List<String> spisok) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("На сколько символов сделать смещение");
        String smesh=scanner.nextLine();
        Integer next= Integer.parseInt(smesh);
        if (next>41){
            next =next%41;
        }

        ArrayList<String> outputSpisok=new ArrayList<>() ;
        String overwrite="";
        //FileWriter writer = new FileWriter("Q:\\filetest\\lvl31\\notes3.txt", true);
        FileWriter writer = new FileWriter(nameFileNew);
        for (String stroka : spisok) {
            char[] strokaChar = stroka.toLowerCase().toCharArray();
            for (int i = 0; i <strokaChar.length ; i++) {

                if (hashMap.get(strokaChar[i])!=null) {
                    if (hashMapNotOver.get(strokaChar[i])+next<41) {
                        overwrite = overwrite + hashMapOver.get(hashMapNotOver.get(strokaChar[i]) + next);
                    }else{

                        overwrite = overwrite + hashMapOver.get(hashMapNotOver.get(strokaChar[i]) + next-41);
                    }
                }else{
                    overwrite =overwrite+strokaChar[i];
                }

            }
            outputSpisok.add(overwrite);
            overwrite="";
        }
        for (String s:outputSpisok) {
            writer.write(s);
            writer.write('\n');
        }
        writer.flush();
        writer.close();
    }         // Метод защифровывает
    public static void podchetSimvolov(List<String> strings){
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
            hashMapProcent.put(key,(value*100)/summaVsexSimmvolov);
        }
        for(Map.Entry e : hashMapProcent.entrySet()){
            System.out.println(e.getKey()+" "+ e.getValue());

        }

    }         // Считаем символы что бы расшифровать методом подбора
    public static void napolnenieMap(){
        char[] bazaSim = {'а','б','в','г','д','е','ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я', '.', ',', '”', ':', '-', '!', '?', ' '};

        for (int i = 0; i < bazaSim.length; i++) {
            hashMap.put(bazaSim[i], 0);
        }
        for (int i = 0; i < bazaSim.length; i++) {
            hashMapOver.put(i,bazaSim[i]);
        }
        for (int i = 0; i < bazaSim.length; i++) {
            hashMapNotOver.put(bazaSim[i], i);
        }
    }          // Всегда надо запускать
    public static List<String> readFile() throws IOException {
        // String nameFile = "test.txt";
        //   String nameFile = "notes4.txt";
        String nameDirectory = "Q:\\filetest\\lvl31";
        Path pathDirectory = Path.of(nameDirectory);
        Path filePath = Path.of(nameFile);
        // List<String> strings = Files.readAllLines(pathDirectory.resolve(filePath), Charset.forName("windows-1251"));
        List<String> strings = Files.readAllLines(filePath, Charset.forName("windows-1251"));
        return strings;
    }  // Читаем файл для шифровки
    public static void reshifrofka(List<String> spisok) throws IOException {
        podchetSimvolov(spisok);
        FileWriter writer = new FileWriter(nameFileForDeshefrovka);
        int probel=0;
        char simvol=' ';
        int smechenie=0;
        for (Map.Entry<Character, Integer> pair : hashMapProcent.entrySet()) {
            Character key = pair.getKey();                      //ключ
            Integer value = pair.getValue();                  //значение
            if (value>probel){
                probel=value;
                simvol=key;
            }
        }
        System.out.println(probel);
        System.out.println(simvol);
        smechenie=(42+(hashMapNotOver.get(simvol)))%41;
        System.out.println(smechenie);
        String ss="";
        ArrayList<String> pavilno = new ArrayList();
        for (String s :spisok ) {
            char[] mass= s.toCharArray();
            for (int i = 0; i <mass.length ; i++) {

                if (hashMap.get(mass[i])!=null) {
                    if (hashMapNotOver.get(mass[i])-smechenie<0) {
                        ss = ss + hashMapOver.get(41 - smechenie);
                    }else{

                        ss = ss + hashMapOver.get(hashMapNotOver.get(mass[i])-smechenie);
                    }
                }else{
                    ss =ss+mass[i];
                }

            }
            System.out.println(ss);
            pavilno.add(ss);
            ss="";

        }
        for (String s:pavilno) {
            writer.write(s);
            writer.write('\n');
        }
        writer.flush();
        writer.close();
    } // Расшифровка не знаем смещенеие
    public static void rashifrovka(List<String>spisok,int smechenie) throws IOException {
        System.out.println(smechenie);
        String ss="";
        FileWriter writer = new FileWriter(nameFileForDeshefrovka);
        ArrayList<String> pavilno = new ArrayList();
        for (String s :spisok ) {
            char[] mass= s.toCharArray();
            for (int i = 0; i <mass.length ; i++) {

                if (hashMap.get(mass[i])!=null) {
                    if (hashMapNotOver.get(mass[i])-smechenie<0) {
                        ss = ss + hashMapOver.get(41 - smechenie);
                    }else{

                        ss = ss + hashMapOver.get(hashMapNotOver.get(mass[i])-smechenie);
                    }
                }else{
                    ss =ss+mass[i];
                }

            }
            System.out.println(ss);
            pavilno.add(ss);
            ss="";

        }
        for (String s:pavilno) {
            writer.write(s);
            writer.write('\n');
        }
        writer.flush();
        writer.close();
    } // Расшифровка знаем смещение
    public static List<String> readFileForDeshefrofka() throws IOException {
      // String nameFile = "test.txt";
        //   String nameFile = "notes4.txt";
//        String nameDirectory = "Q:\\filetest\\lvl31";
//        Path pathDirectory = Path.of(nameDirectory);

        // List<String> strings = Files.readAllLines(pathDirectory.resolve(filePath), Charset.forName("windows-1251"));
        Path filePath = Path.of(nameFileNew);
      //  List<String> strings = Files.readAllLines(filePath, Charset.forName("windows-1251"));
        List<String> strings = Files.readAllLines(filePath);
        return strings;
    }
}
